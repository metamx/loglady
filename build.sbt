organization := "com.metamx"

name := "loglady"

version := "1.1.0-mmx"

description := "Crazy simple logging API for Scala."

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.10.6"

crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")

libraryDependencies ++= Seq(
  "org.slf4j"      %  "slf4j-api"       % "1.7.2",
  "ch.qos.logback" %  "logback-classic" % "1.0.0" % "test",
  "junit"          %  "junit"           % "4.11"  % "test"
)

libraryDependencies <++= scalaVersion {
  case x if x.startsWith("2.9.") => Seq(
    "org.specs2"     %% "specs2"          % "1.12.3" % "test"
  )
  case _ => Seq(
    "org.specs2"     %% "specs2-core"     % "3.8.5"  % "test",
    "org.specs2"     %% "specs2-junit"    % "3.8.5"  % "test"
  )
}

scalacOptions ++= Seq("-optimize", "-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

cancelable := true

testOptions in Test += Tests.Argument("console", "junitxml")

// Publishing

publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  } else {
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  }
}

pomIncludeRepository := { x => false }

pomExtra := (
  <url>http://github.com/dln/loglady</url>
  <scm>
    <url>git@github.com:dln/loglady.git</url>
    <connection>scm:git:git@github.com:dln/loglady.git</connection>
  </scm>
  <developers>
    <developer>
      <id>dln</id>
      <name>Daniel Lundin</name>
      <url>http://eintr.org/</url>
    </developer>
  </developers>)

releaseSettings

ReleaseKeys.publishArtifactsAction := PgpKeys.publishSigned.value
