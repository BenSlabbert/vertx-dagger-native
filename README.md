# Vert.x + Dagger + GraalVM

Build with maven

```shell
mvn clean install
```

Then build the native image

```shell
mvn -Pnative package
```

The binary can be executed:

```shell
./target/main run --conf '{"http.port":8081}' github.benslabbert.vdnative.verticle.MainVerticle --instances 2
```

```shell
java -jar target/example-uber.jar
```

```shell
java -agentlib:native-image-agent=config-output-dir=${pwd},config-write-period-secs=1,config-write-initial-delay-secs=0 -jar target/example-uber.jar
```

```shell
mvnd clean verify -Pnative
```

```shell
mvnd clean verify -Pnative-instrument
```

