package function

import com.openfaas.HttpHeader

object Handler {
  def function(iter: Iterator[Char], header: HttpHeader) = {
    header.method match {
      case "POST" =>
        header.contentLength match {
          case None => "Hi from your Scala function. The content length is not specified."
          case Some(len) =>
            val str = iter.take(len).mkString
            System.err.println(s"body: ${str}")
            s"Hi from your Scala function. You said: $str"
        }

      case _ =>
        "Hi from your Scala function."
    }
  }
}
