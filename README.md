# sample-service
### Spring Boot sample service to show how to integrate with CAPI

## Sample how to self register on Consul

#### Dependencies
```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-consul</artifactId>
  <version>${org.springframework.cloud-version}</version>
</dependency>
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-consul-discovery</artifactId>
  <version>${org.springframework.cloud-version}</version>
</dependency>
```

#### Application Properties for registering on Consul (and be exposed on CAPI)
```yaml
spring:
  application:
    name: sample-service
  cloud:
    consul:
      enabled: true
      port: 8500
      host: http://localhost
      discovery:
        instance-id: ${info.app.environment}-${host}-${server.port}
        instance-group: ${info.app.environment}
        scheme: http
        hostname: localhost
        port: 8082
        tags: Category=, Owner=
        metadata:
          group: dev
          # Your Open API Definition Endpoint
          open-api: http://${host}:${server.port}/v3/api-docs/sample-services
          # If you want CAPI to globally protect your API set secured to `true`
          # If `false` CAPI will honor the security defined in the Open API definition
          secured: false
          # If you want CAPI to check a REGO policy on OPA you can define here the REGO name.
          opa-rego: capi/smk_api/dev
          #allowed-origins: http://localhost, http://xpto.com
          #ingress: 
          #schema: https
          #keep-group: 
          #subscription-group: 
          #throttle-max-calls: 5
          #throttle-period-in-seconds: 10
          #throttle-break: true
```
#### Use Open API 3 to define the authorization to your service.
```java
//Allow anonymous access
@GetMapping(path = "/sample/item", produces = MediaType.APPLICATION_JSON_VALUE)
@Operation(summary = "Get all Items", description = "Open")
public ResponseEntity<Set<CacheEntry<String, Item>>> getItems() {}

//Requires a bearer token
@PostMapping(path = "/sample/item", produces = MediaType.APPLICATION_JSON_VALUE)
@Operation(summary = "Create an Item", description = "Only authenticated applications!", security = {
            @SecurityRequirement(name = "bearerToken")})
public ResponseEntity<Item> createItem(@RequestBody Item item) {}
```
##### Result in the Definition (read by CAPI)
```json
{
  "openapi": "3.0.1",
  "info": {
    "title": "Open API Sample Service",
    "description": "Sample Service"
  },
  "paths": {
    "/sample/item": {
      "get": {
        "tags": [
          "Item Service"
        ],
        "summary": "Get all Items",
        "description": "Open"
      },
      "post": {
        "tags": [
          "Item Service"
        ],
        "summary": "Create an Item",
        "description": "Open",
        "security": [
          {
            "bearerToken": []
          }
        ]
      }
    }
  }
}
```
