package com.app


import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel, JSImport, JSName}
import org.vue.*

/*
@JSExportTopLevel("LoginTestView", moduleID = "templates")
@VueTemplate(moduleID = "components")
class LoginTestView() extends VueComponent:

  override def vueTemplate: TplElement =
    div(
      button(
        text("click!"),
        event("click", "buttonClick")
      ),
      span(
        props("class" -> "counter-text"),
        text(" - "),
        vvar("counter"),
        text(" - "),
        vvar("propText"),
        text(" - "),
        vvar("countChanged"),
        text(" - "),
        vvar("myName"),
        text(" - "),
        vvar("age"),
      )
    )

  override def vueStyle: Styles =
    Styles(
      sel("button", "width" -> "200px", "height" -> "44px", "background-color" -> "blue"),
      sel(".counter-text", "color" -> "red")
    )

  @JSExport
  override def compile: js.Object = compileTemplate[LoginTestView]



@JSExportTopLevel("LoginTestView", moduleID = "components")
object LoginTestView extends js.Object:

  import org.vue.Props.{ref, getProperty}
  import org.vue.Ref

  extension(self: js.Object)
    def propText: Ref[String] = self.ref("propText")

    def myName: Ref[String] = self.ref("myName")

    def age: Int = self.getProperty("age")

  val props = new js.Object:
    val propText: String = ""

  val count = Vue.ref[Int](0)

  def data() = new js.Object:
    val counter = count
    val myName = "ricardo"
    val age = 38

  val computed = new js.Object:
    def countChanged() = count.value + 1

  def setup(props: js.Object, ctx: VueContext) =
    println("setup")

  def mounted() =
    println(s"LoginTestView mounted")

  def created() =
    println(s"LoginTestView created")

  val methods = new js.Object:
    def buttonClick() =
      println(s"button clicked ${count.value}")
      println(s"props = ${this.propText.value}")
      count.value += 1
      this.myName.value = s"ricardo (${this.age}) > cunter ${count.value}"
*/