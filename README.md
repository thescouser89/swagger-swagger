swagger-swagger
===============

The swagger-swagger project is used to show a swagger-resteasy example project,
and how to integrate swagger with Resteasy in the Wildfly application server. It
probably works with other application servers too.

## What is swagger anyways?

Swagger is a library used to show pretty REST documentation of your REST
endpoints in your library. See http://swagger.io/ for more!

## How do I run this anyways?

```
# Start the Wildfly application server locally
# Then compile this application and deploy it on the local Wildfly server
mvn install && mvn wildfly:deploy
```

You will be able to access the Swagger documentation in
http://localhost:8080/swagger-swagger/dist

The REST endpoint for the Swagger API is:
http://localhost:8080/swagger-swagger/rest/api-docs


## Swagger UI

The Swagger UI found in `src/main/webapp/dist` is obtained from the `dist`
folder of Swagger UI (https://github.com/swagger-api/swagger-ui). A change was
made in the `index.html` so that the url points to our 'api-docs' instead of the
example used in their `index.html`.


Technologies used:

- JavaEE 7 (via Wildfly 8.2.0)
- JPA (via Hibernate)
- JAX-RS (via Resteasy)
- Reflections library
- Swagger library

\#swag
