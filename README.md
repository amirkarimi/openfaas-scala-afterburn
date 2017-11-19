# Scala OpenFaaS fast-fork

Scala sample running in Fast Fork mode on [OpenFaaS](openfaas.com)

## Prerequisites

* JDK 8
* [SBT](http://www.scala-sbt.org/download.html)


## Build and Deploy to OpenFaaS

* Build the new version of watchdog supporting Afterburn. Navigate to another dir.

```
git clone git@github.com:openfaas-incubator/of-watchdog.git \
cd of-watchdog \
./build.sh
```

* Copy `of-watchdog` to `openfaas` dir of the current project.

* Build and copy the JAR file to `openfaas` 
```
sbt assembly && cp target/scala-2.12/openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar openfaas/
```

* [Deploy OpenFaaS](https://github.com/openfaas/faas#get-started-with-openfaas)

* Install [openfaas-cli](https://github.com/openfaas/faas-cli).

* Build and deploy the docker image

```
faas-cli build -f openfaas.yml && faas-cli deploy -f openfaas.yml
```

* Navigate to `http://127.0.0.1:8080/ui/` and run `scala_fastburn` function 

## Run watchdog locally

This is useful for testing/debugging purposes.

To run the watchdog locally:

* Build and copy `of-watchdog` and `JAR` file as described in last section. 

* Run watchdog:

```
mode=afterburn fprocess="java -jar openfaas-scala-afterburn-assembly-0.1.0-SNAPSHOT.jar" ./of-watchdog
```

* Send the requests to `localhost:8081`
