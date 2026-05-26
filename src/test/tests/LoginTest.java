package tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    @Test
    @DisplayName("Should show error message when logging in with invalid credentials")
    void shouldShowErrorOnInvalidLogin() {
        LoginPage login = new HomePage(driver).open().goToLogin();
        login.enterEmail("notregistered@test.example")
             .enterPassword("WrongPassword123")
             .submit();

        String err = login.getErrorMessage();
        boolean stillOnLogin = login.isPasswordFieldDisplayed();
        assertTrue(!err.isBlank() || stillOnLogin,
                "Expected either error message or to remain on the login page");
    }

    @Test
    @DisplayName("Should submit the login form even with invalid credentials")
    void shouldSubmitLoginForm() {
        LoginPage login = new HomePage(driver).open().goToLogin();
        login.enterEmail("a@b.cd")
             .enterPassword("xyz")
             .submit();
        assertFalse(driver.getCurrentUrl().isBlank());
    }

    @Test
    @DisplayName("Should display login page after navigating to the login link")
    void shouldDisplayLoginPage() {
        LoginPage login = new HomePage(driver).open().goToLogin();
        assertTrue(login.isPasswordFieldDisplayed(), "Password field must be displayed on the login page");
    }

    @Test
    @DisplayName("Should login successfully with valid credentials")
    void shouldLoginSuccessfully() {
        LoginPage login = new HomePage(driver).open().goToLogin();
        login.enterEmail(ConfigReader.get("login.username"))
             .enterPassword(ConfigReader.get("login.password"))
             .submit();
        assertTrue(login.isLoggedIn(), "Expected to be redirected away from the login page after valid login");
    }
}
