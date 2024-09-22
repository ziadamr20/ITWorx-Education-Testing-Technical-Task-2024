package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class StepDefinitions {
    private WebDriver driver;
    private Properties config = new Properties();
    private WebDriverWait wait;

    @Given("I navigate to the login page")
    public void i_navigate_to_the_login_page() throws IOException {
        FileInputStream fis = new FileInputStream("src/config.properties");
        config.load(fis);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(config.getProperty("url"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(100));
    }

    @When("I enter valid credentials")
    public void USER_PASS() {
        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("Email")));
        email.sendKeys(config.getProperty("username"));
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputPassword")));
        password.sendKeys(config.getProperty("password"));
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin")));
        login.click();
    }

    @When("I add a course with details")
    public void i_add_a_course() {
        WebElement leftBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btnMinifyMe\"]/em")));
        leftBar.click();
        WebElement coursePage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btnMyCoursesList\"]/em")));
        coursePage.click();
        WebElement addCourse = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnListAddCourse")));
        addCourse.click();
        WebElement courseName = wait.until(ExpectedConditions.elementToBeClickable(By.id("txtCourseName")));
        courseName.sendKeys(config.getProperty("coursename"));
        WebElement subject = wait.until(ExpectedConditions.elementToBeClickable(By.id("courseSubject")));
        Select subjectList = new Select(subject);
        subjectList.selectByValue("number:7");
        WebElement selectGrade = wait.until(ExpectedConditions.elementToBeClickable(By.id("courseGrade")));
        Select gradeList = new Select(selectGrade);
        gradeList.selectByValue("number:11");
        WebElement courseTeacher = wait.until(ExpectedConditions.elementToBeClickable(By.id("teacherOnBehalf")));
        courseTeacher.click();
        WebElement teacher = wait.until(ExpectedConditions.elementToBeClickable(By.id("lblCourseOwnerProfilePic")));
        teacher.click();
        WebElement save = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSaveAsDraftCourse")));
        save.click();
    }

    @Then("I should see the course in the course list")
    public void Corse_Page() {
        WebElement coursePage = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnMyCoursesList")));
        coursePage.click();
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtCourseSearch")));
        search.sendKeys(config.getProperty("coursename"));
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCourseSearch")));
        searchButton.click();
        driver.quit();
    }
}
