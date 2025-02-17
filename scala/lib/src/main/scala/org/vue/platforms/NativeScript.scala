package org.vue.platforms

import org.vue.{Components, Element, TplAttr, TplElement, typ}

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("NsComponents", moduleID = "components")
object NativeScript extends Components:
  override def label(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("Label"))
  override def frame(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("Frame"))
  override def page(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("Page"))
  override def actionBar(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("ActionBar"))
  override def gridLayout(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("GridLayout"))
  override def button(children: TplElement | TplAttr*): TplElement = Element(children :+ typ("Button"))