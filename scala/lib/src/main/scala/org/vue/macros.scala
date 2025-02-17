package org.vue

import scala.quoted.{Expr, Quotes, Type}
import scala.quoted.*

//inline def createInstance[T]: T = ${ createInstanceImpl[T] }

// copy from https://github.com/lampepfl/dotty-macro-examples/blob/main/defaultParamsInference/src/macro.scala
private def createInstanceImpl[T: Type](using Quotes): Expr[T] =
  import quotes.*, quotes.reflect.*
  Apply(
    Select(
      New(TypeTree.of[T]),
      TypeRepr.of[T].typeSymbol.primaryConstructor
    ),
    Nil
    //idents.map(_.asExpr.asTerm)
  ).asExprOf[T]

inline def mkTemplate[T <: VueComponent](comps: Components): (String, String) = ${ mkTemplateImpl[T]('comps) }

private def mkTemplateImpl[T <: VueComponent : Type](comps: Expr[Components])(using Quotes): Expr[(String, String)] =
  import quotes.*, quotes.reflect.*

  val component = createInstanceImpl[T]
  val annotationSymbol = TypeRepr.of[VueTemplate]
  val sym = TypeRepr.of[T].typeSymbol
  val className = Expr(sym.name.asInstanceOf[String])
  val annot = sym
    .annotations
    .collectFirst {
      case term if term.tpe =:= annotationSymbol => term.asExprOf[VueTemplate]
    } match
    case Some(expr) => '{Some($expr)}
    case None => '{ None }

  '{ mkElements($className, $annot, $component, $comps) }

private def mkElements[T <: VueComponent](className: String, annot: Option[VueTemplate], component: T, comps: Components): (String, String) =

  given c: Components = comps

  val componentName = annot.filter(_.name.nonEmpty).map(_.name).getOrElse(className)
  val tplContent = component.vueTemplate.mkElement().split("\n").mkString("\n  ")
  val stylesContent = component.vueStyle.mkStyles().split("\n").mkString("\n  ")
  val scriptAttrs = annot.map { it =>
    if it.scriptSetup then " setup" else ""
  }.getOrElse("")

  val styleAttrs = annot.map { it =>
    if it.styleScoped then " scoped" else ""
  }.getOrElse("")

  val moduleID = annot.map(_.moduleID).getOrElse("<NO MODULE ID>")

  val exprDef = s"export default $componentName;"
  val exportDefault = annot.map {
    it => if it.scriptSetup then s"$componentName()" else exprDef
  }.getOrElse(exprDef)

  val templateContent = s"""
     |<script lang="js"$scriptAttrs>
     |  import {$componentName} from './$moduleID';
     |  $exportDefault
     |</script>
     |<template>
     |  $tplContent
     |</template>
     |<style lang="scss"$styleAttrs>
     |  $stylesContent
     |</style>
     |""".stripMargin

  (componentName, templateContent)

