package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT    = By.cssSelector("input[name='email']");
    private static final By PASSWORD_INPUT = By.cssSelector("input[type='password']");
    private static final By NEXT_BUTTON    = By.cssSelector("button.js-form-next");
    // Complex XPath: step-2 login submit — pepita.hu uses type="button" (not type="submit")
    // with the JS hook class js-form-submit.  Match on class + visible text so it never
    // accidentally clicks other buttons on the page.
    private static final By SUBMIT_BUTTON =
            By.xpath("//button[contains(@class,'js-form-submit') and contains(normalize-space(.),'Belépés')]");
    private static final By ERROR_MESSAGE  = By.cssSelector(".js-email-login-message-container");

    // Complex XPath: user-account nav link — present only when logged in.
    // Walks the ancestor::nav axis, then guards against login/registration hrefs
    // so stray footer links don't cause false positives.
    private static final By USER_MENU_LINK =
            By.xpath("(//nav//a[contains(@href,'/profil') and " +
                     "not(contains(@href,'bejelentkezes')) and " +
                     "not(contains(@href,'regisztracio'))])[1]");

    // Complex XPath: logout trigger — matches both Hungarian text and href variants.
    // translate() normalises the accented 'é' comparison across different encodings.
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

    /** Returns true once a successful login has redirected us off the login page. */
    public boolean isLoggedIn() {
        try {
            wait.until(d -> !d.getCurrentUrl().contains("bejelentkezes"));
        } catch (Exception ignored) {}
        return !driver.getCurrentUrl().contains("bejelentkezes");
    }

    /**
     * Clicks the profile menu to open its dropdown, then clicks the logout link.
     * pepita.hu hides the logout option inside a nav dropdown after login.
     */
    public void logout() {
        try {
            waitClickable(USER_MENU_LINK).click();
        } catch (Exception ignored) {}
        waitClickable(LOGOUT_LINK).click();
    }

    public boolean isPasswordFieldDisplayed() {
        // pepita.hu never shows the password field for unknown emails, so checking
        // for the email input on step 1 also counts as "on the login page".
        if (!driver.findElements(EMAIL_INPUT).isEmpty()) {
            return true;
        }
        return !driver.findElements(PASSWORD_INPUT).isEmpty();
    }
}
