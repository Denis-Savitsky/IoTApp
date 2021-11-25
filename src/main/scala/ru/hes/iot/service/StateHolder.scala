package ru.hes.iot.service

import logstage.{IzLogger, LogIO3, LogZIO}
import logstage.LogZIO.log
import ru.hes.iot.model.{Condition, Mode, MoveType}
import zio.{Has, Ref, UIO, ULayer, URIO, ZIO}

trait StateHolder {

  def setState(mode: Mode, moveType: MoveType): UIO[Unit]

  def getState(): UIO[Condition]

}

class StateHolderLive(condition: Ref[Condition], logger: LogZIO.Service) extends StateHolder {
  override def setState(mode: Mode, moveType: MoveType): UIO[Unit] =
    logger.info(s"Setting state $mode $moveType") *>
      condition.set(Condition(mode, moveType))

  override def getState(): UIO[Condition] =
    for {
      state <- condition.get
      _ <- logger.info(s"Getting state $state")
    } yield state
}

object StateHolderLive {
  def layer: ULayer[Has[StateHolder]] = (for {
    condition <- Ref.make(Condition(Mode.Auto, MoveType.Default))
    logger: LogZIO.Service = LogZIO.withFiberId(IzLogger())
  } yield new StateHolderLive(condition, logger)).toLayer
}

object StateHolder {
  def getState(): URIO[Has[StateHolder], Condition] =
    ZIO.serviceWith[StateHolder](_.getState())

  def setState(mode: Mode, moveType: MoveType): URIO[Has[StateHolder], Unit] =
    ZIO.serviceWith[StateHolder](_.setState(mode, moveType))
}

