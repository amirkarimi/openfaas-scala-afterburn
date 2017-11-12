package com.openfaas

import java.io.BufferedWriter
import function.Handler
import scala.io.Source

object HttpParser {
  def acceptIncoming(source: Source, out: BufferedWriter): Unit = {
    val maybeHttpHeader = HttpHeader.parse(source)

    maybeHttpHeader match {
      case None =>
        System.err.println("Couldn't parse http header")
        val httpResponse = HttpResponse(
          status = "400 Bad Request",
          body = "Couldn't parse http header",
          contentType = Some("text/plain")
        )
      case Some(httpHeader) =>
        System.err.println(s"${httpHeader.method} method")
        httpHeader.contentLength.foreach { contentLength =>
          System.err.println(s"$contentLength bytes")
        }

        val response = Handler.function(source, httpHeader)

        val httpResponse = HttpResponse(
          status = "200 OK",
          body = response,
          contentType = Some("text/plain")
        )

        httpResponse.serialize(out)
        out.flush()
    }
  }
}
