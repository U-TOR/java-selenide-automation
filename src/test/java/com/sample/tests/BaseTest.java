package com.sample.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import sun.security.krb5.internal.crypto.Des;

import java.io.File;

public class BaseTest {
    private BrowserWebDriverContainer container;

    @BeforeSuite
    public void beforeSuite() {
        WebDriverRunner.setWebDriver(initDriver());
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    private WebDriver initDriver() {
        switch (System.getProperty("environment.type")) {
            case "local.browser":
                return initLocalDriver();
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

    private WebDriver initLocalDriver() {
        switch (System.getProperty("browser.name")) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new RuntimeException("Browser profile should be selected");
        }
    }

    private WebDriver initContainerisedDriver() {
        Capabilities capabilities;
        switch (System.getProperty("browser.name")) {
            case "chrome":
                capabilities = DesiredCapabilities.chrome();
                break;
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
                break;
            default:
                throw new RuntimeException("Browser profile should be selected");
        }

        container = new BrowserWebDriverContainer()
                .withCapabilities(capabilities)
                .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("target"));
        container.start();
        return container.getWebDriver();
    }

    private WebDriver initSelenoidRemoteDriver() {
        return null;
    }

    private WebDriver initZaleniumRemoteDriver() {
        return null;
    }

    @AfterSuite
    public void tearDown() {
        WebDriverRunner.getWebDriver().close();
        if(container != null) {
            container.stop();
        }
    }
}

