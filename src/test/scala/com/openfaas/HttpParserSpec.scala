package com.openfaas

import java.io.{BufferedWriter, ByteArrayInputStream, DataInputStream, StringWriter}

import org.scalatest._

class HttpParserSpec extends WordSpec with Matchers {
  "The HttpParser" should {
    "parse input and give correct response" in {
      val content = "Hi test!"
      var sourceStr =
        s"""|POST /some/url/ HTTP/1.1
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

      val source = toDataInputStream(sourceStr)
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

    "parse root path" in {
      val content = "Hi test!"
      var sourceStr =
        s"""|POST  HTTP/1.1
            |Host: localhost:8081
            |Content-Type: application/x-www-form-urlencoded
            |
         |${content}""".stripMargin

      val source = toDataInputStream(sourceStr)
      val writer = new StringWriter()
      val out = new BufferedWriter(writer)

      HttpParser.acceptIncoming(source, out)

      val expectedBody = "Hi from your Scala function. The content length is not specified."
      val expectedOutput =
        s"""|HTTP/1.1 200 OK
            |Content-Length: ${expectedBody.length}
            |Connection: Close
            |Content-Type: text/plain
            |
         |${expectedBody}""".stripMargin

      writer.toString shouldBe expectedOutput
    }

    "parse several requests" in {
      val content1 = "Hi1"
      val content2 = "Hi2"
      var sourceStr =
        s"""|POST  HTTP/1.1
            |Host: localhost:8081
            |Content-Length: ${content1.length}
            |
            |${content1}POST  HTTP/1.1
            |Host: localhost:8081
            |Content-Length: ${content2.length}
            |
            |${content2}""".stripMargin

      val source = toDataInputStream(sourceStr)
      val writer = new StringWriter()
      val out = new BufferedWriter(writer)

      HttpParser.acceptIncoming(source, out)
      HttpParser.acceptIncoming(source, out)

      def expectedOutput(content: String) = {
        val expectedBody = s"Hi from your Scala function. You said: $content"
          s"""|HTTP/1.1 200 OK
              |Content-Length: ${expectedBody.length}
              |Connection: Close
              |Content-Type: text/plain
              |
              |${expectedBody}""".stripMargin
        }

      writer.toString shouldBe expectedOutput(content1) + expectedOutput(content2)
    }
  }

  def toDataInputStream(str: String) = new DataInputStream(
    new ByteArrayInputStream(str.getBytes)
  )

}
