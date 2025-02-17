package com.app


import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel, JSImport, JSName}
import org.vue.*

import org.vue.platforms.{Web, NativeScript}

@JSExportTopLevel("LoginView", moduleID = "templates")
@VueTemplate(moduleID = "components")
class LoginView() extends VueComponent:

  override def vueTemplate(using components: Components): TplElement =

    import components.*

    frame(
      page(
        actionBar(
          gridLayout(
            label(
              text("login")
            ),
            button(
              props("text" -> "login")
            )
          )
        )
      )
    )


  override def vueStyle: Styles =
    Styles(
      sel("button", "width" -> "200px", "height" -> "44px", "background-color" -> "blue"),
      sel(".counter-text", "color" -> "red")
    )

  @JSExport
  override def compile(components: Components): js.Object =
    compileTemplate[LoginView](components)


@JSExportTopLevel("LoginView", moduleID = "components")
object LoginView extends js.Object:

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
    println(s"LoginView mounted")

  def created() =
    println(s"LoginView created")

  val methods = new js.Object:
    def buttonClick() =
      println(s"button clicked ${count.value}")
      println(s"props = ${this.propText.value}")
      count.value += 1
      this.myName.value = s"ricardo (${this.age}) > cunter ${count.value}"
