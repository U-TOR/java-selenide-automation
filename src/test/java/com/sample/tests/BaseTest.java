package com.sample.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import sun.security.krb5.internal.crypto.Des;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
    private BrowserWebDriverContainer container;

    @BeforeSuite
    public void beforeSuite() throws MalformedURLException {
        WebDriverRunner.setWebDriver(initDriver());
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    private WebDriver initDriver() throws MalformedURLException {
        switch (System.getProperty("environment.type")) {
            case "local.browser":
                return initLocalDriver(false);
            case "local.headless":
                return initLocalDriver(true);
            case "local.container":
                return initContainerisedDriver();
            case "local.selenoid":
                return initSelenoidRemoteDriver();
            case "local.zalenium":
                return initZaleniumRemoteDriver();
            default:
                throw new RuntimeException("Environment profile should be selected");
        }
    }

    private WebDriver initLocalDriver(boolean isHeadless) {
        switch (System.getProperty("browser.name")) {
            case "chrome":
                return new ChromeDriver(initChromeOptions(isHeadless));
            case "firefox":
                return new FirefoxDriver(initFirefoxOptions(isHeadless));
            default:
                throw new RuntimeException("Browser profile should be selected");
        }
    }

    private WebDriver initContainerisedDriver() {
        container = new BrowserWebDriverContainer()
                .withCapabilities(initBrowserCapabilities(false))
                .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("target"));
        container.start();
        return container.getWebDriver();
    }

    private WebDriver initSelenoidRemoteDriver() throws MalformedURLException {
        MutableCapabilities caps = initBrowserCapabilities(false);
        caps.setCapability("enableVNC", true);
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
    }

    private WebDriver initZaleniumRemoteDriver() {
        return null;
    }

    private MutableCapabilities initBrowserCapabilities(boolean isHeadless) {
        switch (System.getProperty("browser.name")) {
            case "chrome":
                return initChromeOptions(false);
            case "firefox":
                return initFirefoxOptions(false);
            default:
                throw new RuntimeException("Browser profile should be selected");
        }
    }

    private ChromeOptions initChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        if(!System.getProperty("screen.width").equals("") && !System.getProperty("screen.height").equals("")) {
            options.addArguments(String.format("--window-size=%s,%s",
                    System.getProperty("screen.width"), System.getProperty("screen.height")));
        }
        options.setHeadless(isHeadless);
        return options;
    }

    private FirefoxOptions initFirefoxOptions(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();
        if(!System.getProperty("screen.width").equals("") && !System.getProperty("screen.height").equals("")) {
            options.addArguments("--width=" + System.getProperty("screen.width"));
            options.addArguments("--height=" + System.getProperty("screen.height"));
        }
        options.setHeadless(isHeadless);
        return options;
    }

    @AfterSuite
    public void tearDown() {
        WebDriverRunner.getWebDriver().close();
        if(container != null) {
            container.stop();
        }
    }
}

