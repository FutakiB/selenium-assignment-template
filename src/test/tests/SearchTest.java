package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.ConfigReader;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchTest extends BaseTest {

    @Test
    @DisplayName("Should return results when searching for telefon")
    void shouldReturnResultsWhenSearchingForTelefon() {
        SearchResultsPage results = new HomePage(driver).open().searchFor("telefon");
        assertTrue(results.hasResults(), "Expected at least one product card on the results page");
    }

    @Test
    @DisplayName("Should navigate back to homepage using the browser back button")
    void shouldNavigateBackToHomepageUsingBrowserBack() {
        HomePage home = new HomePage(driver).open();
        String homeUrl = driver.getCurrentUrl();
        home.searchFor("konyha");
        String searchUrl = driver.getCurrentUrl();
        assertNotEquals(homeUrl, searchUrl);

        driver.navigate().back();
        assertTrue(driver.getCurrentUrl().startsWith(ConfigReader.get("base.url").replaceAll("/$", "")),
                "Back navigation should return to the base url");

        driver.navigate().forward();
        assertTrue(driver.getCurrentUrl().contains("konyha")
                        || driver.getCurrentUrl().equals(searchUrl),
                "Forward navigation should return to the search results");
    }

    @Test
    @DisplayName("Should submit newsletter form with a randomly generated email address")
    void shouldSubmitNewsletterWithRandomEmail() {
        HomePage home = new HomePage(driver).open();
        String randomEmail = "soat-" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        home.submitNewsletter(randomEmail);
        assertFalse(driver.getTitle().isBlank());
    }

    @Test
    @DisplayName("Should clear the cookie consent cookie and reload without breaking the page")
    void shouldManipulateCookies() {
        HomePage home = new HomePage(driver).open();
        Set<Cookie> cookies = driver.manage().getCookies();
        assertFalse(cookies.isEmpty(), "Expected at least one cookie after loading the page");

        for (Cookie c : cookies) {
            if (c.getName().toLowerCase().contains("consent")
                    || c.getName().toLowerCase().contains("cookie")) {
                driver.manage().deleteCookieNamed(c.getName());
            }
        }
        driver.navigate().refresh();
        home.dismissCookieConsent();
        assertFalse(driver.getTitle().isBlank(), "Page must reload after cookie manipulation");
    }
}
