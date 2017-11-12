import java.io.{BufferedWriter, OutputStreamWriter}
import scala.io.Source
import com.openfaas.HttpParser

object Main {
  def main(args: Array[String]): Unit = {
    val source = Source.fromInputStream(System.in)
    val out = new BufferedWriter(new OutputStreamWriter(System.out))

    while(true) {
      HttpParser.acceptIncoming(source, out)
    }
  }
}
