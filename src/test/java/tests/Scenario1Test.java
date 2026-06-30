package tests;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.FilterPage;
import pages.HomePage;
import pages.SearchResultPage;
import utils.ConfigReader;
import utils.TestListenerUI;
import utils.WaitHelper;

@Listeners(utils.TestListenerUI.class)
public class Scenario1Test extends BaseTest {

    @Test(description = "Scenario 1: Access a Product via category after applying multiple filters")
    public void testApplyMultipleFilters() {
        HomePage homePage = new HomePage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        FilterPage filterPage = new FilterPage(driver);
        WaitHelper waitHelper = new WaitHelper(driver);

        String mainCategory = ConfigReader.getProperty("ebay.category.main");
        String subCategory = ConfigReader.getProperty("ebay.category.sub");
        String condition = ConfigReader.getProperty("ebay.filter.condition");
        String priceId = ConfigReader.getProperty("ebay.filter.price.id");
        String locationId = ConfigReader.getProperty("ebay.filter.location.id");

        TestListenerUI.getTest().log(Status.INFO, "Memulai pengujian.");
        Assert.assertTrue(driver.getCurrentUrl().contains("ebay.com"), "ASSERT FAILED: Tidak di eBay!");

        TestListenerUI.getTest().log(Status.INFO, "Step 1: Navigasi kategori: " + mainCategory);
        homePage.clickShopByCategory();
        homePage.selectMainCategory(mainCategory);
        waitHelper.waitForUrlToContain("ebay.com/b/", 30);
        Assert.assertTrue(driver.getCurrentUrl().contains("/b/"), "ASSERT FAILED: Gagal menavigasi ke Main Category dalam 30 detik!");

        TestListenerUI.getTest().log(Status.INFO, "Step 2: Navigasi sub-kategori: " + subCategory);
        categoryPage.selectLeftHandNavigation(subCategory);
        waitHelper.waitForTitleToContain("Cell", 15);
        Assert.assertTrue(driver.getTitle().contains("Cell Phone") || driver.getTitle().contains("Smartphones"), "ASSERT FAILED: Gagal ke Sub-Kategori!");

        TestListenerUI.getTest().log(Status.INFO, "Step 3: Membuka pop-up All Filters.");
        searchResultPage.clickAllFilters();
        waitHelper.waitForElementVisible(filterPage.getPopupLocator(), 15);
        Assert.assertTrue(filterPage.isPopupDisplayed(), "ASSERT FAILED: Pop-up tidak muncul!");

        TestListenerUI.getTest().log(Status.INFO, "Step 4: Menerapkan 3 filter.");
        filterPage.applyConditionFilter(condition);
        filterPage.applyPriceFilter(priceId);
        filterPage.applyItemLocationFilter(locationId);
        filterPage.clickApply();

        TestListenerUI.getTest().log(Status.INFO, "Step 5: Verifikasi badge filter.");

        try {
            waitHelper.waitForElementVisible(searchResultPage.getFilterBadgeLocator(), 30);
            boolean isFilterApplied = searchResultPage.verifyFiltersApplied(3);
            Assert.assertTrue(isFilterApplied, "VERIFICATION FAILED: Badge tidak muncul/angka tidak sesuai!");
        } catch (Exception e) {
            TestListenerUI.getTest().log(Status.FAIL, "Step 5 Gagal: " + e.getMessage());
            Assert.fail("Badge filter tidak ditemukan setelah menunggu 30 detik");
        }

        TestListenerUI.getTest().log(Status.INFO, "Step 6: Mengeklik badge filter.");
        searchResultPage.clickAppliedFilterBadge();
    }
}