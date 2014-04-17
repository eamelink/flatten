name := "flatten-basics"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  // Typeclass instances for Future. Not necessary for Scalaz 7.1.
  "org.typelevel" %% "scalaz-contrib-210" % "0.1.5")

