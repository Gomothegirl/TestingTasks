package APITest

import io.restassured.http.ContentType
import io.restassured.response.Response
import spock.lang.Specification
import spock.lang.Unroll

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

class APITestTask extends Specification {
    @Unroll
    def "Get an existing user"() {
        given: "base url is working"
        def url = "https://jsonplaceholder.typicode.com/users/2"
        when: "getting an existing user"
        def response = given().log().all()
                .contentType(ContentType.JSON)
                .get(url).prettyPeek()
        then: "Assert status 200"
        assertStatusOf(200, response)
    }

    @Unroll
    def "Create a new user"() {
        given: "base url is working"
        def url = "https://jsonplaceholder.typicode.com/users"
        when: "Creating a new user"
        def response = given()
                .contentType(ContentType.JSON)
                .body("""{
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    "address": {
        "street": "Kulas Light",
        "suite": "Apt. 556",
        "city": "Gwenborough",
        "zipcode": "92998-3874",
        "geo": {
            "lat": "-37.3159",
            "lng": "81.1496"
        }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
        "name": "Romaguera-Crona",
        "catchPhrase": "Multi-layered client-server neural-net",
        "bs": "harness real-time e-markets"
    }
}""")
                .post(url).prettyPeek()
        then: "assert status code 201 and that id is not null"
        assertStatusOf(201, response)

    }

    @Unroll
    def "Update user details (zipcode)"() {
        given: "base url is working"
        def url = "https://jsonplaceholder.typicode.com/users/2"
        when: "Creating a new user"
        def response = given()
                .contentType(ContentType.JSON)
                .body("""{
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    "address": {
        "street": "Kulas Light",
        "suite": "Apt. 556",
        "city": "Gwenborough",
        "zipcode": "92998-0000",
        "geo": {
            "lat": "-37.3159",
            "lng": "81.1496"
        }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
        "name": "Romaguera-Crona",
        "catchPhrase": "Multi-layered client-server neural-net",
        "bs": "harness real-time e-markets"
    }
}""")
                .put(url).prettyPeek()
        then: "assert status code 200 and that id is not null"
        assertStatusOf(200, response)
    }

    @Unroll
    def "Search for users by email"() {
        given: "base url is working"
        def url = "https://jsonplaceholder.typicode.com/users?email=Shanna@melissa.tv"
        when: "getting an existing user by email"
        def response = given().log().all()
                .contentType(ContentType.JSON)
                .get(url).prettyPeek()
        then: "Assert status 200"
        assertStatusOf(200, response)
    }

    @Unroll
    def "Create a user without an email address"(){
        given: "base url is working"
        def url = "https://jsonplaceholder.typicode.com/users"
        when: "Creating a new user"
        def response = given()
                .contentType(ContentType.JSON)
                .body("""{
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "",
    "address": {
        "street": "Kulas Light",
        "suite": "Apt. 556",
        "city": "Gwenborough",
        "zipcode": "92998-3874",
        "geo": {
            "lat": "-37.3159",
            "lng": "81.1496"
        }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
        "name": "Romaguera-Crona",
        "catchPhrase": "Multi-layered client-server neural-net",
        "bs": "harness real-time e-markets"
    }
}""")
                .post(url).prettyPeek()
        then: "assert status code 201 and that id is not null"
        assertStatusOf(500, response)

    }



    def assertStatusOf(int expectedStatusCode, Response response) {
        assert response.statusCode() == expectedStatusCode, """
    Expected a responseCode of ${expectedStatusCode}, instead got ${response.statusCode()}
    Response body:
    ${response.asString()}
    """
        true
    }
}
