# Mobile-Demo

This is a sandbox project primarily used for demonstrating different technologies.


Currently there is only a gRPC go server configured and an empty android apk which uses the gRPC client to communicate with the API.

## Done:
### Add boiler plate for gRPC server and Android Kotlin app using gRPC client.
### Add Angular web project.
### Added envoy proxy need for web-grpc
### Dockerized api
### Add gRPC client to web.

## TODO:
### Add TLS support.
### Add postgres and migration files to api.
### Add REST server.
### Add Authentication (manage user creation / authentication ourselves? Implement OAuth? Implement SAML and use an IdP like keycloak? Allow users to select a client and key for authentication?).
### Configure single activity architecture in android app using navigation graphs to route between fragments.
### Add repository and room DB to Android.
### Develop offline first architecture.
### Add retofit (w/ Kotlin serialization) and hilt to android project.
### Add boiler plate for latest angular + bootstrap version, including page routing.
### Add websocket streaming example from phone => api => web server.
### Add gRPC streaming example.
### Add web style themeing.
### Add android testing.
### Add web testing.
### Add api testing.
### Add build server for CI/CD (use jenkins).
### Add makefiles and dockerize build process.
