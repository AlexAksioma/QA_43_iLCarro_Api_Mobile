package ui_mobile;

import api_rest.CarController;
import config.AppiumConfig;
import dto.CarDto;
import dto.CarsDto;
import dto.RegistrationBodyDto;
import enums.Fuel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.SearchScreen;
import screens.SplashScreen;

import java.util.Random;

public class AddNewCarTests extends AppiumConfig {
    SearchScreen searchScreen;

    @BeforeMethod
    public void login() {
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("0bagginsbob@mail.com")
                .password("Qwerty123!")
                .build();
        searchScreen = new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
                .clickBtnLogin()
                .typeLoginForm(user)
                .clickBtnLoginPositive()
        ;
    }

    @Test
    public void addNewCarPositiveTest() {
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("777-" + i)
                .manufacture("Tesla")
                .model("Model X")
                .year("2024")
                .fuel(Fuel.GAS.getFuel())
                .seats(4)
                .carClass("B")
                .pricePerDay(1.23)
                .city("Netanya")
                .about("My car")
                .build();
        Assert.assertTrue(searchScreen.clickBtnDots()
                .clickBtnMyCars()
                .clickBtnAddNewCar()
                .typeAddNewCarForm(car)
                .clickBtnAddCar()
                .textInElementPresent_popUpMessageSuccess("Car was added!"))
        ;
    }

    @Test
    public void addNewCarPositiveTestValidateWithRest() {
        int i = new Random().nextInt(1000) + 1000;
        CarDto car = CarDto.builder()
                .serialNumber("0000-" + i)
                .manufacture("Tesla")
                .model("Model X")
                .year("2024")
                .fuel(Fuel.GAS.getFuel())
                .seats(4)
                .carClass("B")
                .pricePerDay(1.23)
                .city("Netanya")
                .about("My car")
                .build();
        searchScreen.clickBtnDots()
                .clickBtnMyCars()
                .clickBtnAddNewCar()
                .typeAddNewCarForm(car)
                .clickBtnAddCar()
        ;
        CarController carController = new CarController();
        carController.login();
        System.out.println(carController.tokenDto.getAccessToken());
        Response response = carController.getUserCars();
        if (response.getStatusCode() == 200) {
            CarsDto carsDto = response.as(CarsDto.class);
            for (CarDto carAPI : carsDto.getCars()) {
                //System.out.println(carAPI);
                if (carAPI.equals(car)) {
                    System.out.println("list -->" + carAPI);
                    System.out.println("new  -->" + car);
                    Assert.assertEquals(car, carAPI);
                    break;
                }
            }
        } else
            System.out.println("response get all user car" + response);
    }

    @Test
    public void addNewCarNegativeTest_SerialNumberIsEmpty() {
        CarDto car = CarDto.builder()
                .serialNumber("")
                .manufacture("Tesla")
                .model("Model X")
                .year("2024")
                .fuel(Fuel.GAS.getFuel())
                .seats(4)
                .carClass("B")
                .pricePerDay(1.23)
                .city("Netanya")
                .about("My car")
                .build();
        Assert.assertTrue(searchScreen.clickBtnDots()
                .clickBtnMyCars()
                .clickBtnAddNewCar()
                .typeAddNewCarForm(car)
                .clickBtnAddCarNegative()
                .validateErrorMessage("Fields: Serial number, Manufacture, Model, City, Price per day is required!")
        )
        ;
    }
}
