import java.io.{BufferedWriter, DataInputStream, OutputStreamWriter}

import com.openfaas.HttpParser

object Main {
  def main(args: Array[String]): Unit = {
    val in = new DataInputStream(System.in)
    val out = new BufferedWriter(new OutputStreamWriter(System.out))

    while(true) {
      HttpParser.acceptIncoming(in, out)
    }
  }
}
