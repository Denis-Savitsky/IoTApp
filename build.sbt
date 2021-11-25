
val app = (project in file("."))
  .settings(
    name := "IoTApp",
    version := "0.1.1" ,
    scalaVersion := "2.13.7"
  )
  .settings(
    libraryDependencies ++= Dependencies.allDeps,
    libraryDependencies += "com.beachape" %% "enumeratum-circe" % "1.7.0"
  )
  .settings(
    addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)
  )
  .settings(
    scalacOptions += "-Ymacro-annotations"
  )
  .settings(
    Compile / run / mainClass := Some("ru.hes.iot.Main")
  )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    dockerBaseImage := "adoptopenjdk:11-jre-hotspot",
    Docker / packageName := "iot-service",
    Docker / version := "0.1.1",
    dockerExposedPorts := Seq(8080),
    dockerRepository := Some("dsavitsky96"),
    dockerUpdateLatest := true,
    normalizedName := "iot-service",
  )
