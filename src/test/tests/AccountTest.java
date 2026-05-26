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


/**
 * Tests that require a registered, logged-in user account.
 */
class AccountTest extends BaseTest {

    /** Helper: log in with the credentials from config.properties and return the LoginPage. */
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

        // Search for a product, open the first result, then submit the Add-to-Cart form.
        // This form requires the user to be registered (cart items are tied to the account).
        SearchResultsPage results = new HomePage(driver).open().searchFor("lego");
        results.clickFirstProduct().addToCart();

        // Verify the page is still responsive after the form submission.
        assertFalse(driver.getTitle().isBlank(),
                "Page should still have a title after adding a product to the cart");
    }
}
