import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.openfaas",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "openfaas-scala-afterburn",
    libraryDependencies += scalaTest % Test,
    mainClass in assembly := Some("Main")
  )
