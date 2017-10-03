package com.openfaas

import java.io.{BufferedWriter, StringWriter}

import org.scalatest._

import scala.io.Source

class HttpParserSpec extends FlatSpec with Matchers {
  "The HttpParser" should "parse input and give correct response" in {
    val content = "Hi test!"
    var sourceStr =
      s"""|GET /some/url/ HTTP/1.1
         |Host: google.com
         |User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
         |Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
         |Accept-Language: en-us,en;q=0.5
         |Accept-Encoding: gzip,deflate
         |Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
         |Keep-Alive: 300
         |Pragma: no-cache
         |Cache-Control: no-cache
         |Content-Length: ${content.length}
         |
         |${content}""".stripMargin

    val source = Source.fromString(sourceStr)
    val writer = new StringWriter()
    val out = new BufferedWriter(writer)

    HttpParser.acceptIncoming(source, out)

    val expectedBody = s"Hi from your Scala function. You said: $content"
    val expectedOutput =
      s"""|HTTP/1.1 200 OK
         |Content-Length: ${expectedBody.length}
         |Connection: Close
         |Content-Type: text/plain
         |
         |${expectedBody}""".stripMargin

    writer.toString shouldBe expectedOutput
  }
}
