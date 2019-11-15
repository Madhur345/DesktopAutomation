package generics;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseTest implements IAutoConstant{
	public WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeTest
	public void setExtentAndStartDriver()  throws IOException, InterruptedException {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myReport.html");

		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("InteliApp Desktop Testing");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("user", "vishal");
		
		Process process = Runtime.getRuntime().exec("taskkill /F /IM Winium.Desktop.Driver.exe");
		process.waitFor();
		process.destroy();
		DesktopOptions option = new DesktopOptions();
		option.setApplicationPath("C:/Program Files (x86)/InteliApp/InteliDesktop.exe");
		WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable
				(new File(WINIUMDRIVER_PATH)).usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
		service.start(); 
		Thread.sleep(5000);
		driver = new WiniumDriver(service, option); 
		Thread.sleep(5000);

		
	}

	@BeforeMethod
	public  void openApplication() throws IOException, InterruptedException, AWTException{
		String email = Lib.getPropertyValue("EMAIL");
		String firstName = Lib.getPropertyValue("FIRSTNAME");
		String lastName = Lib.getPropertyValue("LASTNAME");
		driver.findElement(By.name("Email Address: *")).sendKeys(email);
		Thread.sleep(5000);
		Robot r =new Robot();
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		driver.findElement(By.name("First Name: *")).sendKeys(firstName);
		Thread.sleep(5000);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		driver.findElement(By.name("Last Name: *")).sendKeys(lastName);
		Thread.sleep(5000);
		WebElement login=driver.findElement(By.name("SIGN IN"));
		Actions action = new Actions(driver);
		action.moveToElement(login).click().perform();
		
	}

	@AfterMethod
	public void closeApplication(ITestResult result) throws IOException
	{
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); 
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); 
			String screenshotPath = Lib.captureScreenshots(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
		}
		extent.flush();
	}
}
