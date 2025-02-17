package org.vue.platforms

import org.vue.{Components, Element, TplAttr, TplElement, typ}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("WebComponents", moduleID = "components")
object Web extends Components:
  override def label(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("span"))
  override def frame(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("div"))
  override def page(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("div"))
  override def actionBar(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("div"))
  override def gridLayout(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("div"))
  override def button(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("button"))
