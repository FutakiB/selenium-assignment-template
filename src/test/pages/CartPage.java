package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

	private static final By CART_ITEMS = By.cssSelector("[class*='cart'] [class*='item']");

	public CartPage(WebDriver driver) {
		super(driver);
	}

	public int getItemCount() {
		return driver.findElements(CART_ITEMS).size();
	}

	public boolean verifyItemPresent(String titleFragment) {
		return !driver.findElements(By.xpath(
				"//*[contains(@class,'cart')]//*[contains(text(),'" + titleFragment + "')]"
		)).isEmpty();
	}
}
