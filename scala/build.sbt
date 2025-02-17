import sbt.*
import Keys.*
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.*
import scala.sys.process.*
import java.nio.file.{Files, Path}

val scala3Version = "3.6.3"

lazy val lib = project
  .in(file("lib"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "lib",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    Compile / fastLinkJS := {
      val log = streams.value.log
      val report = (Compile / fastLinkJS).value
      val fastOptPath = s"${name.value.replace(".", "-")}-fastopt"
      val outDir = (Compile / fastLinkJS / crossTarget).value / fastOptPath
      val destDir = baseDirectory.value / ".." / "dist"
      val jsFiles = report.data.publicModules.map(_.jsFileName)

      jsFiles.foreach { fileName =>
        val srcFile = outDir / fileName
        val destFile = destDir / fileName
        IO.copyFile(srcFile, destFile) // Copia o JS gerado
      }


      val sharedFile = outDir.toPath.resolve("internal-*.js")
      val sharedFiles = Files.list(outDir.toPath)
        .filter(p => p.getFileName.toString.startsWith("internal-"))
        .toArray

      sharedFiles.foreach { f =>
        val file =  f.asInstanceOf[Path].toFile
        val destFile = destDir / file.name
        IO.copyFile(file, destFile) // Copia o JS gerado
      }

      // generate vue template
      val cmd = "node templater.js"
      val exitCode = Process(cmd).!

      if (exitCode != 0) {
        sys.error(s"❌ Erro ao executar o script Node.js ($exitCode)")
      } else {
        log.info("✅ Templates Vue gerados com sucesso!")
      }

      report // Mantém a execução normal do fastLinkJS
    },

    scalaJSLinkerConfig ~= {
        _.withModuleKind(ModuleKind.ESModule)
    },
    cleanFiles := Seq(baseDirectory.value / ".." / "dist")
  )

lazy val integration = (project in file("integration"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(lib)
  .settings(
    publish / skip := true,
    scalaVersion := scala3Version,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
    },
    // extra test dependencies
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "munit" % "1.1.0" % Test,
    )
  )

