package ui_mobile;

import config.AppiumConfig;
import dto.RegistrationBodyDto;
import org.testng.annotations.Test;
import screens.SearchScreen;
import screens.SplashScreen;

import java.util.Random;

public class RegistrationTests extends AppiumConfig {

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .firstName(i+"Bob")
                .lastName(i+"Family")
                .username(i+"bob_family@mail.com")
                .password("Qwerty123!")
                .build();
        new SplashScreen(driver)
                .goToSearchScreen()
                .clickBtnDots()
        ;
    }
}
