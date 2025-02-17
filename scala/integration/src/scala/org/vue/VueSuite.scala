package org.vue


@VueTemplate()
class MyComponent extends VueComponent:

  override def vueTemplate: Node =
    div(
      button(
        tap("myCallback"),
        props("class" -> "css", "id" -> "button1"),
      ),
      button(
        tap("myCallback"),
        props("class" -> "css", "id" -> "button2"),
      ),
      span(
        text("label!")
      ),
      template(
        `v-slot`("mySlot")
      ),
      tap("myCallback"),
      bind("myprop", "text"),
      slot(
        props("name" -> "my-slot")
      ),
      `v-if`("mytest"),
      `v-show`("testShow")
    )

  override def vueStyle: Styles =
    Styles(
      sel("#id", "font-size" -> "10px", "text-align" -> "center"),
      sel(".mycls",
        "font-size" -> "30px",
        "text-align" -> "rigth",
        sel("&.other", "background-color" -> "black",
          sel(".cc1", "float" -> "rigth")),
        sel("&.other2", "background-color" -> "black", "font-size" -> "10px"))
    )



class MySuite extends munit.FunSuite {
  test("test macro") {

    //val instance = createInstance[MyComponent]
    //println(s"instance = $instance")

    //def styles = new MyComponent().vueStyle.mkStyles()
    //assertEquals(styles, "")

    val result = mkTemplate[MyComponent]
    assertEquals(result, "<div><span></span></div>")

  }
}
