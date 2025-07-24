package listener;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.google.common.io.Files;

public class MyListenerImplementationClass implements ITestListener{
	
	
	  public void onTestSuccess(ITestResult result) {
		  System.out.println("Test Success");
	    
	  }

	  public void onTestFailure(ITestResult result) {

		  TakesScreenshot tks = (TakesScreenshot)base.BaseTest.driver; //we are downcasting to the driver
			
			File temp = tks.getScreenshotAs(OutputType.FILE);
			
			File dest = new File("./screenshot/saucedemo" +System.currentTimeMillis()+ ".png");	
			try {
				Files.copy(temp, dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  
	
	   
}
}
