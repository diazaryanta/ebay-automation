package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;
import java.time.Duration;

public class SearchResultPage {
    private WebDriver driver;
    private WaitHelper wait;

    private By allFiltersButton = By.cssSelector("button.filter-button.brwr__all-filters");

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    private By appliedFilterButton = By.cssSelector("button.filter-button.filter-button--selected");

    public void clickAllFilters() {
        wait.waitForClickable(allFiltersButton).click();
    }

    public boolean verifyFiltersApplied(int expectedCount) {
        By appliedFilterBadge = By.cssSelector("li.brwr__item.brwr__item--applied");

        try {
            WebDriverWait wait5Sec = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement badgeElement = wait5Sec.until(ExpectedConditions.visibilityOfElementLocated(appliedFilterBadge));
            String badgeText = badgeElement.getText();
            System.out.println("Teks badge filter yang muncul: " + badgeText);

            return badgeText.contains(String.valueOf(expectedCount));
        } catch (Exception e) {
            System.out.println("Gagal: Badge filter tidak muncul setelah ditunggu 5 detik!");
            return false;
        }
    }

    public void clickAppliedFilterBadge() {
        try {
            wait.waitForClickable(appliedFilterButton).click();
            System.out.println("Berhasil mengeklik tombol badge '3 filters applied'.");
        } catch (Exception e) {
            System.out.println("Klik tombol badge terhalang, mengeksekusi JavaScript Click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement btn = driver.findElement(appliedFilterButton);
            js.executeScript("arguments[0].click();", btn);
        }
    }

    public void selectLeftCategory(String categoryName) {
        By categoryLocator = By.xpath("//li[contains(@class, 'srp-refine__category__item')]//span[text()='" + categoryName + "']");
        wait.waitForClickable(categoryLocator).click();
    }

    public boolean isPageReady() {
        WebDriverWait waitLoad = new WebDriverWait(driver, Duration.ofSeconds(10));
        return waitLoad.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getFirstResultTitle() {
        org.openqa.selenium.JavascriptExecutor globalJs = (org.openqa.selenium.JavascriptExecutor) driver;
        globalJs.executeScript("window.scrollBy(0, 500);");

        By itemTitlesLocator = By.cssSelector(".s-card__title span");

        try {
            org.openqa.selenium.support.ui.WebDriverWait pollingWait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));

            return pollingWait.until(webDriver -> {
                java.util.List<org.openqa.selenium.WebElement> titles = webDriver.findElements(itemTitlesLocator);

                for (org.openqa.selenium.WebElement title : titles) {
                    try {
                        String text = title.getAttribute("textContent");

                        if (text != null) {
                            text = text.trim();

                            if (!text.isEmpty() && !text.toLowerCase().contains("shop on ebay")) {
                                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) webDriver;
                                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", title);
                                return text;
                            }
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException e) {
                        return null;
                    }
                }
                return null;
            });
        } catch (Exception e) {
            System.out.println("Polling timeout: Gagal menemukan judul setelah berulang kali mencoba.");
            return "Judul tidak ditemukan";
        }
    }
}