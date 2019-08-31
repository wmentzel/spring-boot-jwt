## Start both servers

Start authorization server:

    cd authorization-server
    ./gradlew bootRun

Start resource server:

    cd ..
    cd authorization-server
    ./gradlew bootRun

Both Spring projects have their *application.properties* files check-in for simplicity. You can change the ports there. If unchaged the authorization server will run on *localhost:8080* and the resource server will run on *localhost:8081*.

Retrieve JWT token:

    curl -X POST -u "testClient:testClientSecret" -d "grant_type=password&username=testUser&password=testUserPassword" http://localhost:8080/oauth/token


Consume secured REST API:

    curl -X GET -H 'Authorization:Bearer [JWT_TOKEN]'  http://localhost:8081/hello
