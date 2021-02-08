package com.sample.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;

public class FirstChapterPage {

  @Step("Fist chapter page should have test '{0}'")
  public FirstChapterPage checkText(String expectedText) {
    $("#divontheleft").shouldHave(text(expectedText));
    return this;
  }

  @Step("Navigate to home page")
  public HomePage navigateToHomePage() {
    $("a[href='/']").click();
    return new HomePage();
  }
}
