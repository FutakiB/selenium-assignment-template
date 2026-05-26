package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;

public class HomePage extends BasePage {

    private static final By SEARCH_INPUT        = By.cssSelector("input.aa-Input");
    private static final By SEARCH_SUBMIT       = By.cssSelector("button.js-algolia-search");
    private static final By LOGIN_LINK          = By.cssSelector(".js-cart-login-container a.profile__login-link");
    private static final By FOOTER_PRIVACY_LINK = By.cssSelector("a[href*='adatvedelmi']");

    private static final By NEWSLETTER_EMAIL      = By.cssSelector("input[name='email']");
    private static final By NEWSLETTER_NAME       = By.cssSelector("input[placeholder='Név']");
    private static final By NEWSLETTER_PRIVACY_CB = By.cssSelector("input[name='privacy_policy']");
    private static final By NEWSLETTER_CONSENT_CB = By.cssSelector("input[name='newsletter_consent']");
    private static final By NEWSLETTER_SUBMIT     = By.cssSelector("button[type='submit']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(ConfigReader.get("base.url"));
        dismissCookieConsent();
        return this;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public SearchResultsPage searchFor(String keyword) {
        WebElement input = waitClickable(SEARCH_INPUT);
        input.clear();
        input.sendKeys(keyword);
        waitClickable(SEARCH_SUBMIT).click();
        return new SearchResultsPage(driver);
    }

    public LoginPage goToLogin() {
        waitClickable(LOGIN_LINK).click();
        return new LoginPage(driver);
    }

    public boolean isFooterPrivacyLinkVisible() {
        scrollToFooter();
        return !driver.findElements(FOOTER_PRIVACY_LINK).isEmpty();
    }

    public void submitNewsletter(String email) {
        scrollToNewsletter();
        try {
            WebElement name = waitClickable(NEWSLETTER_NAME);
            name.clear();
            name.sendKeys("Test");
        } catch (Exception ignored) {}
        for (By cb : new By[]{NEWSLETTER_PRIVACY_CB, NEWSLETTER_CONSENT_CB}) {
            try {
                WebElement checkbox = driver.findElement(cb);
                if (!checkbox.isSelected()) checkbox.click();
            } catch (Exception ignored) {}
        }
        WebElement field = waitClickable(NEWSLETTER_EMAIL);
        field.clear();
        field.sendKeys(email);
        try {
            waitClickable(NEWSLETTER_SUBMIT).click();
        } catch (Exception ignored) {
            field.sendKeys(Keys.ENTER);
        }
    }

    private void scrollToNewsletter() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight * 0.65);");
    }
}
