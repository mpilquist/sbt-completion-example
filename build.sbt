import AssemblyKeys._

name := "SBT Completion Example"

version := "1.0"

scalaVersion := "2.9.2"

resolvers += "Typesafe" at "http://repo.typesafe.com/typesafe/repo"

// SBT 0.12.0-RC2 is only available in the Ivy Releases repository
resolvers += Resolver.url("Typesafe Ivy Releases", url("http://repo.typesafe.com/typesafe/repo"))(Resolver.ivyStylePatterns)

libraryDependencies ++=
  "org.scala-sbt" % "completion" % "0.12.0-RC2" ::
  "org.scala-sbt" % "logging" % "0.12.0-RC2" ::
  Nil

assemblySettings

jarName in assembly := "mud.jar"

