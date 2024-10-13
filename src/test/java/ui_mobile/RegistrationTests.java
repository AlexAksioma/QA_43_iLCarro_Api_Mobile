package ui_mobile;

import config.AppiumConfig;
import dto.RegistrationBodyDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.SearchScreen;
import screens.SplashScreen;

import java.util.Random;

public class RegistrationTests extends AppiumConfig {

    @Test(groups = "positive")
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .firstName(i+"Bob")
                .lastName(i+"Family")
                .username(i+"bob_family@mail.com")
                .password("Qwerty123!")
                .build();
        Assert.assertTrue(new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
                .clickBtnRegistration()
                .typeRegForm(user)
                .clickCheckBox()
                .clickBtnYallaPositive()
                .textInElementPresent_popUpMessageSuccess("Registration success"))
        ;
    }
    @Test
    public void registrationNegativeTest_wrongEmail(){
        int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .firstName(i+"Bob")
                .lastName(i+"Family")
                .username(i+"bob_familymail.com")
                .password("Qwerty123!")
                .build();
        Assert.assertTrue(new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
                .clickBtnRegistration()
                .typeRegForm(user)
                .clickCheckBox()
                .clickBtnYallaNegative()
                .validateErrorMessage("username=must be a well-formed email address"))
        ;
    }
    @Test
    public void registrationNegativeTest_WOCheckBox(){
        int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .firstName(i+"Bob")
                .lastName(i+"Family")
                .username(i+"bob_family@mail.com")
                .password("Qwerty123!")
                .build();
        Assert.assertTrue(new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
                .clickBtnRegistration()
                .typeRegForm(user)
                .clickBtnYallaNegative()
                .validateErrorMessage("All fields must be filled and agree terms"))
        ;
    }
}
