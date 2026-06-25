package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitHelper;

public class FilterPage {
    private WebDriver driver;
    private WaitHelper wait;

    private By applyFiltersButton = By.cssSelector("button.btn-submit.btn--primary");

    public FilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    private void clickFilterTab(String tabName) {
        By tabLocator = By.xpath("//h3[@aria-label='" + tabName + "']");

        WebElement tabElement;
        try {
            tabElement = wait.waitForVisibility(tabLocator);
        } catch (Exception e) {
            System.out.println("Tab " + tabName + " tidak terlihat jelas, memaksa pencarian di DOM...");
            tabElement = driver.findElement(tabLocator);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tabElement);

        try {
            wait.waitForClickable(tabLocator).click();
        } catch (Exception e) {
            System.out.println("Klik tab " + tabName + " terhalang, mengeksekusi JavaScript Click...");
            js.executeScript("arguments[0].click();", tabElement);
        }
    }

    public void applyConditionFilter(String condition) {
        clickFilterTab("Condition");

        By conditionLabelLocator = By.xpath("//label[@for='group-checkbox-" + condition + "']");

        WebElement conditionLabelElement;
        try {
            conditionLabelElement = wait.waitForVisibility(conditionLabelLocator);
        } catch (Exception e) {
            System.out.println("Label " + condition + " tidak terlihat jelas, memaksa pencarian di DOM...");
            conditionLabelElement = driver.findElement(conditionLabelLocator);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", conditionLabelElement);

        try {
            wait.waitForClickable(conditionLabelLocator).click();
        } catch (Exception e) {
            System.out.println("Klik label " + condition + " terhalang, mengeksekusi JavaScript Click...");
            js.executeScript("arguments[0].click();", conditionLabelElement);
        }
    }

    public void applyPriceFilter(String priceElementId) {
        clickFilterTab("Price");
        By priceLabelLocator = By.xpath("//label[@for='" + priceElementId + "']");

        WebElement priceLabelElement;
        try {
            priceLabelElement = wait.waitForVisibility(priceLabelLocator);
        } catch (Exception e) {
            System.out.println("Label harga tidak terlihat jelas, memaksa pencarian di DOM...");
            priceLabelElement = driver.findElement(priceLabelLocator);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", priceLabelElement);

        try {
            wait.waitForClickable(priceLabelLocator).click();
        } catch (Exception e) {
            System.out.println("Klik radio Price terhalang, mengeksekusi JavaScript Click...");
            js.executeScript("arguments[0].click();", priceLabelElement);
        }
    }

    public void applyItemLocationFilter(String locationElementId) {
        clickFilterTab("Item location");

        By locationLabelLocator = By.xpath("//label[@for='" + locationElementId + "']");

        WebElement locationLabelElement;
        try {
            locationLabelElement = wait.waitForVisibility(locationLabelLocator);
        } catch (Exception e) {
            System.out.println("Label lokasi tidak terlihat jelas, memaksa pencarian di DOM...");
            locationLabelElement = driver.findElement(locationLabelLocator);
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", locationLabelElement);

        try {
            wait.waitForClickable(locationLabelLocator).click();
        } catch (Exception e) {
            System.out.println("Klik radio Location terhalang, mengeksekusi JavaScript Click...");
            js.executeScript("arguments[0].click();", locationLabelElement);
        }
    }

    public void clickApply() {
        wait.waitForClickable(applyFiltersButton).click();
    }

    public boolean isPopupDisplayed() {
        try {
            return wait.waitForVisibility(applyFiltersButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}