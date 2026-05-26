package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ScreenshotListener;

@ExtendWith(ScreenshotListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @RegisterExtension
    final ContextCapture capture = new ContextCapture();

    @BeforeEach
    void setUpDriver() {
        driver = DriverFactory.create();
        capture.context.getStore(ScreenshotListener.NS).put(ScreenshotListener.DRIVER_KEY, driver);
    }

    @AfterEach
    void tearDownDriver(TestInfo info) {
        if (driver != null) {
            driver.quit();
        }
    }

    static class ContextCapture implements org.junit.jupiter.api.extension.BeforeEachCallback {
        ExtensionContext context;
        @Override
        public void beforeEach(ExtensionContext ctx) {
            this.context = ctx;
        }
    }
}
