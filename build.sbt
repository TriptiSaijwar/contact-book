import NativePackagerHelper._
name := """contact-book"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(SbtWeb).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.10.Final"
)

libraryDependencies += "mysql" %  "mysql-connector-java" % "5.1.35"
libraryDependencies += evolutions
libraryDependencies += "org.webjars.bower" % "angularjs" % "1.6.5"
libraryDependencies += "org.webjars.bower" % "angular-ui-router" % "0.4.2"
libraryDependencies += "org.webjars.bower" % "angular-sanitize" % "1.4.7"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.5"
libraryDependencies += "org.webjars" % "jquery" % "2.1.4"
libraryDependencies += "org.webjars" % "jquery-ui" % "1.11.4"
libraryDependencies += "org.webjars" % "requirejs" % "2.1.20"
libraryDependencies += "org.webjars.npm" % "alertify.js" % "1.0.10"

//redis dependency
libraryDependencies += "com.typesafe.play.modules" %% "play-modules-redis" % "2.4.1"

resolvers += "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk"

resolvers ++= Seq(
  //"Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "Sonatype snapshots repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Pablo repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo/"
)

//https://github.com/sbt/sbt-less

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

LessKeys.compress := true

//For persistence.xml
PlayKeys.externalizeResources := false

pipelineStages := Seq(uglify)

UglifyKeys.compress in Assets := true
UglifyKeys.mangle in Assets := true
excludeFilter in uglify := (excludeFilter in uglify).value || GlobFilter("*.min.js")
includeFilter in uglify := GlobFilter("javascripts/*.js")
