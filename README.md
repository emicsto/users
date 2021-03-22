# Users app
Application that retrieves user data from `https://api.github.com/users/{login}`, save in the database how many time request was made for a prticular user, does some magic calculations and then returns the result to the user.

## Requirements
*  [MySQL](https://dev.mysql.com/downloads/mysql/5.7.html)

## Installation
1. Clone repository
```bash
$ git clone https://github.com/emicsto/users.git
```

2. Go to project directory
```bash
$ cd users
```

3. Run the application
```bash
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--MYSQL_USERNAME=<<username>> --MYSQL_PASSWORD=<<password>> --MYSQL_HOST=<<host>> --MYSQL_DATABASE=<<database>>"
```
App should start on port `8080`.

## Endpoints
* GET `/users/{login}` – returns user info

## Documentation
[Swagger](https://swagger.io/) documentation is available at `/swagger-ui/index.html`
