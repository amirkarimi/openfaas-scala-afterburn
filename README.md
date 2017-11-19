# Scala OpenFaaS Afterburn

Scala sample running in Fast Fork mode on [OpenFaaS](openfaas.com)

## Build and Deploy to OpenFaaS

* Deploy [OpenFaaS](https://github.com/openfaas/faas#get-started-with-openfaas)

* Install [OpenFaaS CLI](https://github.com/openfaas/faas-cli#get-started-install-the-cli)

* Get the template and create the function and run the sample

```
faas-cli template pull https://github.com/amirkarimi/openfaas-scala-afterburn
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

The default template contains a sample JAR file. You can create your own JAR file and replace with the with that

* Clone the sample project and go to the project dir

```
git clone https://github.com/amirkarimi/openfaas-scala-afterburn
cd function-src
```

* Build the source:

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

This is useful for testing/debugging purposes.

* Build the new version of watchdog supporting Afterburn. Navigate to another dir.

```
git clone git@github.com:openfaas-incubator/of-watchdog.git \
cd of-watchdog \
./build.sh
```

* Copy `of-watchdog` to a where the main JAR file exists

* Run watchdog:

```
mode=afterburn fprocess="java -jar app.jar" ./of-watchdog
```

* Send the requests to `localhost:8081`
