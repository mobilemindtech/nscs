package org.vue

import scala.annotation.StaticAnnotation
import scala.scalajs.js



trait TplAttr
case class TplAttrType(name: String) extends TplAttr
case class TplAttrProps(values: Map[String, String]) extends TplAttr
case class TplAttrText(text: String) extends TplAttr
case class TplAttrChild(node: TplElement) extends TplAttr
case class TplAttrEvent(name: String, callback: String) extends TplAttr
case class TplAttrBind(prop: String, value: String) extends TplAttr
case class TplAttrIf(test: String) extends TplAttr
case class TplAttrElseIf(test: String) extends TplAttr
case class TplAttrElse() extends TplAttr
case class TplAttrShow(test: String) extends TplAttr
case class TplAttrFor(body: String, key: String) extends TplAttr
case class TplAttrVar(varName: String) extends TplAttr

trait TplElement:
  def children: Seq[TplAttr]
  def mkElement(ident: String = ""): String =
    val props = children
      .collect {
        case n: TplAttrProps => n.values.map((k, v) => s"$k=\"$v\"")
        case n: TplAttrEvent => List(s"@${n.name}=\"${n.callback}\"")
        case n: TplAttrBind => List(s":${n.prop}=\"${n.value}\"")
        case n: TplAttrIf => List(s"v-if=\"${n.test}\"")
        case n: TplAttrElseIf => List(s"v-else-if=\"${n.test}\"")
        case TplAttrElse() => List(s"v-else")
        case n: TplAttrShow => List(s"v-show=\"${n.test}\"")
        case n: TplAttrFor =>
          val key = if n.key.isEmpty then "" else s""" :v-key=\"${n.key}\""""
          List(s"v-for=\"${n.body}\"$key")
      }
      .flatten


    val typ = children.collectFirst { case t: TplAttrType => t }.map(_.name).getOrElse("NoneType")

    val content = children.collect {
      case TplAttrText(text) => s"  $ident${text}"
      case TplAttrVar(varName) => s"  $ident{{$varName}}"
      case TplAttrChild(node) => node.mkElement(s"  $ident")
    }.mkString(s"    $ident\n")

    val propsStr = if props.isEmpty then "" else s" ${props.mkString(" ")}"

    if content.isBlank
    then s"$ident<$typ$propsStr></$typ>"
    else
      s"""|$ident<$typ$propsStr>
          |$content
          |$ident</$typ>""".stripMargin

case class Element(items: Seq[TplElement | TplAttr]) extends TplElement:
  override def children: Seq[TplAttr] = items.collect {
    case n: TplAttr => n
    case n: TplElement => TplAttrChild(n)
  }

// node attrs
def typ(name: String): TplAttr = TplAttrType(name)
def props(values: (String, String)*): TplAttr = TplAttrProps(values.toMap)
def event(name: String, cb: String): TplAttr = TplAttrEvent(name, cb)
def tap(callback: String): TplAttr = event("tap", callback)
def text(txt: String): TplAttr = TplAttrText(txt)
def vvar(varName: String): TplAttr = TplAttrVar(varName)
def bind(prop: String, value: String): TplAttr = TplAttrBind(prop, value)
def `v-if`(test: String): TplAttr = TplAttrIf(test)
def `v-else-if`(test: String): TplAttr = TplAttrElseIf(test)
def `v-else`(test: String): TplAttr = TplAttrElse()
def `v-show`(test: String): TplAttr = TplAttrShow(test)
def `v-for`(body: String, key: String = ""): TplAttr = TplAttrFor(body, key)
def `v-slot`(name: String): TplAttr = props("v-slot" -> name)
def style(name: String, value: String): TplAttr = props(name -> value)

// node elements
def div(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("div"))
def button(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("button"))
def span(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("span"))
def template(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("template"))
def slot(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("slot"))
def element(children: TplElement | TplAttr*): TplElement = Element(children)

case class StyleAttr(key: String, value: String)
case class Style(name: String, attrs: Seq[StyleAttr], children: Seq[Style] = Nil)
case class Styles(styles: Style*):
  def mkStyles(): String =
    def rec(st: Style, ident: String = ""): String =
      val children = st.children.collect(it => rec(it, s"  $ident"))
      val attrs = st.attrs.collect { case StyleAttr(k, v) => s"  $ident$k:$v;" }

      val content = (attrs, children) match
        case (Nil, Nil) => ""
        case (a, Nil) => s"${a.mkString("\n")}"
        case (Nil, b) => s"${b.mkString("\n")}"
        case (a, b) => s"${a.mkString("\n")}\n${b.mkString("\n")}"

      s"""|$ident${st.name} {
          |$content
          |$ident}""".stripMargin

    styles.collect(rec(_)).mkString("\n")

// style sel
def sel(name: String, values: (String, String)|Style*): Style =
  val attrs = values.collect { case (name, value) => StyleAttr(name, value)}
  val styles = values.collect { case s: Style => s}
  Style(name, attrs, styles)

class VueTemplate(val name: String = "",
  val moduleID: String = "",
  val scriptSetup: Boolean = false,
  val styleScoped: Boolean = false) extends StaticAnnotation


trait VueComponent:
  type T <: VueComponent
  def vueTemplate(using Components): TplElement
  def vueStyle: Styles = Styles()
  def compile(components: Components): js.Object

inline def compileTemplate[T <: VueComponent](comps: Components): js.Object =
  val result = mkTemplate[T](comps)
  new js.Object {
    val name = result._1
    val content = result._2
  }


