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
    scalaVersion := scala3Version,
    libraryDependencies ++= {
      Seq(
        "org.scalatest" %% "scalatest" % "3.2.9" % Test,
        "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
      )
    }
  )
