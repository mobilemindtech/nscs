package org.vue.libs

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobalScope, JSImport}

object NodeJS:
  @JSImport("fs", JSImport.Namespace)
  @js.native
  object fs extends js.Object:
    def existsSync(path: js.Any): Boolean = js.native
    def mkdirSync(path: js.Any): Boolean = js.native
    def writeFileSync(path: js.Any, data: js.Any): js.Any = js.native

  @JSImport("path", JSImport.Namespace)
  @js.native
  object path extends js.Object:
    def resolve(paths: String*): String = js.native

@js.native
@JSGlobalScope
object Globals extends js.Any:
  val __dirname: String = js.native