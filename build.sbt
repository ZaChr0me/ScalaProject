val scala3Version = "3.1.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "ScalaProject",
    version := "0.1.0-SNAPSHOT",
    scalacOptions ++= Seq(
      "-language:postfixOps",
      "-Ykind-projector",
      "-source",
      "3.1"
    ),
    fork := true,
    scalaVersion := scala3Version,
    libraryDependencies ++= {
      Seq(
        "org.scalatest" %% "scalatest" % "3.2.9" % Test,
        "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
        "org.scalafx" %% "scalafx" % "16.0.0-R24"
      )
    },
    libraryDependencies ++= {
      lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux")   => "linux"
        case n if n.startsWith("Mac")     => "mac"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
      }
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
    }
  )
