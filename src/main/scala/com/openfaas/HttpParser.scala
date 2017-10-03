package com.openfaas

import java.io.BufferedWriter
import function.Handler
import scala.io.Source

object HttpParser {
  def acceptIncoming(source: Source, out: BufferedWriter): Unit = {
    val maybeHttpHeader = HttpHeader.parse(source)

    maybeHttpHeader match {
      case None => System.err.println("Couldn't parse http header")
      case Some(httpHeader) =>
        System.err.println(s"${httpHeader.method} method")
        httpHeader.contentLength.map { contentLength =>
          System.err.println(s"${contentLength} bytes")
        }

        val body = source.mkString
        val response = Handler.function(body, httpHeader.method)

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
