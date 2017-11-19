package com.openfaas

import java.io.DataInputStream

object DataInputStreamHelper {
  def toIterator(in: DataInputStream): Iterator[Char] = {
    def readChar() = {
      val v = in.read()
      if (v == -1) {
        None
      } else {
        Some(v.asInstanceOf[Char])
      }
    }

    Iterator
      .continually(readChar())
      .takeWhile(_.nonEmpty)
      .flatten
  }
}
