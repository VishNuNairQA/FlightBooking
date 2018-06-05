package BookingTest;

import java.io.IOException;

import org.testng.annotations.Test;

public class PayTMTest extends reusableMethods{
	
	@Test
	public void test() throws IOException{
		
		openBrowser("chrome");
		driver.get(readfromProp("PayTMURL"));
	}

}
