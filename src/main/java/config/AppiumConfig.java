package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import static helper.PropertiesReader.getProperty;
public class AppiumConfig {
    public static AppiumDriver<AndroidElement> driver;
    public static int height = 0, width = 0;

    //      "platformName": "Android",
//              "deviceName": "Nex5",
//              "platformVersion": "8.0",
//              "appPackage": "com.telran.ilcarro",
//              "appActivity": ".SplashActivity"
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//        desiredCapabilities.setCapability("platformName", "Android");
//        desiredCapabilities.setCapability("deviceName", "Nex5");
//        desiredCapabilities.setCapability("platformVersion", "8.0");
//        desiredCapabilities.setCapability("appPackage", "com.telran.ilcarro");
//        desiredCapabilities.setCapability("appActivity", ".SplashActivity");
        desiredCapabilities.setCapability("platformName", getProperty("mobile.properties", "platformName"));
        desiredCapabilities.setCapability("deviceName",  getProperty("mobile.properties", "deviceName"));
        desiredCapabilities.setCapability("platformVersion",  getProperty("mobile.properties", "platformVersion"));
        desiredCapabilities.setCapability("appPackage",  getProperty("mobile.properties", "appPackage"));
        desiredCapabilities.setCapability("appActivity",  getProperty("mobile.properties", "appActivity"));

        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");

        try {
            driver = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
