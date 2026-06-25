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

@Listeners(utils.TestListenerUI.class)
public class Scenario1Test extends BaseTest {

    @Test(description = "Scenario 1: Access a Product via category after applying multiple filters")
    public void testApplyMultipleFilters() {
        HomePage homePage = new HomePage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        FilterPage filterPage = new FilterPage(driver);

        String mainCategory = ConfigReader.getProperty("ebay.category.main");
        String subCategory = ConfigReader.getProperty("ebay.category.sub");
        String condition = ConfigReader.getProperty("ebay.filter.condition");
        String priceId = ConfigReader.getProperty("ebay.filter.price.id");
        String locationId = ConfigReader.getProperty("ebay.filter.location.id");

        TestListenerUI.getTest().log(Status.INFO, "Memulai pengujian dan memastikan URL awal adalah eBay.");
        Assert.assertTrue(driver.getCurrentUrl().contains("ebay.com"), "ASSERT FAILED: Tidak berada di halaman eBay!");

        TestListenerUI.getTest().log(Status.INFO, "Step 1: Navigasi ke kategori utama: " + mainCategory);
        homePage.clickShopByCategory();
        homePage.selectMainCategory(mainCategory);
        Assert.assertTrue(driver.getCurrentUrl().contains("/b/"), "ASSERT FAILED: Gagal menavigasi ke halaman Main Category!");

        TestListenerUI.getTest().log(Status.INFO, "Step 2: Navigasi menu kiri memilih sub-kategori: " + subCategory);
        categoryPage.selectLeftHandNavigation(subCategory);
        Assert.assertTrue(driver.getTitle().contains("Cell Phone") || driver.getTitle().contains("Smartphones"), "ASSERT FAILED: Gagal menavigasi ke Sub-Kategori!");

        TestListenerUI.getTest().log(Status.INFO, "Step 3: Membuka pop-up All Filters.");
        searchResultPage.clickAllFilters();
        Assert.assertTrue(filterPage.isPopupDisplayed(), "ASSERT FAILED: Pop-up 'All Filters' tidak muncul atau gagal diklik!");

        TestListenerUI.getTest().log(Status.INFO, "Step 4: Menerapkan 3 filter (Condition: " + condition + ", Price, dan Location).");
        filterPage.applyConditionFilter(condition);
        filterPage.applyPriceFilter(priceId);
        filterPage.applyItemLocationFilter(locationId);
        filterPage.clickApply();

        TestListenerUI.getTest().log(Status.INFO, "Step 5: Memverifikasi badge angka filter (3) muncul pada tombol filter utama.");
        boolean isFilterApplied = searchResultPage.verifyFiltersApplied(3);
        Assert.assertTrue(isFilterApplied, "VERIFICATION FAILED: Filter badge (3) tidak ditemukan pada tombol filter setelah di-apply!");

        TestListenerUI.getTest().log(Status.INFO, "Step 6: Mengeklik badge '3 filters applied' di atas hasil pencarian.");
        searchResultPage.clickAppliedFilterBadge();
    }
}