package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    private static final By TITLE = By.cssSelector("h1");
    private static final By PRICE = By.xpath("//*[contains(@class,'price') and contains(text(),'Ft')]");
    private static final By QUANTITY_INPUT = By.cssSelector("input[type='number']");
    private static final By ADD_TO_CART = By.xpath("//button[contains(.,'Kosárba') or contains(.,'kosárba')]");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return waitClickable(TITLE).getText();
    }

    public String getPrice() {
        try {
            return waitClickable(PRICE).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public ProductPage setQuantity(int quantity) {
        WebElement el = waitClickable(QUANTITY_INPUT);
        el.clear();
        el.sendKeys(String.valueOf(quantity));
        return this;
    }

    public CartPage addToCart() {
        waitClickable(ADD_TO_CART).click();
        return new CartPage(driver);
    }
}
