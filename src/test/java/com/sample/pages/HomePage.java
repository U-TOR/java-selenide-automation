package com.sample.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage {

  @Step("Open home page")
  public HomePage openHomePage() {
    open("/");
    return this;
  }

  @Step("Should be on home page")
  public HomePage shouldBeOnHomePage() {
    $(By.xpath(
        "//div[contains(text(), 'Below is a list of links to the examples needed in the chapters. Click on the links below and follow the steps in the book.')]"))
        .shouldBe(visible);
    return this;
  }

  @Step("Open '{0}' chapter")
  public FirstChapterPage openChapter(String chapter) {
    $(By.linkText(chapter)).click();
    return new FirstChapterPage();
  }
}
