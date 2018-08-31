# a RESTful service

### What does this app do?

This webservice stores users with books related to them.
For populating the database it uses a `CommandLineRunner` bean what saves 
6 users and 3 books to each user in a H2 in memory database.

The root url for the app is `/books` where we can see the available users as
hypermedia driven outputs, with links to relevant operations
```
{
 "_links": {
    "rnemeth": {
        "href": "http://localhost:8080/books/rnemeth"
    },
     "ebotos": {
        "href": "http://localhost:8080/books/ebotos"
     },
     "bkocsis": {
        "href": "http://localhost:8080/books/bkocsis"
     },
     "edroberts": {
        "href": "http://localhost:8080/books/edroberts"
     },
     "takovacs": {
        "href": "http://localhost:8080/books/takovacs"
     },
     "glaszlo": {
        "href": "http://localhost:8080/books/glaszlo"
     }
  }
}
```

After navigating to the a given user, we can see the related books 
with the available actions
```
{
    "_embedded": {
        "bookList": [
            {
                "id": 2,
                "description": "Book 1",
                "_links": {
                    "books": {
                        "href": "http://localhost:8080/books/rnemeth"
                    },
                    "self": {
                        "href": "http://localhost:8080/books/rnemeth/2"
                    }
                }
            },
                {
                "id": 3,
                "description": "Book 2",
                "_links": {
                    "books": {
                        "href": "http://localhost:8080/books/rnemeth"
                    },
                    "self": {
                        "href": "http://localhost:8080/books/rnemeth/3"
                    }
                }
            },
                {
                "id": 4,
                "description": "Book 3",
                "_links": {
                    "books": {
                        "href": "http://localhost:8080/books/rnemeth"
                    },
                    "self": {
                        "href": "http://localhost:8080/books/rnemeth/4"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/books/rnemeth"
        }
    }
}
```

For adding a book to an existing user you need to send POST request (e.g.: 
`http://localhost:8080/books/rnemeth`) with JSON containing a `description` element
```
{
    "description":"sample description"
}
```

In those cases when a `Book`/`User` is not found an exception is thrown and 
a `ControllerAdvice` advice creates a hypermedia-supporting error container 
`VndError` which wraps these exceptions and express the response in JSON. 

## How to launch the applicaiton
From the root folder run the following command 

`mvnw clean spring-boot:run`
