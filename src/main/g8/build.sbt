name := "$name$"

version := "$version$"

scalaVersion := "$scala_version$"

seq(sbtassembly.Plugin.assemblySettings: _*)

seq(webSettings :_*)

jettyPort := $jetty_port$

libraryDependencies ++= Seq(
  //Casbah
  "com.mongodb.casbah" %% "casbah" % "2.1.5.0",
  //Logging
  //"org.slf4j" % "slf4j-nop" % "1.6.0" % "runtime",
  //Scalatra
  "org.scalatra" %% "scalatra" % "2.0.0.M4",
  "org.scalatra" %% "scalatra-specs2" % "2.0.0.M4" % "test",
  "org.scalatra" %% "scalatra-test" % "2.0.0.M4" % "test",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  //Akka
  "se.scalablesolutions.akka" % "akka-actor" % "1.1.3",
  "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
  "net.liftweb" %% "lift-json" % "2.4-M2",
  //Jetty
  "org.mortbay.jetty" % "servlet-api" % "3.0.20100224" % "provided",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
  "org.mortbay.jetty" % "jetty-util" % "6.1.22" % "jetty",
  "org.mortbay.jetty" % "jetty" % "6.1.22",
  "org.mortbay.jetty" % "jetty-util" % "6.1.22",
  //specs testing
  //"org.specs2" %% "specs2" % "1.5" % "test",
  //"org.specs2" %% "specs2-scalaz-core" % "6.0.RC2" % "test"
  "net.databinder" %% "dispatch-http" % "0.8.4",
  "com.recursivity" %% "recursivity-commons" % "0.5.3",
  "org.slf4j" % "jcl-over-slf4j" % "1.5.10"
)

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Web plugin repo" at "http://siasia.github.com/maven2",
  "Akka Repo" at "http://akka.io/repository",
  "repo.novus rels" at "http://repo.novus.com/releases/",
  "repo.novus snaps" at "http://repo.novus.com/snapshots/",
  "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"
)

testOptions in Test += Tests.Setup( loader => {
  val oldEnv = System.getProperty("env")
  System.setProperty("env", "test")
  val load = loader.loadClass("com.$domain_name$.$product_name$.$name$.boot.Initializer").newInstance()
  if (oldEnv != null) { System.setProperty("env", oldEnv) }
} )

testOptions := Seq(Tests.Filter(s =>
  Seq("Spec", "Suite", "Unit", "all").exists(s.endsWith(_))))