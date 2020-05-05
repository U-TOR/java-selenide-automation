package com.sample.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.sample.pages.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Epic("Chapters Tests")
@Feature("Validate First Chapter")
public class FirstChapterTest extends BaseTest {
    @Test(description = "Check text on Chapter 1 page")
    public void checkTextOnFirstChapterPage() {
        new HomePage()
                .openHomePage()
                .shouldBeOnHomePage()
                .openChapter("Chapter1")
                .checkText("Assert that this text is on the page")
                .navigateToHomePage()
                .shouldBeOnHomePage();
    }
}
