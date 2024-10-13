package api_rest;

import dto.CarDto;
import dto.RegistrationBodyDto;
import dto.TokenDto;
import interfaces.BaseApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;
import static helper.PropertiesReader.getProperty;

public class CarController implements BaseApi {

    public TokenDto tokenDto;

    RequestSpecification requestSpecification;

    @BeforeSuite
    public void login() {
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                //.username("0bagginsbob@mail.com")
                //.password("Qwerty123!")
                .username(getProperty("login.properties", "email"))
                .password(getProperty("login.properties", "password"))
                .build();
        tokenDto = given()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + LOGIN_URL)
                .thenReturn()
                .getBody()
                .as(TokenDto.class)
        ;
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", tokenDto.getAccessToken())
                .build();
        System.out.println(tokenDto.getAccessToken());
    }

    public Response addNewCar(CarDto car) {
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .post(BASE_URL + ADD_NEW_CAR_URL)
                .thenReturn();
    }

    public Response addNewCarWrongToken(CarDto car, String token) {
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post(BASE_URL + ADD_NEW_CAR_URL)
                .thenReturn();
    }

    public Response getUserCars() {
        return given()
//                .contentType(ContentType.JSON)
//                .header("Authorization", tokenDto.getAccessToken())
                .spec(requestSpecification)
                .when()
                .get(BASE_URL + GET_USER_CAR_URL)
                .thenReturn();
    }
    public Response getUserCarsWrongURL(String url) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(BASE_URL + url)
                .thenReturn();
    }

    public Response deleteCarById(String serialNumber){
        return given()
                .spec(requestSpecification)
                .when()
                .delete(BASE_URL+DELETE_CAR_URL+serialNumber)
                .thenReturn();
    }
}
