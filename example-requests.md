##Example GraphQL Requests

Using Postman or a similar tool, execute these requests to test out the GraphQl endpoint. 
The single endpoint only accepts POST requests. 

*Endpoint: locahost:8000/v1/graphql*

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

#####Retrieve the names and types of all animals
```JSON
{
  "query":"{ animals { name type } }"
}
```

#####Retrieve all keepers and all animals
```JSON
{
  "query":"{ keepers { id name } animals { name type } }"
}
```