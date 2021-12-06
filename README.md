# backend Budget Adviser

//only authorized
POST http://localhost:8081/api/users
//problem with id
{
    "username": "John1",
    "password": "Password",
    "role": "ADMIN",
    "personalInfo": {
        "firstName": "string",
        "lastName": "string",
        "phone": "343451",
        "email": "s64n1g@mail"     
    }
}

POST http://localhost:8081/api/auth/login
//returns full user info from 'users' if correct
{   
    "username": "username",
    "password": "simplepassword"
}

{
    "groupName": "Family",
    "description": "just for fun",
    "createdBy": 1
}

{
    "name": "on drinking",
    "createdBy": "1",
    "sum": "300",
    "groupId": "1", 
    "categoryId": "2"
}
