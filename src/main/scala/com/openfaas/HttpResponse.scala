package com.openfaas

import java.io.BufferedWriter

case class HttpResponse(
  status: String,
  body: String,
  contentType: Option[String]
) {
  def serialize(out: BufferedWriter): Unit = {
    out.write(serializeToString)
  }

  def serializeToString: String = {
    val baseStr =
      s"""|HTTP/1.1 $status
          |Content-Length: ${body.length}
          |Connection: Close
          |""".stripMargin

    val contentTypeStr = contentType
      .map(ct => s"Content-Type: $ct" + System.lineSeparator)
      .getOrElse("")

    baseStr + contentTypeStr + System.lineSeparator + body
  }
}