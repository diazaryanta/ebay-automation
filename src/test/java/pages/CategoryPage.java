package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

public class CategoryPage {
    private WebDriver driver;
    private WaitHelper wait;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public void selectLeftHandNavigation(String subCategoryName) {
        By leftNavLink = By.xpath("//a[normalize-space()='" + subCategoryName + "']");

        WebElement linkElement;
        try {
            linkElement = wait.waitForVisibility(leftNavLink);
        } catch (Exception e) {
            System.out.println("Elemen tidak terlihat jelas, memaksa pencarian di DOM...");
            linkElement = driver.findElement(leftNavLink);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", linkElement);

        try {
            wait.waitForClickable(leftNavLink).click();
            System.out.println("Berhasil klik secara natural!");
        } catch (Exception e) {
            System.out.println("Klik natural ditolak, mengeksekusi JavaScript Click...");
            js.executeScript("arguments[0].click();", linkElement);
        }
    }
}