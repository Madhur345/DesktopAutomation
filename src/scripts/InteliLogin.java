package scripts;


import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import generics.BaseTest;

public class InteliLogin extends BaseTest {

	@Test
	public void successfulLogin()throws  InterruptedException, MalformedURLException {

		test = extent.createTest("Login to inteliApp");
		test.info("Starting Testing");
		WebElement title=	driver.findElement(By.name("Inte"));
		if(title.isDisplayed()) {
			System.out.println("Login successfull");
		}
		Thread.sleep(5000);
		test.pass("Loggin successfully");
	}
}