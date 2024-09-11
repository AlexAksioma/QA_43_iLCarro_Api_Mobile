package api.rest;

import api_rest.CarController;
import dto.CarsDto;
import dto.ErrorMessageDtoString;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeleteCarByIdTests extends CarController {
    SoftAssert softAssert = new SoftAssert();
    CarsDto carsDto;

    @BeforeClass
    public void getUserCarsBeforeDelete() {
        Response response = getUserCars();
        if (response.getStatusCode() == 200) {
            carsDto = response.getBody().as(CarsDto.class);
        } else
            System.out.println("get status code isn't 200 --> " + response.getStatusCode());
    }

    @Test
    public void deleteCarPositiveTest() {
        if (carsDto.getCars().length != 0) {
            String serialNumber = carsDto.getCars()[0].getSerialNumber();
            Response response = deleteCarById(serialNumber);
            Assert.assertEquals(response.getStatusCode(), 200);
        } else
            Assert.fail("user don't have cars");
    }

    @Test
    public void deleteCarNegativeTest_wrongSerialNumber() {
        Response response = deleteCarById("serialNumber");
        softAssert.assertEquals(response.getStatusCode(), 400);
        ErrorMessageDtoString errorMessageDtoString = response.getBody().as(ErrorMessageDtoString.class);
        System.out.println(errorMessageDtoString);
        softAssert.assertEquals(errorMessageDtoString.getMessage(), "Car with serial number serialNumber not found");
        softAssert.assertAll();
    }
}
