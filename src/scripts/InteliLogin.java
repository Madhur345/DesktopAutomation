package scripts;


import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import generics.BaseTest;

public class InteliLogin extends BaseTest {

	@Test
	public void successfulLogin()throws  InterruptedException, MalformedURLException {

		test = extent.createTest("Login to inteliApp");
		test.info("Starting Testing");
		Thread.sleep(3000);
		WebElement home =driver.findElement(By.linkText("Home"));
		Actions action = new Actions(driver);
		action.moveToElement(home).click().perform();
		Thread.sleep(5000);
		test.pass("Loggin successfully");
	}
}