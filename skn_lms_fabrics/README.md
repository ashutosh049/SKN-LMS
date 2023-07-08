# skn_lms_fabrics
Online store where we can browse fabrics

Swagger Doc: http://localhost:3000/skn-lms-fabrics/swagger-ui/index.html

Actuator Endpoints:
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


