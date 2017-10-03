# Scala OpenFaaS fast-fork

Scala sample running in Fast Fork mode on [OpenFaaS](openfaas.com)

* For notes on how this works please see the `Optimizing for performance` section of the Watchdog notes:

https://github.com/alexellis/faas/tree/fast_fork/watchdog

* Video showing this in action:

https://www.youtube.com/watch?v=gG6z-4a1gpQ&feature=youtu.be

* Work with the sample:

Build the watchdog with the code in the `fast_fork` branch in the faas repo.

Edit the code in the `function` folder in this sample.

## Generate JAR file

1. Install JDK 8
2. Install [SBT](http://www.scala-sbt.org/download.html)
3. run `sbt assembly`. It generates the jar file at `target/scala-2.12/openfaas-scala-afterburn-assembly-[version].jar`
