After you clone the repository

1. Build the application using the following command:
`mvn clean install`
2. Run the application using this command: `mvn spring-boot:run`

In order to make it easier to test the REST API, swagger UI has been added in the application.
After running the application, open this link in your browser:
`http://localhost:8080/swagger-ui/index.html`

- In the landing page select the `commission-calculatro-controller` 
![](../../Screenshot from 2022-02-24 11-11-59.png)
- Select the `Try it out` button to test the application.
- In `Request body`text area add the json a json in the following format (these are the example rules provided in the exercise's description):

{
"date": "2021-01-02",
"amount": "2000.00",
"currency": "EUR",
"client_id": 42
}

{
"date": "2021-01-03",
"amount": "500.00",
"currency": "EUR",
"client_id": 1
}

{
"date": "2021-01-04",
"amount": "499.00",
"currency": "EUR",
"client_id": 1
}

{
"date": "2021-01-05",
"amount": "100.00",
"currency": "EUR",
"client_id": 1
}

{
"date": "2021-01-06",
"amount": "1.00",
"currency": "EUR",
"client_id": 1
}

{
"date": "2021-02-01",
"amount": "500.00",
"currency": "EUR",
"client_id": 1
}

- Press the `Execute` button to send the POST request and check the result.
