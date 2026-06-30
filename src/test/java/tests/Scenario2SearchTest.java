package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultPage;
import utils.ConfigReader;
import utils.TestListenerUI;
import utils.WaitHelper;

@Listeners(utils.TestListenerUI.class)
public class Scenario2SearchTest extends BaseTest {

    @Test(description = "Scenario 2: Access a Product via Search")
    public void testProductSearch() {
        HomePage homePage = new HomePage(driver);
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        WaitHelper waitHelper = new WaitHelper(driver);

        String keyword = ConfigReader.getProperty("ebay.search.keyword");
        String category = ConfigReader.getProperty("ebay.search.category");

        TestListenerUI.getTest().log(Status.INFO, "Step 1: Membuka website eBay.");
        Assert.assertTrue(driver.getCurrentUrl().contains("ebay.com"), "ASSERT FAILED: URL tidak sesuai!");

        TestListenerUI.getTest().log(Status.INFO, "Step 2: Mengetikkan kata kunci '" + keyword + "' pada Search Bar.");
        waitHelper.waitForElementVisible(org.openqa.selenium.By.id("gh-ac"), 15);
        homePage.enterSearchKeyword(keyword);

        TestListenerUI.getTest().log(Status.INFO, "Step 3: Mengeklik tombol Search.");
        homePage.clickSearch();

        searchResultPage.isPageReady();
        String urlSafeKeyword = keyword.replace(" ", "+");
        waitHelper.waitForUrlToContain("nkw=" + urlSafeKeyword, 15);
        Assert.assertTrue(driver.getCurrentUrl().contains("nkw=" + urlSafeKeyword), "ASSERT FAILED: URL pencarian gagal dimuat!");

        TestListenerUI.getTest().log(Status.INFO, "Step 4: Mengganti Search Category menjadi '" + category + "'.");
        searchResultPage.selectLeftCategory(category);

        searchResultPage.isPageReady();
        Assert.assertTrue(driver.getCurrentUrl().contains("sch"), "ASSERT FAILED: URL kategori gagal dimuat!");

        TestListenerUI.getTest().log(Status.INFO, "Step 5: Memverifikasi bahwa halaman termuat sepenuhnya.");
        Assert.assertTrue(searchResultPage.isPageReady(), "ASSERT FAILED: Halaman tidak termuat!");

        TestListenerUI.getTest().log(Status.INFO, "Step 6: Memverifikasi judul produk.");
        String firstProductTitle = searchResultPage.getFirstResultTitle();
        TestListenerUI.getTest().log(Status.INFO, "Judul produk: " + firstProductTitle);

        String[] wordsToCheck = keyword.toLowerCase().split(" ");
        boolean containsAllKeywords = true;
        String kataYangBikinGagal = "";

        for (String word : wordsToCheck) {
            if (!firstProductTitle.toLowerCase().contains(word)) {
                containsAllKeywords = false;
                kataYangBikinGagal = word;
                break;
            }
        }

        Assert.assertTrue(containsAllKeywords,
                "VERIFICATION FAILED: Judul produk (" + firstProductTitle + ") tidak mengandung kata: '" + kataYangBikinGagal + "'!");
    }
}