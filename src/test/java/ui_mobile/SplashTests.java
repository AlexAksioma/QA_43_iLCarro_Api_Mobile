package ui_mobile;

import config.AppiumConfig;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.SearchScreen;
import screens.SplashScreen;

public class SplashTests extends AppiumConfig {

    @Test(groups = "positive")
    public void validateVersion(){
        Assert.assertTrue(new SplashScreen(driver).validateVersion());
    }
}
