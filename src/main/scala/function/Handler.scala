package function

import com.openfaas.HttpHeader

import scala.io.Source

object Handler {
  def function(input: Source, header: HttpHeader) = {
    header.method match {
      case "POST" =>
        header.contentLength match {
          case None => "Hi from your Scala function. The content length is not specified."
          case Some(len) =>

            System.err.println(s"len: ${len}")

            var str = ""
            var i = 0
            // Blocks here receiving the second POST request!!!
            while(i < len && input.hasNext) {
              System.err.println(s"iter[${i}]")
              str += input.next()
              i = i + 1
            }

            System.err.println(s"str: ${str}")
            s"Hi from your Scala function. You said: $str"
        }

      case _ =>
        "Hi from your Scala function."
    }
  }
}
