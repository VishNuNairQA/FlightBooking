package BookingTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class IndigoTest extends reusableMethods{
	
	@Test
	public void open() throws IOException{
		
		WindowsUtils.killByName("excel.exe");
		
		openBrowser("chrome");
		driver.get(readfromProp("indigoURL"));
	}

	@Test(dependsOnMethods = "open")
	public void OneWayBooking() throws IOException, InterruptedException{
		
		String from = readfromExcel("fare", 1, 0);
		System.out.println("From: " +from);
	
		String to = readfromExcel("fare", 1, 1);
		System.out.println("To: " +to);
		
		String month = readfromExcel("fare", 1, 2);
		String day = readfromExcel("fare", 1, 3);
		
		System.out.println("Date: " +month+ "" +day);
		
		driver.findElement(By.xpath("//div[@id='oneWay']//input[@placeholder='From']")).clear();
		driver.findElement(By.xpath("//div[@id='oneWay']//input[@placeholder='From']")).click();
		
		
		WebElement fromList = driver.findElement(By.xpath("//div[@id='oneWay']//div[@class='city-dropdown-list city-name-from']"));
		
		List<WebElement> fromListLinks = fromList.findElements(By.tagName("a"));
		
		System.out.println("Total State for Selection: " +fromListLinks.size());
		
		for(int i=0; i<fromListLinks.size(); i++){
			
			String fromText = fromList.findElements(By.tagName("a")).get(i).getText();
			
			System.out.println(fromText);
			
			if(fromText.contains(from)){
				fromList.findElements(By.tagName("a")).get(i).click();
				break;
			}
		}
		
		
		////div[@id='oneWay']//div[@class='city-dropdown-list city-name-to']
		
		WebElement toList = driver.findElement(By.xpath("//div[@id='oneWay']//div[@class='city-dropdown-list city-name-to']"));
		
		List<WebElement> toListLinks = toList.findElements(By.tagName("a"));
		
		System.out.println("Total State for Selection: " +toListLinks.size());
		
		for(int i=0; i<toListLinks.size(); i++){
			
			String toText = toList.findElements(By.tagName("a")).get(i).getText();
			
			System.out.println(toText);
			
			if(toText.contains(to)){
				toList.findElements(By.tagName("a")).get(i).click();
				break;
			}
		}
		
		Select selectAdult = new Select(driver.findElement(By.xpath("//div[@id='oneWay']//select[@class='select-custome book-trip-adult']")));
		
		selectAdult.selectByVisibleText("2");
		
		driver.findElement(By.xpath("//div[@id='oneWay']//label[text()='Departure Date']/following-sibling::input")).click();
		
		String currentMonth = driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']/div/div/span[1]")).getText();
		System.out.println("Current Month: " +currentMonth);
		
		while(!driver.findElement(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']/div/div/span[1]")).getText().equalsIgnoreCase(month)){
			driver.findElement(By.xpath("//a/span[text()='Next']")).click();
		}
		
		
		List<WebElement> totalDays = driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//table//a"));
		System.out.println("Total Days in Month: " +month+ " is: " +totalDays.size());
		
		
		for(int j=0; j<totalDays.size(); j++){
			String eachDay = driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//table//a")).get(j).getText();
			
			if(eachDay.equals(day)){
				driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-first']//table//a")).get(j).click();
				break;
			}
		}
		
		driver.findElement(By.xpath("//div[@id='oneWay']//button[text()[normalize-space()='Search Flight']]")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='oneWay indfooter']")));
		
		
		String price = driver.findElement(By.xpath("//div[@class='content-wrapper']/div/div[1]/div[2]/div[1]//span")).getText();
		System.out.println("Price: " +price);
		
		writetoExcel("fare", 1, 4, price);
		
	}
}
