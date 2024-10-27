package ui_mobile;

import config.AppiumConfig;
import dto.RegistrationBodyDto;
import helper.TestNGListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import screens.LoginScreen;
import screens.SearchScreen;
import screens.SplashScreen;

import java.lang.reflect.Method;

@Listeners(TestNGListener.class)
public class LoginTests extends AppiumConfig {

    LoginScreen loginScreen;

    @BeforeMethod(alwaysRun = true)
    public void openScreenLogin() {
        loginScreen = new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
                .clickBtnLogin();
    }

    @Test(groups = "positive")
    public void loginPositiveTest(Method method) {
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("0bagginsbob@mail.com")
                .password("Qwerty123!")
                .build();
        logger.info("start test --> " + method.getName() + " with data: " + user.toString());
        Assert.assertTrue(loginScreen.typeLoginForm(user)
                .clickBtnLoginPositive()
                .textInElementPresent_popUpMessageSuccess("Login success!"))
        ;
    }

    @Test
    public void loginNegativeTest_wrongPassword() {
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("0bagginsbob@mail.com")
                .password("Qwerty123!11111")
                .build();
        Assert.assertTrue(loginScreen.typeLoginForm(user)
                .clickBtnLoginNegative()
                .validateErrorMessage("Login or Password incorrect"))
        ;
    }

    @Test
    public void loginNegativeTest_usernameEmpty() {
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("")
                .password("Qwerty123!")
                .build();
        Assert.assertTrue(loginScreen.typeLoginForm(user)
                .clickBtnLoginNegative()
                .validateErrorMessage("All fields must be filled"))
        ;
    }

}
