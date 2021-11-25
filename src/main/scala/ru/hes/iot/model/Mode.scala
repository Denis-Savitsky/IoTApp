package ru.hes.iot.model

import enumeratum.EnumEntry.LowerCamelcase
import enumeratum.{CirceEnum, Enum}

sealed trait Mode extends LowerCamelcase

object Mode extends Enum[Mode] with CirceEnum[Mode] {
  final case object Auto extends Mode

  final case object Manual extends Mode

  override def values: IndexedSeq[Mode] = findValues
}
