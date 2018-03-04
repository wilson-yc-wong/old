# Project Title
This is an web application to show Top N ranking on internet web sites.

### Prerequisites
Maven
JDK8

### Installing
This project source code is managed by MAVEN and Spring Boot. So you can bring up web application by 
1) run command "$ mvn spring-boot:run". Default servlet container is Jetty.
2) run command "$mvn package" and then deploy to any application server.

Then you can visit the application by sample URL "http://localhost:8080/simple_report.html"

This is default application configuration by file /src/main/resources/app.properties
```
website.statistics.file.watch.source_folder=/tmp
website.statistics.file.watch.polling_interval=
website.statistics.file.watch.polling_max=

website.statistics.file.watch.polling_crontab=0 * * * * *
website.exclusion.watch.polling_crontab=0 0 * * * *
website.exclusion.watch.endpoint=http://private-1de182-mamtrialrankingadjustments4.apiary-mock.com/exclusions
```

## Getting Started
```
Architecture
Angularjs is front-end UI for user to query top ranking web sites.
Spring Boot Web is used to build Web and Restful controller.
Spring integration is used to aggregate File (web sites' visit count statistics data) and HTTP (exclusion list) to local Database
Memory Database H2 is configurated for testing purpose.
```
```
Implemented functional requirements
1) Interactive UI is provided to query ranking.
2) Source web site visits data file by configured path
3) Source web site exclusioin list from configured web endpoint
```
```
Implemented non-functional requirements
1) Validation on restful request's parameter. Requested parameter must be in Date format "yyyy-MM-dd"
2) Authentication protecting web and restful ws. Permitted user accoutn is "user/password".
3) Unit-test is covered
```
```
Unimplemented non-functional requirements
1) Unexpected error handling, eg. connection fail
2) Administration/Support feature for configuration, data file upload.
3) Authorization
4) Automated integration test
5) Audit logging
```
