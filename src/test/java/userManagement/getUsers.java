package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
//import core.utils.JsonReader;
//import core.utils.PropertyReader;
//import core.utils.SoftAssertionUtil;
import utils.JsonReader;
import utils.PropertyReader;
import utils.SoftAssertionUtil;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;



public class getUsers extends BaseTest {

 //   SoftAssertionUtil softAssertInstance = new SoftAssertionUtil();
//    @Test(description = "validateResponseHasItems" )
//    public void validateResponseHasItems() {
//        // Set base URI for the API
//        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
//
//        // Send a GET request and store the response in a variable
//        Response response =
//                given()
//                        .when()
//                        .get("/posts")
//                        .then()
//                        .extract()
//                        .response();
//
//        // Use Hamcrest to check that the response body contains specific items
//        assertThat(response.jsonPath().getList("title"), hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse"));
//    }

    @Test
    public void getUserdata() {
        given().
                when().get("https://reqres.in/api/users?page=2").
                then().
                assertThat().
                statusCode(200);

    }

    @Test
    public void validateGetResponseBody() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
                .when()
                .get("/todos/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()))  //if its in red color then its compilation error . suggestion will be there if you put crusor on that.
                .body("title", equalTo("delectus aut autem"))
                .body("userId", equalTo(1));
    }


    @Test
    public void validateResponseHasSize() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body has a specific size
        assertThat(response.jsonPath().getList(""), hasSize(500));
    }

//    @Test
//    public void validateListContainsInOrder() {
//        // Set base URI for the API
//        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
//
//        // Send a GET request and store the response in a variable
//        Response response = given()
//                .when()
//                .get("/comments?postId=1")
//                .then()
//                .extract()
//                .response();
//
//        // Use Hamcrest to check that the response body contains specific items in a specific order
//        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");
//        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
//    }

    @Test
    public void testGetUsersWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert that the response contains 6 users
//        response.then().body("data", hasSize(6));

        // Assert that the first user in the list has the correct values
        response.then().body("data[1].id", is(8));
        response.then().body("data[1].email", is("lindsay.ferguson@reqres.in"));
        response.then().body("data[1].first_name", is("Lindsay"));
        response.then().body("data[1].last_name", is("Ferguson"));
        response.then().body("data[1].avatar", is("https://reqres.in/img/faces/8-image.jpg"));
    }


    //Pass Params n multiple param
    @Test()
    public void validateStatusCodeGetUser() {
        System.out.println("*****************");
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("https://reqres.in/api/users");
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
    }

    @Test
    public void testGetUsersWithMultipleQueryParams() {
        Response response =
                given()
                        .queryParam("page", 2)
                        .queryParam("per_page", 3)
                        .queryParam("rtqsdr", 4)
                        .when()
                        .get("https://reqres.in/api/users")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

//    @Test
//    public void testCreateUserWithFormParam() {
//        Response response = given()
//                .contentType("application/x-www-form-urlencoded")
//                .formParam("name", "John Doe")
//                .formParam("job", "Developer")
//                .when()
//                .post("https://reqres.in/users")
//                .then()
//                .statusCode(201)
//                .extract()
//                .response();
//
//        // Assert that the response contains the correct name and job values
//        response.then().body("name", equalTo("John Doe"));
//        response.then().body("job", equalTo("Developer"));
//    }

    @Test
    public void testGetUserListWithHeader() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testGetUserListWithHeader Executed Successfully");
    }

    @Test
    public void testWithTwoHeaders() {
        given()
                .header("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx")
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testWithTwoHeaders Executed Successfully");
    }

    //**** How to use cookies in Rest Assured
    @Test
    public void testUseCookies() {
        Cookie cookies = new Cookie.Builder("cookieKey1", "cookieValue1")
                .setComment("using cookie key")
                .build();

        given()
                .cookie(cookies)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(StatusCode.SUCCESS.code);
        System.out.println("testUseCookies Executed Successfully");
    }


    // Delete using inRest Assured
    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void verifyStatusCodeDelete() {
        Response resp = given()
                .delete("https://reqres.in/api/users/2");
        assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println("verifyStatusCodeDelete executed successfully");
    }

    //How to use TestData using JSON reader separately
    @Test
    public void validateWithTestDataFromJson() throws IOException, ParseException {
        String username = JsonReader.getTestData("username");
        String password = JsonReader.getTestData("password");

        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get("https://postman-echo.com/basic-auth");
        int actualStatusCode = resp.statusCode();
        assertEquals(actualStatusCode, 200);
        System.out.println("validateWithTestDataFromJson executed successfully");


    }

    // How to use serverAddress in propertyReader file separately
    @Test(groups = "RegressionSuite")
    public void validateWithDataFromPropertiesFile() {

        String serverAddress = PropertyReader.propertyReader("config.properties", "serverAddress");
        System.out.println("Server Address is : " + serverAddress);
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get(serverAddress);
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithDataFromPropertiesFile executed successfully" + serverAddress);
    }


    // How to combine TestData and PropertyReader together
    @Test()
    public void validateFromProperties_TestData() throws IOException, ParseException {
        String serverAddress = PropertyReader.propertyReader("config.properties", "server");
        String endpoint = JsonReader.getTestData("endpoint");
        String URL = serverAddress + endpoint;
        System.out.println("URL  is : " + URL);
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get(URL);
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateFromProperties_TestData executed successfully" + URL);
    }

    @Test
    public void validateWithSoftAssertUtil() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        SoftAssertionUtil.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        SoftAssertionUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
 }
    @DataProvider(name = "testdata")
    public Object[][] testData() {
        return new Object[][]{
                {"1", "John"},
                {"2", "Jane"},
                {"3", "Bob"}
        };
    }

    @Test(dataProvider = "testdata")
    @Parameters({"id", "name"})
    public void testEndpoint(String id, String name) {
        given()
                .queryParam("id", id)
                .queryParam("name", name)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void Test() throws IOException, ParseException {
        JsonReader.getJsonArrayData("technology", 2);

    }
}






