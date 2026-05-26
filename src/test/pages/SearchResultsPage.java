package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends BasePage {

    private static final By SORT_TOGGLE    = By.id("dropdownMenuButton");
    // Price currency span appears in every product card regardless of grid or list view.
    private static final By PRODUCT_CARDS  = By.className("list-element");
    private static final By PRODUCT_TITLES = By.cssSelector(".list-element__title h2 a");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /** Clicks the first product title link and returns the resulting ProductPage. */
    public ProductPage clickFirstProduct() {
        waitClickable(PRODUCT_TITLES).click();
        return new ProductPage(driver);
    }

    public boolean hasResults() {
        try {
            dismissCookieConsent();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(PRODUCT_CARDS, 0));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
