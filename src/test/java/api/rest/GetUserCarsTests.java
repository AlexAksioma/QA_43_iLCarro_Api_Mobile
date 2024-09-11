package api.rest;

import api_rest.CarController;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetUserCarsTests extends CarController {

    @Test
    public void getUserCarPositiveTests(){
        Assert.assertEquals(getUserCars().getStatusCode(), 200);
    }

    @Test
    public void getUserCarNegativeTest_wrongURL(){
        Response response = getUserCarsWrongURL(LOGIN_URL);
        System.out.println(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
