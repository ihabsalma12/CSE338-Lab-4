import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;



public class testForm {

    WebDriver driver;
    WebElement element;

    @Before
    public void setup(){
        //tell the system where to find chromedriver
        System.setProperty("webdriver.chrome.driver",
                "src/chromedriver_win32/chromedriver.exe");
        //instantiate driver
        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void tearDown(){
        //close webpage
        driver.quit();
    }

    // NOTE: ASSUME WE IGNORE VALIDATION OF NAME AND JOB FIELDS (no such thing as invalid name or job title)
    // ASSUME WE IGNORE BUTTON SIZE/ COLOR REQUIREMENTS TO BE CHECKED
    // ASSUME WE IGNORE ERROR MESSAGES MAY SHOW UP
    // sendkeys to fname and lname and job title fields and assert that the text ends up the same
    //
    // Test 1: select "High School" from Highest level of education.
    // Assert that "College" and "Grad School" are not selected
    // Test 2: select high school and then college and assert that only college is selected
    //
    // click on the submit button and links to this page after: http://formy-project.herokuapp.com/thanks

    // test first name field
    @Test
    public void firstNameTest(){
        //navigate to the url
        driver.get("http://formy-project.herokuapp.com/form");
        //find element to test
        //WebElement fn = driver.findElement(By.id("first-name"));
        //ensure browser is ready
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //element = wait.until(
        //        ExpectedConditions.presenceOfElementLocated(
        //               By.id("first-name")));

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        element = driver.findElement(By.id("first-name"));
        element.sendKeys("Salma");
        String first_name = element.getAttribute("value");
        assertEquals("Salma", first_name);
        element.clear();


    }

    // test last name field
    @Test
    public void lastNameTest(){
        driver.get("http://formy-project.herokuapp.com/form");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        element = driver.findElement(By.id("last-name"));
        element.sendKeys("Hamed");
        String last_name = element.getAttribute("value");
        assertEquals("Hamed", last_name);
        element.clear();
    }

    // test education radio buttons
    @Test
    public void educationTest(){
        driver.get("http://formy-project.herokuapp.com/form");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        element = driver.findElement(By.id("radio-button-1"));
        element.click();
        assertTrue(element.isSelected());
        WebElement college = driver.findElement(By.id("radio-button-2"));
        WebElement grad = driver.findElement(By.id("radio-button-3"));
        assertFalse(college.isSelected());
        assertFalse(grad.isSelected());
    }

    // test job title field
    @Test
    public void jobTitleTest(){
        driver.get("http://formy-project.herokuapp.com/form");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        element = driver.findElement(By.id("job-title"));
        element.sendKeys("Technical Sales Engineer");
        String job_title = element.getAttribute("value");
        assertEquals("Technical Sales Engineer", job_title);
        element.clear();
    }

    // education radio buttons test 2 - this test will fail because radio buttons don't work correctly
    @Test
    public void educationRadioBtnTest(){
        driver.get("http://formy-project.herokuapp.com/form");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement hs = driver.findElement(By.id("radio-button-1"));
        WebElement college = driver.findElement(By.id("radio-button-2"));
        WebElement grad = driver.findElement(By.id("radio-button-3"));

        hs.click();
        college.click();
        assertTrue(college.isSelected());
        assertFalse(hs.isSelected());
        assertFalse(grad.isSelected());
    }

    // test submit button link
    @Test
    public void submitTest(){
        driver.get("http://formy-project.herokuapp.com/form");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        element = driver.findElement(By.xpath("//a[@class='btn btn-lg btn-primary']"));
        assertTrue(element.isEnabled());
        element.click();
        assertEquals(driver.getCurrentUrl(), "http://formy-project.herokuapp.com/thanks");
    }

    // integration - testing fields and radio buttons to submit a filled form
    @Test
    public void fillFormTest(){
        driver.get("http://formy-project.herokuapp.com/form");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        element = driver.findElement(By.tagName("form"));
        fillFormAndSubmit(element, "Salma", "Hamed", "Solutions Architect", "1");
    }

    // function to fill the form and submit it
    public void fillFormAndSubmit(WebElement element, String fname, String lname, String job, String edu_option){
        WebElement f = element.findElement(By.id("first-name"));
        f.sendKeys(fname);
        WebElement l = element.findElement(By.id("last-name"));
        l.sendKeys(lname);
        WebElement j = element.findElement(By.id("job-title"));
        j.sendKeys(job);

        String rb = "radio-button-" + edu_option;
        WebElement e = element.findElement(By.id(rb));
        e.click();

        WebElement btn = driver.findElement(By.xpath("//a[@class='btn btn-lg btn-primary']"));
        btn.click();
        assertEquals(driver.getCurrentUrl(), "http://formy-project.herokuapp.com/thanks");
    }



}
