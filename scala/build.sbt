val scala3Version = "3.6.3"

import sbt.*
import Keys.*
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport.*

import java.nio.file.{Files, Path}

val copyFullJS     = TaskKey[Unit]("copyJS", "Copy scala.js linker artifacts to another location after linking.")

def copyJSTask = Def.task {
    val logger = streams.value.log
    val odir = new File("./dist")
    val src = (Compile / scalaJSLinkedFile).value.data
    val isJsFileName = odir.getCanonicalPath.endsWith(".js")
    val fileName = if (isJsFileName) odir.name else src.name
    val destPath = if (isJsFileName) odir.getParentFile else odir

    logger.info(s"Copying artifacts [js,map] from ${src.getParent} to [${destPath.getCanonicalPath}]")

    IO.copy(Seq((src, destPath / fileName),
            (file(src.getCanonicalPath + ".map"), destPath / (fileName + ".map"))),
        CopyOptions(overwrite = true, preserveLastModified = true, preserveExecutable = true)
    )
}

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "com.app",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,

    copyFullJS := copyJSTask.value,
    //fastOptJS / copyFullJS := (copyFullJS triggeredBy (Compile / fastOptJS)).value,
    //fullOptJS / copyFullJS := (copyFullJS triggeredBy (Compile / fullOptJS)).value,


    Compile / fastLinkJS := {
      val report = (Compile / fastLinkJS).value
      val fastOptPath = s"${name.value.replace(".", "-")}-fastopt"
      val outDir = (Compile / fastLinkJS / crossTarget).value / fastOptPath
      val destDir = baseDirectory.value / "dist"
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

      report // Mantém a execução normal do fastLinkJS
    },

      scalaJSLinkerConfig ~= {
          _.withModuleKind(ModuleKind.ESModule)
      },
  )


