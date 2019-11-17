# QUarkus Using OpenID Connect -Keycloak

In this example, we build a very simple microservice which offers angular UI two endpoints:
* `http://localhost:4200`
* `http://localhost:8080/api/users/me`
* `http://localhost:8080/api/users/`

These endpoints are protected and can only be accessed if a client is sending a bearer token along with the request, which must be valid (e.g.: signature, expiration and audience) and trusted by the microservice.

The bearer token is issued by a Keycloak Server and represents the subject to which the token was issued for.
For being an OAuth 2.0 Authorization Server, the token also references the client acting on behalf of the user.

The `/api/users/me` endpoint can be accessed by any user with a valid token.
As a response, it returns a JSON document with details about the user where these details are obtained from the information carried on the token.
This endpoint is protected with RBAC (Role-Based Access Control) and only users granted with the `user` role can access this endpoint.

The `/api/users` endpoint is protected with RBAC (Role-Based Access Control) and only users granted with the `ROLE_ADMIN` role can access it.

This is a very simple example using RBAC policies to govern access to your resources.
However, Keycloak supports other types of policies that you can use to perform even more fine-grained access control.
By using this example, you'll see that your application is completely decoupled from your authorization policies with enforcement being purely based on the accessed resource.

## Requirements

To compile and run this demo you will need:

- JDK 1.8+
- GraalVM
- NodeJS
- Keycloak

### Configuring GraalVM and JDK 1.8+

Make sure that both the `GRAALVM_HOME` and `JAVA_HOME` environment variables have
been set, and that a JDK 1.8+ `java` command is on the path.

See the [Building a Native Executable guide](https://quarkus.io/guides/building-native-image)
for help setting up your environment.

## Building the application

Launch the Maven build on the checked out sources of this demo:

> ./mvnw package

## Starting Keycloak Server

To start a Keycloak Server you can use Docker and just run the following command:

```bash
docker-compose -f src/main/docker/docker-compose.yaml up -d
```

You should be able to access your Keycloak Server at http://localhost:8180/auth[localhost:9080/auth].

Log in as the `admin` user to access the Keycloak Administration Console.
Username should be `admin` and password `admin`.

For more details, see the Keycloak documentation about how to https://www.keycloak.org/docs/latest/server_admin/index.html#_create-realm[create a new realm].

### Live coding with Quarkus

The Maven Quarkus plugin provides a development mode that supports
live coding. To try this out:

####Backend(quarkus)
> ./mvnw compile quarkus:dev

This command will leave Quarkus running in the foreground listening on port 8080.

####Frontend(angular)
```bash
cd src/main/webapp/
npm install
npm start
```

This command will leave Angular app running in the foreground listening on port 4200.

### Run Quarkus in JVM mode

When you're done iterating in developer mode, you can run the application as a
conventional jar file. First compile it:

> ./mvnw package

Then run it:

> java -jar ./target/maxilog/quarkus-oidc-keycloak-1.0-SNAPSHOT-runner.jar

Have a look at how fast it boots, or measure the total native memory consumption.

### Run Quarkus as a native executable

You can also create a native executable from this application without making any
source code changes. A native executable removes the dependency on the JVM:
everything needed to run the application on the target platform is included in 
the executable, allowing the application to run with minimal resource overhead.

Compiling a native executable takes a bit longer, as GraalVM performs additional
steps to remove unnecessary codepaths. Use the  `native` profile to compile a
native executable:

> ./mvnw package -Dnative

After getting a cup of coffee, you'll be able to run this executable directly:

> ./target/maxilog/quarkus-oidc-keycloak-1.0-SNAPSHOT-runner