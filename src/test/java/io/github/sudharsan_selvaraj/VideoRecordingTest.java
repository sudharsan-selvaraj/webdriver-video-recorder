package io.github.sudharsan_selvaraj;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sudharsan_selvaraj.autowait.SeleniumWaitOptions;
import io.github.sudharsan_selvaraj.autowait.SeleniumWaitPlugin;
import io.github.sudharsan_selvaraj.videorecorder.VideoOptions;
import io.github.sudharsan_selvaraj.videorecorder.WebDriverVideoRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class VideoRecordingTest {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();

        SpyDriver<WebDriver> chromeDriverSpyDriver = new SpyDriver<>(new FirefoxDriver());
        VideoOptions options = new VideoOptions("/Users/sselvaraj/Documents/git/personal/webdriver-video-recorder/video.mp4", 1);
        SeleniumWaitPlugin<WebDriver> waitPlugin = new SeleniumWaitPlugin<WebDriver>(chromeDriverSpyDriver, SeleniumWaitOptions.builder().build());
        WebDriverVideoRecorder videoRecorder = new WebDriverVideoRecorder(chromeDriverSpyDriver, options);

        WebDriver driver = chromeDriverSpyDriver.getSpyDriver();
        driver.manage().window().setSize(new Dimension(1500, 1000));
        amazon(driver);
        driver.manage().window().setSize(new Dimension(1500, 1000));
        react(driver);
        driver.quit();
    }


    public static void amazon(WebDriver driver) {
        try {
            driver.get("https://www.amazon.in");
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys("oneplus 7");
            driver.findElement(By.id("nav-search-submit-text")).click();
            driver.findElement(By.partialLinkText("OnePlus 7 Pro")).click();
            driver.switchTo().window(driver.getWindowHandles().toArray(new String[]{})[1]);
            driver.findElement(By.id("add-to-cart-button")).click();

            driver.manage().window().maximize();
            driver.manage().window().setSize(new Dimension(1000, 700));

            ((JavascriptExecutor) driver).executeScript("console.log('hi')");
            ((JavascriptExecutor) driver).executeAsyncScript("(function(callback){ callback()})(arguments[0])");

            /* This will trigger the exception */
            driver.findElement(By.id("attach-view-cart-button-form")).sendKeys("sdfsdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void react(WebDriver driver) {
        driver.get("https://react-redux.realworld.io/");
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("abc@tester.com");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Qwerty@123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.partialLinkText("TesterABC123")).click();
        driver.findElement(By.partialLinkText("Home")).click();
        driver.findElement(By.xpath(".//*[contains(@class,'tag-pill')][text()='HITLER']")).click();
    }

}
