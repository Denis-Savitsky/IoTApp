package ru.hes.iot

import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import ru.hes.iot.api.Api
import ru.hes.iot.service.{StateHolder, StateHolderLive}
import zio._
import zio.blocking.Blocking
import zio.clock.Clock
import zio.interop.catz._

object Main extends App {

  val serve: ZIO[ZEnv with Has[StateHolder], Throwable, Unit] =
    ZIO.runtime[ZEnv with Has[StateHolder]].flatMap { implicit runtime => // This is needed to derive cats-effect instances for that are needed by http4s
      BlazeServerBuilder[RIO[Has[StateHolder] with Clock with Blocking, *]]
        .withExecutionContext(runtime.platform.executor.asEC)
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(Router("/" -> Api.allRoutes).orNotFound)
        .serve
        .compile
        .drain
    }

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = serve.provideCustomLayer(StateHolderLive.layer).exitCode
}
