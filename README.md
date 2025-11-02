# Vert.x + Dagger + GraalVM

Build with maven

```shell
mvn clean install
```

## Native Image

Run instrumented binary and specify the output path to the iprof file.

```shell
./target/main run --conf '{"http.port":8081}' github.benslabbert.vdnative.verticle.MainVerticle --instances 2 -XX:ProfilesDumpFile=/path/to/p.iprof
```

Run fat JAR

```shell
java -jar target/example-uber.jar
```

Run with native agent to generate reachability metadata.

```shell
java -agentlib:native-image-agent=config-output-dir=${pwd},config-write-period-secs=1,config-write-initial-delay-secs=0 -jar target/example-uber.jar
```

Build with instrumentation

```shell
mvnd clean verify -Pnative
```

Run with previous iprof

```shell
mvnd clean verify -Pnative-instrument
```

Ping the server

```shell
curl http://localhost:8081
```

## Layered Native Image

[TODO]

https://github.com/graalvm/graalvm-demos/tree/master/native-image/microservices/micronaut-hello-rest-maven-layered
https://github.com/oracle/graal/blob/master/substratevm/src/com.oracle.svm.core/src/com/oracle/svm/core/imagelayer/NativeImageLayers.md
