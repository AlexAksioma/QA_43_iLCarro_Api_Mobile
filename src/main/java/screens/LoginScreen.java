package screens;

import dto.RegistrationBodyDto;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.FindBy;

public class LoginScreen extends  BaseScreen{
    public LoginScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }
    @FindBy(id = "com.telran.ilcarro:id/editLoginEmail")
    AndroidElement loginEmail;
    @FindBy(id = "com.telran.ilcarro:id/editLoginPassword")
    AndroidElement loginPassword;
    @FindBy(id = "com.telran.ilcarro:id/loginBtn")
    AndroidElement btnLogin;

    public LoginScreen typeLoginForm(RegistrationBodyDto user) {
        loginEmail.sendKeys(user.getUsername());
        loginPassword.sendKeys(user.getPassword());
        return this;
    }
    public SearchScreen clickBtnLoginPositive(){
        btnLogin.click();
        return new SearchScreen(driver);
    }

    public ErrorScreen clickBtnLoginNegative() {
        btnLogin.click();
        return new ErrorScreen(driver);
    }
}
