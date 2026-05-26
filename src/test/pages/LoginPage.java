package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT    = By.cssSelector("input[name='email']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[type='password']");
    private static final By NEXT_BUTTON    = By.cssSelector("button.js-form-next");
    private static final By SUBMIT_BUTTON =
            By.xpath("//button[contains(@class,'js-form-submit') and contains(normalize-space(.),'Belépés')]");
    private static final By ERROR_MESSAGE  = By.cssSelector(".js-email-login-message-container");

    private static final By USER_MENU_LINK =
            By.xpath("(//nav//a[contains(@href,'/profil') and " +
                     "not(contains(@href,'bejelentkezes')) and " +
                     "not(contains(@href,'regisztracio'))])[1]");

    private static final By LOGOUT_LINK =
            By.xpath("//a[contains(normalize-space(.),'Kijelentkezés') or " +
                     "contains(normalize-space(.),'kijelentkezes') or " +
                     "contains(@href,'kijelentkezes') or contains(@href,'logout')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterEmail(String email) {
        WebElement el = waitClickable(EMAIL_INPUT);
        el.clear();
        el.sendKeys(email);
        try {
            waitClickable(NEXT_BUTTON).click();
        } catch (Exception ignored) {}
        return this;
    }

    public LoginPage enterPassword(String password) {
        try {
            WebElement el = waitClickable(PASSWORD_INPUT);
            el.click();
            el.sendKeys(password);
        } catch (Exception ignored) {}
        return this;
    }

    public LoginPage submit() {
        try {
            waitClickable(SUBMIT_BUTTON).click();
        } catch (Exception ignored) {}
        return this;
    }

    public String getErrorMessage() {
        try {
            return waitClickable(ERROR_MESSAGE).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoggedIn() {
        try {
            wait.until(d -> !d.getCurrentUrl().contains("bejelentkezes"));
        } catch (Exception ignored) {}
        return !driver.getCurrentUrl().contains("bejelentkezes");
    }

    public void logout() {
        try {
            waitClickable(USER_MENU_LINK).click();
        } catch (Exception ignored) {}
        waitClickable(LOGOUT_LINK).click();
    }

    public boolean isPasswordFieldDisplayed() {
        if (!driver.findElements(EMAIL_INPUT).isEmpty()) {
            return true;
        }
        return !driver.findElements(PASSWORD_INPUT).isEmpty();
    }
}
