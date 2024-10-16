# How to run?

You need to up compose file located in "/docker/docker-compose.yml" direction with "docker-compose up" command.
After running compose file, the required tables and data will be added to the database with init.sql.
You can connect to the database with the following information.

url: jdbc:postgresql://localhost:5999/checkout?useSSL=false&useUnicode=true&characterEncoding=utf-8
user: postgres
password: postgres

Couchbase cache added to application. After running compose file couchbase cluster, bucket, scope and collection 
generate automatically with couchbase-setup.sh. You can connect to the couchbase with the following information.

url: http://localhost:8091/
user: admin
password: password

Swagger-UI has been added to the project. You can access it from the URL http://localhost:8080/swagger-ui/index.html.
Additionally, a JaCoCo code coverage report is available at target/site/jacoco/index.html.
Sample request objects and curl commands for Swagger-UI can be found below.

### Add student
request body:
{
"firstName": "Name",
"lastName": "Surname"
}

curl:
curl -X 'POST' \
'http://localhost:8080/v1/students' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"firstName": "Name",
"lastName": "Surname"
}'

### Get all tests
curl -X 'GET' \
'http://localhost:8080/v1/tests?page=0&size=20' \
-H 'accept: application/json'

### Get specific test with id
curl -X 'GET' \
'http://localhost:8080/v1/tests/1' \
-H 'accept: application/json'

### Get test result for student
curl -X 'GET' \
'http://localhost:8080/v1/tests/result?test-id=1&student-id=1' \
-H 'accept: application/json'

### Submit test answer
request body:
{
    "studentId": 1,
    "testId": 1,
    "answers": [
        {
            "questionId": 1,
            "selectedOptionId": 2
        },
        {
            "questionId": 2,
            "selectedOptionId": 5
        },
        {
            "questionId": 3,
            "selectedOptionId": 8
        },
        {
            "questionId": 4,
            "selectedOptionId": 10
        },
        {
            "questionId": 5,
            "selectedOptionId": null
        }
    ]
}

Curl:
curl -X 'POST' \
'http://localhost:8080/v1/tests/submit' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"studentId": 1,
"testId": 1,
"answers": [
{
"questionId": 1,
"selectedOptionId": 2
},
{
"questionId": 2,
"selectedOptionId": 5
},
{
"questionId": 3,
"selectedOptionId": 8
},
{
"questionId": 4,
"selectedOptionId": 10
},
{
"questionId": 5,
"selectedOptionId": null
}
]
}'
