package com.app

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


object lib:
  @JSExportTopLevel(name = "sum", moduleID="calcule")
  def sum(x: Int, y: Int): Int = x + y

  @JSExportTopLevel(name = "multiply", moduleID = "calcule")
  def multiply(x: Int, y: Int): Int = x + y

  @JSExportTopLevel(name = "info", moduleID = "logger")
  def info(msg: String): Unit = println(s"[INFO]::> $msg")

  @JSExportTopLevel(name = "error", moduleID = "logger")
  def error(msg: String): Unit = println(s"[ERROR]::> $msg")

@JSExportTopLevel("ScalaComponent", moduleID="component")
class ScalaComponent extends js.Object:
  val props = js.Array("text")
  val template = "<div>ScalaJS Rock  {{text}}!!! =)</div>"

@JSExportTopLevel("ScalaComponent2", moduleID="component")
val component = new js.Object {
  val props = js.Array("text")
  val template = "<div>ScalaJS Rock  {{text}}!!! =) ^^</div>"
}