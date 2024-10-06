package ui_mobile;

import config.AppiumConfig;
import dto.RegistrationBodyDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.MyCarsScreen;
import screens.SplashScreen;

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
    public void deleteCarPositiveTest(){
            myCarsScreen.deleteCar();
    }
}
