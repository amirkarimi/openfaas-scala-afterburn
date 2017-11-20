# Scala OpenFaaS Afterburn Sample

Scala sample running in Fast Fork mode on [OpenFaaS](openfaas.com). To be used in combination with [OpenFaaS JVM template](https://github.com/amirkarimi/openfaas-jvm-afterburn-template).

## Build and Deploy to OpenFaaS

NOTE: Under development!

* Deploy [OpenFaaS](https://github.com/openfaas/faas#get-started-with-openfaas)

* Install [OpenFaaS CLI](https://github.com/openfaas/faas-cli#get-started-install-the-cli)

* Get the template and create the function and run the sample

```
faas-cli template pull https://github.com/amirkarimi/openfaas-jvm-afterburn-template
faas-cli new --lang scala-afterburn scala_burner

faas-cli build -f scala_burner.yml
faas-cli deploy -f scala_burner.yml
echo test | faas-cli invoke scala_burner
```

## Update the function

### Prerequisites

* JDK 8
* [SBT](http://www.scala-sbt.org/download.html)

### Build and deploy the function

The default template contains a sample JAR file. You can create your own JAR file and replace with the sample one.

* Clone the sample project

```
git clone https://github.com/amirkarimi/openfaas-scala-afterburn
```

* Build and generate the JAR file using SBT

```
sbt assembly
```

The JAR file can be found at `target/scala-2.12/openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar`

* Copy the JAR file to your OpenFaaS function dir (e.g. `scala_burner`) and rename it to `app.jar`

```
cp target/scala-2.12/openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar ../scala_burner/app.jar
```

* Build and deploy again (inside the OpenFaaS function dir)

```
faas-cli build -f scala_burner.yml
faas-cli deploy -f scala_burner.yml
```

## Run watchdog locally

You can [run the the watchdog locally](https://github.com/amirkarimi/openfaas-scala-afterburn#run-watchdog-locally) which is useful for testing/debugging purposes.
