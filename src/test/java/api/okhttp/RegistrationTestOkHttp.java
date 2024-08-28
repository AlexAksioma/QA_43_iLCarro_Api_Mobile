package api.okhttp;

import dto.ErrorMessageDtoString;
import dto.RegistrationBodyDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestOkHttp implements BaseApi {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000)+1000;
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .username("frodo_baggins"+i+"@gmail.com")
                .password("Qwerty123!")
                .firstName("frodo")
                .lastName("baggins")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(registrationBodyDto), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void registrationNegativeTest_emailWOAt(){
        int i = new Random().nextInt(1000)+1000;
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .username("frodo_baggins"+i+"gmail.com")
                .password("Qwerty123!")
                .firstName("frodo")
                .lastName("baggins")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(registrationBodyDto), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        String responseJson;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            responseJson = response.body().string();
            System.out.println(responseJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDtoString errorMessageDtoString = GSON.fromJson(responseJson, ErrorMessageDtoString.class);
        softAssert.assertEquals(errorMessageDtoString.getStatus(), 400);
        softAssert.assertEquals(errorMessageDtoString.getError(), "Bad Request");
        softAssert.assertTrue(errorMessageDtoString.getMessage().toString().contains("email address"));
        softAssert.assertAll();
    }

}
