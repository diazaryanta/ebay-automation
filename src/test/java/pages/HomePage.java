package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

public class HomePage {
    private WebDriver driver;
    private WaitHelper wait;

    private By shopByCategoryBtn = By.cssSelector("span.gh-categories__title");
    private By searchInput = By.xpath("//input[@id='kw' or @placeholder='Search for anything']");
    private By searchSubmitBtn = By.id("gh-search-btn");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public void clickShopByCategory() {
        WebElement btn = wait.waitForClickable(shopByCategoryBtn);

        try {
            btn.click();
        } catch (Exception e) {
            System.out.println("Klik normal terhalang, mencoba JavaScript Click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", btn);
        }
    }

    public void selectMainCategory(String categoryName) {
        By dynamicCategoryLink = By.xpath("//a[contains(text(), '" + categoryName + "')]");
        wait.waitForClickable(dynamicCategoryLink).click();
    }

    public void enterSearchKeyword(String keyword) {
        WebElement searchBox;
        try {
            searchBox = wait.waitForClickable(searchInput);
            searchBox.clear();
            searchBox.sendKeys(keyword);
        } catch (Exception e) {
            System.out.println("Kolom pencarian terhalang, mengeksekusi jurus paksa DOM...");

            searchBox = driver.findElement(searchInput);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", searchBox);

            searchBox.clear();
            searchBox.sendKeys(keyword);
        }
    }

    public void clickSearch() {
        wait.waitForClickable(searchSubmitBtn).click();
    }
}