package com.divercity.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FooterLinksTest {
	public static void main(String[] args) throws InterruptedException{
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Ramya\\Documents\\geckodriver-v0.10.0-win64\\wires.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		WebDriver driver = new FirefoxDriver(capabilities);	
		final String startPage = "https://develop.divercity.io";

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(startPage );
		driver.manage().window().maximize();
		List<WebElement> footerLinks = driver.findElements(By.xpath("//footer//a"));
		
		Iterator<WebElement> itr = footerLinks.iterator();
	    
	    while(itr.hasNext()) {
	    	WebElement nextElm = itr.next();
			if(nextElm.getAttribute("href").startsWith("mailto:")) {
				continue; // no need to verify
			}
			nextElm.sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));
			Thread.sleep(5000); // waiting for page to load
			ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
		      
		    driver.switchTo().window(newTb.get(1));
			
		    int numberOfLinks = driver.findElements(By.xpath("/html/body//a")).size();
		    
		    driver.close();
		    driver.switchTo().window(newTb.get(0));
		    
		    if(numberOfLinks < 10) { // modern webpages have many more links
		    	System.out.println(nextElm.getText() + " - " + nextElm.getAttribute("href") + " is broken");
		    }
			
	    }
	    driver.close();
	}
}

