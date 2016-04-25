package com.albee.webPages;

import org.openqa.selenium.By; // This import is to use By in findElement
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.albee.DataBeans.LoginData;

public class Loginpage extends Basepage {

	public Loginpage(WebDriver driver) throws InterruptedException {
		super.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='landingReturningCustomersEmail']")
	WebElement fieldUsername;

	@FindBy(xpath = "//input[@id='landingReturningCustomersPassword']")
	WebElement fieldPassword;

	@FindBy(xpath = "//a[@class='landingForgotPassword']")
	WebElement linkForgotPassword;

	@FindBy(xpath = "//input[@value='Sign In']")
	WebElement buttonGo;

	public void userAccountLogin(String type, LoginData ld) throws InterruptedException {
		// String[] validType = {"RightUser_RightPwd","RightUser_WrongPwd"};
		String[] validType = ld.getLogintype();
		Homepage hp = new Homepage(driver);

		for (int i = 0; i < ld.getLogintype().length; i++) {
			for (int j = 0; j < validType.length; j++) {
				if ((ld.getLogintype()[i] == type) && (ld.getLogintype()[i] == validType[j])) {
					System.out.println("Matched type : >>>" + validType[j]);
					System.out.println(ld.getEmail()[i] + "<<<<<<>>>>>>" + ld.getPassword()[i]);
					fieldUsername.sendKeys(ld.getEmail()[i]);
					fieldPassword.sendKeys(ld.getPassword()[i]);
					buttonGo.click();
				}
			}
			hp.navAccount();
		}

		// for (int i = 0; i < ld.getLogintype().length; i++) {
		//
		// if ((ld.getLogintype()[i] == type) && (ld.getLogintype()[i] ==
		// validType)) {
		// System.out.println("Username and password passed in the program is
		// :-" + ld.getLogintype()[i]);
		// fieldUsername.sendKeys(ld.getEmail()[i]);
		// fieldPassword.sendKeys(ld.getPassword()[i]);
		// buttonGo.click();
		// } else {
		// // fieldUsername.clear();
		// fieldUsername.sendKeys(ld.getEmail()[i]);
		// // fieldPassword.clear();
		// fieldPassword.sendKeys(ld.getPassword()[i]);
		// buttonGo.click();
		// System.out.println("Incorrect Credentials");
		//
		// hp.navAccount();
		//
		// }
		//

		// }

	}

}
