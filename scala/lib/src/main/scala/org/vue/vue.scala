package org.vue

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("vue", JSImport.Namespace)
@js.native
object Vue extends js.Object:
  def ref[T](obj: js.Any): Ref[T]  = js.native
  def onMounted(f: js.Function0[Unit], target: js.Object): Unit = js.native
  //def onCreated(f: js.Function0[Unit]): Unit = js.native
  def computed(f: () => js.Any): js.Any = js.native
  def defineProps(obj: js.Object): js.Any = js.native
  def reactive(obj: js.Object): js.Any = js.native
  def toRef[T](obj: js.Any, name: String): Ref[T] = js.native

@js.native
trait VueContext extends js.Object:
  val attrs: js.Object = js.native
  val slots: js.Object = js.native
  val emit: js.Object = js.native
  val expose: js.Object = js.native

@js.native
trait Ref[T] extends js.Object:
  var value: T = js.native

object Props:
  extension (self: js.Object)

    def asDyn: js.Dynamic = self.asInstanceOf[js.Dynamic]

    def getProperty[T <: js.Any](name: String): T = asDyn.selectDynamic(name).asInstanceOf[T]

    def setProperty[T <: js.Any](name: String, value: T): Unit = asDyn.updateDynamic(name)(value)

    def ref[T](name: String): Ref[T] = Vue.toRef(self, name)