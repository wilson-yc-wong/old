# Project Title
This is an web application to show Top N ranking on internet web sites.

### Prerequisites
Maven
JDK8

### Installing
This project source code is managed by MAVEN and Spring Boot. So you can bring up web application by 
1) run command "$ mvn spring-boot:run". Default servlet container is Jetty.
2) run command "$mvn package", maven will produce war file and then deploy to any application server.

Then you can visit the application by sample URL "http://localhost:8080/simple_report.html"

This is default application configuration by file /src/main/resources/app.properties
```
website.statistics.file.watch.source_folder=/tmp/statistics_data
website.statistics.file.watch.polling_max=
website.statistics.file.watch.polling_crontab=0 * * * * *

website.exclusion.watch.polling_crontab=0 */10 * * * *
website.exclusion.watch.endpoint=http://private-1de182-mamtrialrankingadjustments4.apiary-mock.com/exclusions
```

## Deployment
Application is deployed to AWS
http://wilson-ranking-app.us-east-2.elasticbeanstalk.com/simple_report.html

To initialize Database for web site visit count. You can either upload CSV to config path (default is /tmp/statistics_data), or either trigger web endpoint to submit pre-defined data
http://wilson-ranking-app.us-east-2.elasticbeanstalk.com/api/websites/init

Some data has been prepared for testing purpose
```
date|file|visits
2018-01-01|google.com|1
2018-01-01|google.com|100
2018-01-01|amazon.com|2
2018-01-03|yahoo.com|2
2018-01-04|amazon.com|3
2018-01-05|ibm.com|4
2018-01-06|hsbc.com|5
2018-01-07|cnn.com|10
2018-01-01|facebook.com|1000
2016-03-13|google.com|1000
```

## Running the tests
For quick test purpose, please select date range '2018-01-01' and today() on UI.
Here are few test cases
1) Sum of visit counts of same host is reported in selected date range
Pick date range 2018-01-01 and 2018-01-05, visit count of amazon becomes 5 (2 from 1/1, 3 from 1/4)
2) Exclusion list is effective by not showing facebook.com (w/o excludedTill)
3) Exclusion list is effective by not showing google.com (with both excludedSince and excludedTill)
Pick date range 2018-03-11 and 2018-03-14. Nothing is shown because google.com is excluded in this period.

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
1) Interactive UI provides query of top 5 web sites ranking.
2) Source web site visits data file by configured path
  Spring integration flow polls CSV file (by default is folder /tmp) and save web site visit count statistics into database
3) Source web site exclusioin list from configured web endpoint
  Spring integration flow polls web restful endpoint (by default every hour) and overwrite records into database
```
```
Implemented non-functional requirements
1) Validation on restful request's parameter. Requested parameter must be in Date format "yyyy-MM-dd"
2) Authentication protects web and restful ws. Permitted user accoutn is "user/password" and "admin/password"
3) Authorization protects admin function.
4) Administration/support feature support prepared CSV submission. It help AWS to load pre-defined data
5) Unit-test is covered
```
```
Unimplemented non-functional requirements
1) Unexpected error handling, eg. connection fail
2) Administration/Support feature for configuration, data file upload.
3) Automated integration test
4) Audit logging
```
