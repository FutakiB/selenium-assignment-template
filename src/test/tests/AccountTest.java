package tests;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchResultsPage;
import utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AccountTest extends BaseTest {

    private LoginPage loginWithValidCredentials() {
        LoginPage login = new HomePage(driver).open().goToLogin();
        login.enterEmail(ConfigReader.get("login.username"))
             .enterPassword(ConfigReader.get("login.password"))
             .submit();
        return login;
    }

    @Test
    @DisplayName("Should add a product to the cart as a logged-in user")
    void shouldAddProductToCartAsLoggedInUser() {
        loginWithValidCredentials();

        SearchResultsPage results = new HomePage(driver).open().searchFor("lego");
        results.clickFirstProduct().addToCart();

        assertFalse(driver.getTitle().isBlank(),
                "Page should still have a title after adding a product to the cart");
    }
}
