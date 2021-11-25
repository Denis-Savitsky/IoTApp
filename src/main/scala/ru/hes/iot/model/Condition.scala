package ru.hes.iot.model

import derevo.circe.{decoder, encoder}
import derevo.derive

@derive(decoder, encoder)
case class Condition(mode: Mode, move: MoveType)

