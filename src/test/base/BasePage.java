package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.timeout")));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void scrollToFooter() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void dismissCookieConsent() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By[] candidates = new By[]{
                By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"),
                By.id("CybotCookiebotDialogBodyButtonAccept"),
                By.cssSelector("button[aria-label*='Accept']"),
                By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'elfogad')]")
        };
        for (By by : candidates) {
            try {
                WebElement btn = shortWait.until(ExpectedConditions.elementToBeClickable(by));
                btn.click();
                return;
            } catch (TimeoutException | NoSuchElementException ignored) {
            }
        }
    }
}
