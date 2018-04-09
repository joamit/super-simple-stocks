# super-simple-stocks
A spring boot based web service to perform super simple stock operations. The restful api is exposed using Swagger springfox implementation for ease of access and testability.
Spring rest repositories and H2 in-memory database has been used to perform domain related CRUD operations and to make functional queries. 
Application is initialized with dummy stock and trade information during the startup.

## Getting Started with this application in Intellij

### prerequisites
The following items should be installed in your system:
* [Java Platform (JDK) 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)
* [Intellij Community/Ultimate](https://www.jetbrains.com/idea/)

### Steps:

1) In the command line
```
mkdir super-simple-stocks
cd super-simple-stocks
git clone https://github.com/joamit/super-simple-stocks.git
```
2) Inside Intellij
```
VCS -> Checkout From Version Controller -> github -> https://github.com/joamit/super-simple-stocks.git
```
3) Start SuperSimpleStocksApplication.java as main java class.

4) Access following link in your browser
```
http://localhost:8081/swagger-ui.html 
```


## Important Links

### Swagger UI
```
http://localhost:7777/swagger-ui.html
```

## Built With

* [Spring](http://spring.io/) - The Spring Framework
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors
* **Amit Joshi** - (https://github.com/joamit)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

