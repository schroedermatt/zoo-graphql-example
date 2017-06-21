##Example Requests

Using Postman or a similar tool, execute these requests to test out the GraphQl endpoint.

Endpoint: locahost:8000/v1/graphql

#####Retrieve the names of all keepers and the names of their animals
```JSON
{
  "query":"{ keepers { name animals { name } } }"
}
```

#####Retrieve the names of all keepers and the id and type of their animals
```JSON
{
  "query":"{ keepers { name animals { id type } } }"
}
```