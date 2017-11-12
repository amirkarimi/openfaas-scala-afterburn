package function

import java.io.{File, FileOutputStream, PrintWriter}

import com.openfaas.HttpHeader

import scala.io.Source

object Handler {
  def function(input: Source, header: HttpHeader) = {
    header.method match {
      case "POST" =>
        header.contentLength match {
          case None => "Hi from your Scala function. The content length is not specified."
          case Some(len) =>

            val log = new PrintWriter( new PrintWriter(new FileOutputStream(
              new File("/home/amir/log.txt"),
              true /* append = true */))
            )

            log.println(s"len: ${len}")
            log.flush()

            var str = ""
            var i = 0
            // Blocks here receiving the second POST request!!!
            while(i < len && input.hasNext) {
              log.println(s"iter[${i}]")
              log.flush()
              str += input.next()
              i = i + 1
            }

            log.println(s"str: ${str}")
            log.flush()
            s"Hi from your Scala function. You said: $str"
        }

      case _ =>
        "Hi from your Scala function."
    }
  }
}
