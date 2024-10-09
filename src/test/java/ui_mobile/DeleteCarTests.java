package ui_mobile;

import api_rest.CarController;
import config.AppiumConfig;
import dto.CarsDto;
import dto.RegistrationBodyDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.MyCarsScreen;
import screens.SplashScreen;

import static helper.ReturnCarsDtoApi.returnCarsDto;

public class DeleteCarTests extends AppiumConfig {
    MyCarsScreen myCarsScreen;

    @BeforeMethod
    public void login() {
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("0bagginsbob@mail.com")
                .password("Qwerty123!")
                .build();
        myCarsScreen = new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
                .clickBtnLogin()
                .typeLoginForm(user)
                .clickBtnLoginPositive()
                .clickBtnDots()
                .clickBtnMyCars()
        ;
    }

    @Test
    public void deleteCarPositiveTest() {
        int quantityCars = 0, quantityAfterDelete = 0;
        CarController carController = new CarController();
        carController.login();
        if (carController.tokenDto.getAccessToken() != null) {
            Response response1 = carController.getUserCars();
            quantityCars = response1.as(CarsDto.class).getCars().length;
            System.out.println("cars --> " + quantityCars);
        } else
            System.out.println("Login failed");

        myCarsScreen.deleteCar();

        Response response2 = carController.getUserCars();
        if (response2.getStatusCode() == 200) {
            quantityAfterDelete = response2.as(CarsDto.class).getCars().length;
            System.out.println("cars after delete --> " + quantityAfterDelete);
        }
        Assert.assertEquals(quantityCars-1, quantityAfterDelete);
    }
    @Test
    public void deleteCarPositiveTest_HelperApi(){
        CarsDto carsDtoBeforeDelete = returnCarsDto();
        if(carsDtoBeforeDelete != null) {
            int quantityCars = carsDtoBeforeDelete.getCars().length;
            myCarsScreen.deleteCar();
            int quantityAfterDelete = returnCarsDto().getCars().length;
        Assert.assertEquals(quantityCars-1, quantityAfterDelete);
        }else {
            System.out.println("cars list is null");
            Assert.fail();
        }
    }

}

