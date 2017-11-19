package com.openfaas

import java.io.{BufferedWriter, DataInputStream}

import function.Handler

object HttpParser {
  def acceptIncoming(in: DataInputStream, out: BufferedWriter): Unit = {
    val iter = DataInputStreamHelper.toIterator(in)

    val maybeHttpHeader = HttpHeader.parse(iter)

    val response = maybeHttpHeader match {
      case None =>
        System.err.println("Couldn't parse http header")
        HttpResponse(
          status = "400 Bad Request",
          body = "Couldn't parse http header",
          contentType = Some("text/plain")
        )
      case Some(httpHeader) =>
        System.err.println(s"${httpHeader.method} method")
        httpHeader.contentLength.foreach { contentLength =>
          System.err.println(s"$contentLength bytes")
        }

        val response = Handler.function(iter, httpHeader)

        HttpResponse(
          status = "200 OK",
          body = response,
          contentType = Some("text/plain")
        )
    }

    response.serialize(out)
    out.flush()
  }
}
