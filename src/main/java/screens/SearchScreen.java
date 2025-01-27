package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.FindBy;

public class SearchScreen extends BaseScreen {
    public SearchScreen(AppiumDriver<AndroidElement> driver) {
        super(driver);
    }

    @FindBy(xpath = "//android.widget.ImageView[@content-desc='More options']")
    AndroidElement btnDots;
    @FindBy(xpath = "//*[@text='Registration' and @resource-id='com.telran.ilcarro:id/title']")
    AndroidElement btnRegistration;
    @FindBy(xpath = "//*[@text='Login' and @resource-id='com.telran.ilcarro:id/title']")
    AndroidElement btnLogin;
    @FindBy(xpath = "//*[@text='My Cars' and @resource-id='com.telran.ilcarro:id/title']")
    AndroidElement btnMyCars;
    @FindBy(xpath = "//hierarchy/android.widget.Toast")
    AndroidElement popUpMessageSuccess;

    public SearchScreen clickBtnDots() {
        //btnDots.click();
        clickWait(btnDots, 5);
        return this;
    }
    public RegistrationScreen clickBtnRegistration(){
        btnRegistration.click();
        return new RegistrationScreen(driver);
    }
    public LoginScreen clickBtnLogin(){
        btnLogin.click();
        return new LoginScreen(driver);
    }

    public boolean textInElementPresent_popUpMessageSuccess(String text){
        return textInElementPresent(popUpMessageSuccess, text, 3);
    }

    public MyCarsScreen clickBtnMyCars() {
        btnMyCars.click();
        return new MyCarsScreen(driver);
    }
}
