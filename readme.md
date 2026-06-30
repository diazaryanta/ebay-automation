# 🛒 eBay Test Automation Framework

This project is an automated testing framework for the **[eBay](https://www.ebay.com/)** platform. It utilizes the **Page Object Model (POM)** design pattern to ensure modular, maintainable, and scalable UI automation scripts using Java, Selenium WebDriver, and TestNG.

---

## 🛠️ Technologies & Libraries Used

* **Programming Language:** Java 21
* **Build Tool:** Gradle
* **Testing Framework:** TestNG
* **UI Automation:** Selenium WebDriver
* **Reporting:** ExtentReports (Spark Reporter)
* **Utilities:** Rest-Assured (for API-integrated scenarios), Jackson Databind

---

## 📁 Project Structure

```text
ebay-automation/
├── src/test/java/
│   ├── base/                # WebDriver setup (BaseTest)
│   ├── pages/               # Page Object Model (HomePage, SearchResultPage, etc.)
│   ├── tests/               # Test Scenarios (Scenario1Test, Scenario2SearchTest)
│   └── utils/               # Helpers (ConfigReader, WaitHelper, ExtentReportManager)
├── src/test/resources/
│   └── staging.properties   # Configuration for eBay URLs and test data
├── reports/                 # Auto-generated ExtentReports
└── build.gradle.kts         # Gradle dependency management
```
---
## 🧪 Test Scenarios

| **Feature** | **Status** |                                        **Description**                                        |
|:------------|:----------:|:---------------------------------------------------------------------------------------------:|
| Scenario 1  |     ✅      | Access a product via categories after applying multiple filters (Condition, Price, Location). |
| Scenario 2  |     ✅      |               Access a product via the search bar and verify result relevance.                |
---
## 📥 Installation & Setup
1. **Clone the Repository:**
```bash
git clone [https://github.com/diazaryanta/ebay-automation.git
```
2. **Configure:**
   Update `src/test/resources/staging.properties` with your desired search keywords, categories, and filter IDs.
3. **Running Test**
* **Via IntelliJ:** Run individual test classes in `src/test/java/tests/`.
* **Via Terminal:**
```bash
./gradlew clean test
```
---
## 🧠 Key Features
* **WaitHelper:** Custom wrapper for `WebDriverWait` to ensure robust element interaction without `Thread.sleep()`.
* **Dynamic JavaScript Execution:** Automatic fallback to `JavascriptExecutor` for elements that are hidden or blocked by overlays.
* **ExtentReports Integration:** Generates a professional dark-themed HTML report with automatic screenshot capture on test failure.
* **Smart Search:** Handles dynamic elements in the search bar and category selection using XPath and CSS Selectors.
---
## 📈 How to View Reports
After running the tests, an interactive HTML report will be generated in the `reports/` folder. Open `ExtentReport.html` in your web browser to view the test status, logs, and failure screenshots.

---
## 👨🏻‍💻 Tester
**DIAZ**