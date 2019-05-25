name := "ZooKeeperScala"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.19",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.19" % Test,
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
  "com.novocode" % "junit-interface" % "0.11" % Test,
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalafx" %% "scalafx" % "8.0.192-R14",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)