package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {

    private DriverFactory() {}

    public static WebDriver create() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=" + ConfigReader.get("window.size"));
        options.addArguments("--user-agent=" + ConfigReader.get("user.agent"));
        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage",
                "--lang=hu-HU");

        return new ChromeDriver(options);
    }
}
