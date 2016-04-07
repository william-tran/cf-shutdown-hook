# cf-shutdown-hook

Inspired by https://github.com/scottfrederick/cf-shutdown-hook but removes the dependency on how logging behaves for an app that's shutting down.

The test consists of 2 apps, a Spring Boot app `shutdown` which makes requests to a Static Buildpack app `listener-for-shutdown`. The magic is here: [ShutdownHookApplication.java](src/main/java/com/example/ShutdownHookApplication.java)

## Instructions
1. `./mvnw package`
2. `cf push`
3. `cf logs listener-for-shutdown`
4. In another terminal, `curl shutdown.cfdomain.com` and verify in the `listener-for-shutdown` logs that `shutdown` can make calls to it.
5. Run `shutdown` locally with `./mvnw spring-boot:run -Dshutdown.uri=shutdown.cfdomain.com` and when it's started up, hit Ctrl-C to send a `SIGTERM`. Verify in the `listener-for-shutdown` logs that it received a request from the app.
6. `cf stop shutdown`. Verify in the `listener-for-shutdown` logs that it received a request from `shutdown`.
