package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import utils.ConfigReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaticPageTest extends BaseTest {

    @Test
    @DisplayName("Should have a non-empty homepage title containing the brand name")
    void shouldHaveExpectedHomepageTitle() {
        HomePage home = new HomePage(driver).open();
        String title = home.getTitle();
        assertFalse(title.isBlank(), "Homepage title must not be blank");
        assertTrue(title.toLowerCase().contains("pepita"),
                "Homepage title should reference the brand: " + title);
    }

    @Test
    @DisplayName("Should display footer privacy link after scrolling to the bottom with JS")
    void shouldDisplayFooterTextWhenScrolledToBottom() {
        HomePage home = new HomePage(driver).open();
        assertTrue(home.isFooterPrivacyLinkVisible(),
                "Privacy link must be visible after scrolling to the footer");
    }

    @Test
    @DisplayName("Should load every page in a predefined list and verify it has a title")
    void shouldLoadAllListedCategoryPages() {
        String base = ConfigReader.get("base.url").replaceAll("/$", "");
        List<String> paths = List.of(
                "/auto",
                "/ingatlan",
                "/allas",
                "/elektronika",
                "/divat"
        );
        for (String p : paths) {
            driver.get(base + p);
            String title = driver.getTitle();
            assertFalse(title.isBlank(), "Title must not be blank for " + p);
        }
    }
}
