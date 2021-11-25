package ru.hes.iot.model

import enumeratum.EnumEntry.LowerCamelcase
import enumeratum.{CirceEnum, Enum}


sealed trait MoveType extends LowerCamelcase

object MoveType extends Enum[MoveType] with CirceEnum[MoveType] {

  final case object Default extends MoveType

  final case object Left extends MoveType

  final case object Right extends MoveType

  final case object Forward extends MoveType

  final case object Back extends MoveType

  final case object Stop extends MoveType

  final case object LeftTurn extends MoveType

  final case object RightTurn extends MoveType

  override def values: IndexedSeq[MoveType] = findValues
}