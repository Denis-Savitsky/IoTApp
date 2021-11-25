import sbt._

object Dependencies {

  object V {
    val tapir = "0.19.0"
    val logback = "1.2.7"
    val logstage = "1.0.8"
    val zio = "1.0.12"
  }

  lazy val zio = Seq("dev.zio" %% "zio" % V.zio)

  lazy val tapir = Seq("com.softwaremill.sttp.tapir" %% "tapir-zio" % V.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-zio-http4s-server" % V.tapir)

  lazy val `tapir-circe` = Seq(
    "com.softwaremill.sttp.client3" %% "circe" % "3.3.16",
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % V.tapir
  )

  lazy val swagger = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % V.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % V.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui" % V.tapir
  )

  lazy val derevo = Seq("tf.tofu" %% "derevo-circe" % "0.12.7")

  lazy val logback = Seq(
    "ch.qos.logback" % "logback-classic" % V.logback % Runtime,
    "ch.qos.logback" % "logback-core" % V.logback % Runtime
  )

  lazy val slf4j = Seq("org.slf4j" % "slf4j-api" % "1.7.32")

  lazy val logstage = Seq(
    // LogStage core library
    "io.7mind.izumi" %% "logstage-core" % V.logstage,
    // Json output
    "io.7mind.izumi" %% "logstage-rendering-circe" % V.logstage,
    // Router from Slf4j to LogStage
    "io.7mind.izumi" %% "logstage-adapter-slf4j" % V.logstage,
  )

  lazy val allDeps =
    zio ++
      tapir ++
      `tapir-circe` ++
      swagger ++
      derevo ++
      logback ++
      slf4j ++
      logstage
}
