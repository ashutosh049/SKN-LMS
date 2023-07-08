# SKN LMS:: skn_lms_fabrics
Online store where we can browse fabrics

### Tech Stack:

|Tech stack | Version        |
|----------|----------------|
|Java| `17`           |
|Spring Boot| `3.1.1`        |
|Spring Framework| `6.0.10`       |
|MySql| `8`            |
|Api Doc|  `OpenAPI 3.0` |


> NOTE: Spring boot 3 has issues with Springfox' Swagger, we had to use OpenAPI

### Modules:
1. Exception library
2. Fabric service


### How to run the project
1. Clone project
2. Build microservice `skn_lm_exception`
   1. Go to project location `SKN-LMS/skn_lm_exception`
   2. Execute `mvn clean install`
3. Build Catalog Fabric Service
   1. Go to `SKN-LMS/skn_lms_fabrics`
   2. Execute `mvn clean install`
4. Start the service as:
   1. Go to `SKN-LMS/skn_lms_fabrics/target/`
   2. Execute `java -jar skn_lms_fabrics-1.0-SNAPSHOT-exec.jar`

## Api/Swagger Doc: 
[skn-lms-fabrics/swagger-ui](http://localhost:3000/skn-lms-fabrics/swagger-ui/index.html)

![1.PNG](static/swagger/1.PNG)

![2.PNG](static/swagger/2.PNG)


## Actuator Endpoints:
1. [info](http://localhost:3000/skn-lms-fabrics/actuator/info)
2. [health](http://localhost:3000/skn-lms-fabrics/actuator/health)

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:3000/skn-lms-fabrics/actuator",
      "templated": false
    },
    "health": {
      "href": "http://localhost:3000/skn-lms-fabrics/actuator/health",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:3000/skn-lms-fabrics/actuator/health/{*path}",
      "templated": true
    },
    "info": {
      "href": "http://localhost:3000/skn-lms-fabrics/actuator/info",
      "templated": false
    }
  }
}
```

### Steps to access the apis
#### Fabric Colors
Create few colors as:

```shell
curl --location 'http://localhost:3000/skn-lms-fabrics/v1/fabric-color' \
--header 'Content-Type: application/json' \
--data '{
    "code":"BLACK",
    "description":"Black color",
    "status":"AVAILABLE"
}'

> 201 Created
```

#### Materials
Create few materials as:

```shell
curl --location 'http://localhost:3000/skn-lms-fabrics/v1/fabric-material' \
--header 'Content-Type: application/json' \
--data '{
    "code":"LEATHER",
    "description":"Leather type material",
    "status":"AVAILABLE"
}'
> 201 Created
```

#### Fabric

Create a Fabric as:
```shell
curl --location 'http://localhost:3000/skn-lms-fabrics/v1/fabric' \
--header 'Content-Type: application/json' \
--data '{
    "code":"FABRIC-7",
    "description":"Fabric 7",
    "status":"AVAILABLE",
    "price": 750.00,
    "color-code":"ORANGE",
    "materials": [
        {
            "material-code":"VELVET",
            "percent": 20.00
        },
        {
            "material-code":"KHADI",
            "percent": 30.00
        },
        {
            "material-code":"LINEN",
            "percent": 50.00
        }
    ]
}'

> 201 Created
```

Search a Fabric as :

```shell
curl -X 'GET' \
  'http://localhost:3000/skn-lms-fabrics/v1/fabric?color-code=RED&fabric-status%5B%5D=AVAILABLE&sort-by=FABRIC_PRICE&page-number=0&per-page=10&sort-order=ASC' \
  -H 'accept: */*'  
```

Response:
```json
{
   "paginator": {
      "pageNo": 0,
      "itemPerPages": 1,
      "totalItems": 1,
      "hasNext": false,
      "hasPrevious": false,
      "isFirst": true,
      "isLast": true,
      "first": true,
      "last": true
   },
   "data": [
      {
         "fabricId": 1,
         "fabricCode": "RED",
         "description": "Red color",
         "price": 1000,
         "fabricStatus": "AVAILABLE",
         "fabricColorDto": {
            "fabricColorId": 1,
            "fabricColorCode": "RED",
            "description": "Red color",
            "status": "AVAILABLE"
         },
         "fabricMaterialDtos": [
            {
               "fabricMaterialId": 1,
               "percentage": 50,
               "material": {
                  "materialId": 5,
                  "materialCode": "LINEN",
                  "description": "Linen type material",
                  "status": "AVAILABLE"
               }
            },
            {
               "fabricMaterialId": 2,
               "percentage": 50,
               "material": {
                  "materialId": 3,
                  "materialCode": "SILK",
                  "description": "SILK type material",
                  "status": "AVAILABLE"
               }
            }
         ]
      }
   ]
}
```

