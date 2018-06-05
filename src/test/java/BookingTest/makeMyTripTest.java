package BookingTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class makeMyTripTest extends reusableMethods{
	
	
	
	@Test
	public void openURL() throws IOException{
		
		openBrowser("chrome");
		
		driver.get(readfromProp("mmtURL"));
		
	}

	
	@Test(dependsOnMethods = "openURL")
	public void OneWayBookingFrom() throws Exception{

		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		
		String from = readfromExcel("makeMyTrip", 1, 0);
		System.out.println("From: " +from);
		
		String fromHint = from.substring(0, 3);
		
		driver.findElement(By.xpath("//input[@id='hp-widget__sfrom']")).clear();
		
		driver.findElement(By.xpath("//input[@id='hp-widget__sfrom']")).sendKeys(fromHint);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='hp-widget__sfrom']")).sendKeys(Keys.DOWN);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		
		String currentEditText = "return document.getElementById(\"hp-widget__sfrom\").value";
		
		String editText = (String) jse.executeScript(currentEditText);
		
		System.out.println("Current From Text: " +editText);
		
		while(!editText.contains(from)){
			
			driver.findElement(By.xpath("//input[@id='hp-widget__sfrom']")).sendKeys(Keys.DOWN);
			
			 editText = (String) jse.executeScript(currentEditText);
			 
			 System.out.println("Current From Text: " +editText);
			
		}
		
		driver.findElement(By.xpath("//input[@id='hp-widget__sfrom']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
	}
	
	@Test(dependsOnMethods = {"openURL", "OneWayBookingFrom"})
	public void OneWayBookingTo() throws Exception{
		
		String to = readfromExcel("makeMyTrip", 1, 1);
		System.out.println("To: " +to);
		
		String toHint = to.substring(0, 4);
		
		driver.findElement(By.xpath("//input[@id='hp-widget__sTo']")).click();
		driver.findElement(By.xpath("//input[@id='hp-widget__sTo']")).clear();
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='hp-widget__sTo']")).sendKeys(toHint);
		
		driver.findElement(By.xpath("//input[@id='hp-widget__sTo']")).sendKeys(Keys.DOWN);
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		
		String currentToText = "return document.getElementById(\"hp-widget__sTo\").value";
		
		String toText = (String) jse.executeScript(currentToText);
		
		System.out.println("Current To Text: " +toText);
		
		
		while(!toText.contains(to)){
			
			driver.findElement(By.xpath("//input[@id='hp-widget__sTo']")).sendKeys(Keys.DOWN);
			
			toText = (String) jse.executeScript(currentToText);
			
			System.out.println("Current To Text: " +toText);
		}
		
		driver.findElement(By.xpath("//input[@id='hp-widget__sTo']")).sendKeys(Keys.ENTER);
	}
		
	
	@Test(dependsOnMethods = {"openURL", "OneWayBookingFrom", "OneWayBookingTo"})
	public void selectDate() throws Exception{
		
		String month = readfromExcel("makeMyTrip", 1, 2);
		
		String day = readfromExcel("makeMyTrip", 1, 3);
		
		System.out.println("Date: " +month+ " " +day);
		
		String currentMonth = driver.findElement(By.xpath("(//div[@class='ui-datepicker-group ui-datepicker-group-first'])[1]/div/div[@class='ui-datepicker-title']/span[1]")).getText();
		
		System.out.println("Current Month: " +currentMonth);
		
		while(!driver.findElement(By.xpath("(//div[@class='ui-datepicker-group ui-datepicker-group-first'])[1]/div/div[@class='ui-datepicker-title']/span[1]")).getText().equalsIgnoreCase(month)){
			driver.findElement(By.xpath("(//a/span[text()='Next'])[1]")).click();
		}
		
		List<WebElement> totalDays = driver.findElements(By.xpath("(//div[@class='ui-datepicker-group ui-datepicker-group-first'])[1]//td/a"));
		
		System.out.println("Total days in month: " +month+ " is: " +totalDays.size());
		
		
		for(int i=0; i<totalDays.size(); i++){
			
			String eachDay = driver.findElements(By.xpath("(//div[@class='ui-datepicker-group ui-datepicker-group-first'])[1]//td/a")).get(i).getText();
			
			if(eachDay.equals(day)){
				driver.findElements(By.xpath("(//div[@class='ui-datepicker-group ui-datepicker-group-first'])[1]//td/a")).get(i).click();
				break;
			}
		}

	}
	
	@Test(dependsOnMethods = {"openURL", "OneWayBookingFrom", "OneWayBookingTo", "selectDate"})
	public void searchFlight() throws Exception{
		driver.findElement(By.xpath("//div/button[text()='Search']")).click();
		
		String flightPrice = driver.findElement(By.xpath("//div[@id='sortingRow']/following-sibling::div[1]/div/div[2]/div[contains(@class, 'price')]/p/span[2]")).getText();
	
		System.out.println("Price: " +flightPrice);
		writetoExcel("makeMyTrip", 1, 4, flightPrice);
	}
	
}
