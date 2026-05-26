package utils;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ScreenshotListener implements TestWatcher, AfterTestExecutionCallback {

    public static final ExtensionContext.Namespace NS =
            ExtensionContext.Namespace.create("soat.screenshot");
    public static final String DRIVER_KEY = "driver";

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Optional<Throwable> exception = context.getExecutionException();
        if (exception.isEmpty()) return;

        WebDriver driver = context.getStore(NS).get(DRIVER_KEY, WebDriver.class);
        if (driver == null) return;
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path target = Paths.get("build", "screenshots",
                    context.getDisplayName().replaceAll("[^A-Za-z0-9._-]", "_") + ".png");
            Files.createDirectories(target.getParent());
            Files.copy(src.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + target.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
