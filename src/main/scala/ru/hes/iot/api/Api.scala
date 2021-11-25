package ru.hes.iot.api

import cats.syntax.all._
import org.http4s.HttpRoutes
import ru.hes.iot.model.Condition
import ru.hes.iot.service.StateHolder
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.openapi.OpenAPI
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import sttp.tapir.swagger.SwaggerUI
import sttp.tapir.ztapir._
import zio.blocking.Blocking
import zio.clock.Clock
import zio.interop.catz._
import zio.{Has, RIO}

object Api {

  val setEndpoint: ZServerEndpoint[Has[StateHolder], Any] =
    endpoint
      .post
      .in("command")
      .in(jsonBody[Condition])
      .zServerLogic(cond => StateHolder.setState(cond.mode, cond.move))

  val getEndpoint: ZServerEndpoint[Has[StateHolder], Any] =
    endpoint
      .get
      .in("command")
      .out(jsonBody[Condition])
      .zServerLogic(_ => StateHolder.getState())


  val routes = List(setEndpoint, getEndpoint)
  val serverRoutes = ZHttp4sServerInterpreter().from(routes).toRoutes
  val openApiDocs: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(routes.map(_.endpoint), "Droid API", "0.1")
  val openApiYml: String = openApiDocs.toYaml

  val swaggerUIRoutes: HttpRoutes[RIO[Has[StateHolder] with Clock with Blocking, *]] = ZHttp4sServerInterpreter().from(SwaggerUI[RIO[Has[StateHolder] with Clock with Blocking, *]](openApiYml)).toRoutes


  val allRoutes = serverRoutes <+> swaggerUIRoutes

}
