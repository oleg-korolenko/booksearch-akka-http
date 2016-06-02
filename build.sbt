name := "book-search"

organization := "com.okorolenko"

version := "SNAPSHOT-1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:_"
//  , "-Ylog-classpath"
)

libraryDependencies ++= {
  val akkaVersion = "2.4.2"
  val akkaStreamVersion = "2.0.3"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion withSources(),
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion withSources(),

    "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion withSources(),
    "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamVersion withSources(),
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion withSources(),

    "ch.qos.logback" % "logback-classic" % "1.1.3",

    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test" withSources(),
    "org.scalatest" %% "scalatest" % "2.2.5" % "test" withSources()

    //"com.typesafe.akka" % "akka-http-testkit-scala-experimental_2.11" % "1.0-RC2" withSources()
    //"com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test",
  )
}



//fork in run := true




    