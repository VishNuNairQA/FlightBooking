package BookingTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class reusableMethods extends testNGListener {
	
	WebDriver driver;
	Properties prop;
	FileInputStream fis, fisExcel;
	FileOutputStream fout;
	
	public void openBrowser(String browserName){
		
		switch(browserName){
		
		case "chrome":
			
			ChromeOptions opt = new ChromeOptions();
			opt.addArguments("start-maximized");
			opt.addArguments("disable-notifications");
			//opt.addArguments("incognito");
			
			System.setProperty("webdriver.chrome.driver", "D:\\Selenium Library\\Chrome\\chromedriver.exe");
			driver = new ChromeDriver(opt);
			break;
			
		case "firefox":
		
			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile fp = profile.getProfile("VishNuNair");
			fp.setPreference("browser.privatebrowsing.autostart", true);
			fp.setPreference("dom.webnotifications.enabled", false);
			
			DesiredCapabilities dc = DesiredCapabilities.firefox();
			dc.setCapability(FirefoxDriver.PROFILE, fp);
			
			FirefoxOptions fo = new FirefoxOptions();
			fo.merge(dc);
			
			System.setProperty("webdriver.gecko.driver", "D:\\Selenium Library\\Gecko\\geckodriver.exe");
			driver = new FirefoxDriver(fo);
			driver.manage().window().maximize();
			break;
			
		default:
			
			System.out.println("Oops! We don't have drivers for " +browserName);
			break;
			
		}
	}
	
	
	public String readfromProp(String url) throws IOException{
		
		fis = new FileInputStream("C:\\Users\\vishn\\Test\\info.properties");
		prop = new Properties();
		prop.load(fis);
		String link = prop.getProperty(url);
		return link;
		
	}
	
	public String readfromExcel(String worksheet, int rowno, int cellno) throws IOException{
		
		fisExcel = new FileInputStream("D:\\Selenium Library\\booking.xlsx");
		
		XSSFWorkbook wb = new XSSFWorkbook(fisExcel);
		XSSFSheet sheet = wb.getSheet(worksheet);
		XSSFRow row = sheet.getRow(rowno);
		XSSFCell cell = row.getCell(cellno);
		cell.setCellType(CellType.STRING);
		String value = cell.getStringCellValue();
		
		return value;
	}
	
	public void writetoExcel(String worksheet, int rownum, int colnum, String str) throws IOException{
		
		fisExcel = new FileInputStream("D:\\Selenium Library\\booking.xlsx");
		
		XSSFWorkbook wb = new XSSFWorkbook(fisExcel);
		XSSFSheet sheet = wb.getSheet(worksheet);
		XSSFRow row = sheet.getRow(rownum);
		XSSFCell cell = row.createCell(colnum);
		cell.setCellType(CellType.STRING);
		
		cell.setCellValue(str);	
		
		fout = new FileOutputStream("D:\\Selenium Library\\booking.xlsx");
		wb.write(fout);
		
		fout.close();
	}

}
