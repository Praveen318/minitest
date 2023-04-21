Test Maker API
This project is an API for a Test Maker. It allows the master to create and manage multiple-choice questions, set test rules such as total time, number of questions, and grant permission to students to take the test. The API is built using Spring Boot framework with Java programming language, and the data is stored in MySQL.

Technologies Used
Eclipse
Postman
MySQL
Spring Boot
Java

API Endpoints
POST /student/Login - authenticate and get access token for a student
POST /student/add - add a new student (Master authorization required)
POST /student/Logout - logout the current student
PUT /student/start - start a new test (Student authorization required)
GET /student/next - get the next question in the test (Student authorization required)
GET /student/previous - get the previous question in the test (Student authorization required)
POST /student/save/{ch} - save the answer for the current question (Student authorization required)
POST /student/submit - submit the test for grading (Student authorization required)
GET /student/getProfile - get the profile information for the current student (Student authorization required)
POST /master/Login - authenticate and get access token for a master
POST /master/add - add a new master (Master authorization required)
POST /master/Logout - logout the current master
POST /master/addQuestion - add a new question (Master authorization required)
GET /master/findAllQuestions - get all the questions (Master authorization required)
PUT /master/updateQuestion/{id} - update an existing question by ID (Master authorization required)
POST /master/setTest/{testTime} - set the test rules and duration (Master authorization required)
GET /master/findAllStudent - get all the students (Master authorization required)
POST /master/grantPermission/{id} - grant permission to a student to take the test (Master authorization required)


How to Run
Clone the repository
Import the project into Eclipse
Run the application
Use Postman to make requests to the API endpoints

Contribution Guidelines
This project is open to contributions. If you find any issues or would like to suggest an improvement, feel free to create a pull request.