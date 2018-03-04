This is an web application to show Top N ranking on internet web sites.

#Architecture
Angularjs is front-end UI for user to query top ranking web sites.
Spring Boot Web is used to build Web and Restful controller.
Spring integration is used to aggregate File (web sites' visit count statistics data) and HTTP (exclusion list) to local Database
Memory Database H2 is configurated for testing purpose.

#Implemented functional requirements
1) Interactive UI is provided to query ranking.
2) Source web site visits data file by configured path
3) Source web site exclusioin list from configured web endpoint

#Implemented non-functional requirements
1) Validation on restful request's parameter. Requested parameter must be in Date format "yyyy-MM-dd"
2) Authentication protecting web and restful ws. Permitted user accoutn is "user/password".
3) Unit-test is covered

#Unimplemented non-functional requirements
1) Unexpected error handling, eg. connection fail
2) Administration/Support feature for configuration, data file upload.
3) Authorization
4) Automated integration test.
