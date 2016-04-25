package com.albee.webPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage extends Basepage {
	
	@FindBy(xpath="//div[@class='links']//a[text()='My Account']")
	WebElement linkMyAccount;

	@FindBy(xpath="//div[@class='pdnav-container']//a[text()='Car Seats']")
	WebElement menuCarSeat;
	
	public Homepage(WebDriver driver) throws InterruptedException {
		super.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void navAccount() {
	//	driver.navigate().refresh(); //refreshing the page
		linkMyAccount.click();
	//	driver.navigate().back();  //just navigating back for testing
	//	driver.navigate().forward(); //just navigating forward for testing
	}
	
	public void navCarSeat() {
		menuCarSeat.click();
	}
	
	/*public void navCategory(category) {
		menuCategory.click();
	}*/
	
	
}
