# Vert.x + Dagger + Logback + GraalVM

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

