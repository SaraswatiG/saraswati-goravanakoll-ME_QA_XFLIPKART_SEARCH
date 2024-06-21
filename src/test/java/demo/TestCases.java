package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    WebDriverWait wait;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01() throws InterruptedException {
        Wrappers wrapper = new Wrappers(driver,wait);

        //Open FLipkart
        wrapper.openURL("https://www.flipkart.com/");
        //Search for washing machine
        wrapper.searchWrapper(By.name("q"),"Washing Machine");
        //Sort by popularity
        wrapper.clickWrapper(By.xpath("//div[text()='Popularity']"));
        wait.until(ExpectedConditions.urlContains("popularity"));

        System.out.println("Count of Washing machine with rating less than or equal to 4 stars : " + wrapper.getCountOfWashingMachineWithRatingAtMostFourStars());
    }

    @Test
    public void testCase02(){
        Wrappers wrapper = new Wrappers(driver,wait);

        wrapper.openURL("https://www.flipkart.com/");
        wrapper.searchWrapper(By.name("q"),"iPhone");
        wrapper.printItemsWithMoreThan17PercentDiscount();

    }

    @Test
    public void testCase03() throws InterruptedException {
        Wrappers wrapper = new Wrappers(driver, wait);

        wrapper.openURL("https://www.flipkart.com/");
        wrapper.searchWrapper(By.name("q"),"Coffee Mug");
        wrapper.selectCheckboxOption("4",By.xpath("//div[text()='Customer Ratings']/../following-sibling::div//div[@class='_6i1qKy']"));
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        wrapper.printTop5ReviewedItems();
    }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeSuite
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterSuite
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}