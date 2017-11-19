package com.openfaas

case class HttpHeader(
  method: String,
  headers: Map[String, String]
) {
  val contentLength: Option[Int] = headers.get("Content-Length").map(_.toInt)
}

object HttpHeader {
  def parse(iter: Iterator[Char]): Option[HttpHeader] = {
    def readLine() = iter
      .filter(_ != '\r')
      .takeWhile(_ != '\n')

    val headerLines = Iterator
      .continually(readLine().mkString)
      .takeWhile(_.nonEmpty)
      .toList

    parse(headerLines)
  }

  def parse(source: List[String]): Option[HttpHeader] = {
    val maybeMethod = source.headOption.flatMap(_.split(" ").headOption)
    val headers = source.drop(1).map { line =>
      // TODO: Better error handling
      val List(key, value) = line.split(":", 2).map(_.trim).toList
      (key, value)
    }.toMap

    maybeMethod.map { method =>
      HttpHeader(
        method = method,
        headers = headers
      )
    }
  }
}
