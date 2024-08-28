package api.okhttp;

import dto.CarDto;
import dto.RegistrationBodyDto;
import dto.TokenDto;
import enums.Fuel;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddNewCarTestsOkHttp implements BaseApi {
    TokenDto tokenDto = new TokenDto();

    @BeforeClass
    public void login(){
        RegistrationBodyDto registrationBodyDto = RegistrationBodyDto.builder()
                .username("789baggins_bilbo@mail.com")
                .password("Zxc12345!")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(registrationBodyDto), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(tokenDto);
    }

    @Test
    public void addNewCarPositiveTest(){
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("12345-" + i)
                .manufacture("Opel")
                .model("Astra")
                .year("2023")
                .fuel(Fuel.DIESEL.getFuel())
                .seats(4)
                .carClass("A")
                .pricePerDay(100.23)
                .city("Haifa")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(car), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CAR_URL)
                .addHeader("Authorization", tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        Response response;
        String responseJson;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }
}
