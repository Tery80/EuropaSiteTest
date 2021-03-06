import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EuropaEuTest {
    private final By LANGUAGES = By.xpath(".//li[contains(@id,'lang')]");
    private final By LATVIAN = By.id("lang_lv");
    private final By SEARCH_FIELD = By.id("search");
    private final By SEARCH = By.xpath(".//input[contains(@class, 'btn-search')]");
    private final By ARTICLE = By.xpath(".//div[@class='res-detail']");
    private final Logger LOGGER = LogManager.getLogger(EuropaEuTest.class);
    private final String CHROME_DRIVER_PATH = "c:/temp1/chromedriver.exe";
    WebDriver driver;

    @Test
    public void europaEU() {
        long startTime = System.currentTimeMillis();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        driver = new ChromeDriver(options);

        LOGGER.info("Open the first page");
        driver.get("http://europa.eu/");

        String checkUrl = driver.getCurrentUrl();

        LOGGER.info("Check if Redirect to https present");
        Assertions.assertTrue(checkUrl.startsWith("https"), "Missing redirect to https");

        LOGGER.info("Check if age contains 24 languages");
        List<WebElement> languagesElements = driver.findElements(LANGUAGES);
        Assertions.assertEquals(languagesElements.size(), 24, "Languages amount is not 24");

        LOGGER.info("Looking for a Latvian Language");
        driver.findElement(LATVIAN).click();
        LOGGER.info("Waiting 5 sec while loading");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        LOGGER.info("At Input field set text iestāde, and search by that");
        Assertions.assertTrue(driver.findElement(SEARCH_FIELD).isDisplayed(), "There are no Search field");
        driver.findElement(SEARCH_FIELD).sendKeys("iestāde");
        driver.findElement(SEARCH).click();

        LOGGER.info("Check that there are at least 1 article");
        List<WebElement> articles = driver.findElements(ARTICLE);
        Assertions.assertTrue(articles.size() > 0, "There are no articles at all, not 1");
        System.out.println("There are " + articles.size() + " articles");
        driver.close();
        long finishTime = System.currentTimeMillis();
        LOGGER.info("Display working time in seconds"); 
        System.out.println("Test has been worked " + TimeUnit.MILLISECONDS.toSeconds(finishTime - startTime) + " seconds");
    }
}
