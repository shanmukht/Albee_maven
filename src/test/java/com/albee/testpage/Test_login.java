package com.albee.testpage;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.albee.DataBeans.DbconInMemory;
import com.albee.DataBeans.LoginData;
import com.albee.DataBeans.ShippingData;
import com.albee.DataBeans.WebserviceData;
import com.albee.report.Test_report_ireport;
import com.albee.webPages.Basepage;
import com.albee.webPages.Categorypage;
import com.albee.webPages.Checkoutpage;
import com.albee.webPages.Homepage;
import com.albee.webPages.Loginpage;
import com.albee.webPages.Paymentpage;
import com.albee.webPages.Productpage;
import com.albee.webServices.RestWebService;
import com.albee.webServices.RestWithBasicAuth;







@Listeners(value=Test_report_ireport.class)   //To generate test reports
public class Test_login extends Basepage{

	@Test
	public void Login() throws InterruptedException, SQLException{
		
		DbconInMemory dbconin = new DbconInMemory();
		

		LoginData ldt  = dbconin.getLoginData();
		WebserviceData wdt= dbconin.getWebserviceData();
		
		RestWithBasicAuth rAuth= new RestWithBasicAuth();  
		rAuth.restApiwithAuth(wdt);
		
		RestWebService  rws = new RestWebService();  //rest api call
		rws.RestApi(null);
		
		
		
		WebDriver driver=new FirefoxDriver();

//		WebDriver driver = new HtmlUnitDriver();
		
//		System.setProperty("webdriver.chrome.driver","/Users/NIS1631-mbpr/Documents/chromedriver");
//
//		WebDriver driver = new ChromeDriver();
		
//Below 4 lines code to initiate REST api calls
		
		
		String appUrl= "http://www.albeebaby.com";

//		((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		driver.get(appUrl); //open an url with this or one below
//		driver.navigate().to(appUrl); //open url
/*	
 * System.out.println("Homepage title:-" + driver.getTitle());
 		System.out.println("Page Source" + driver.getPageSource());
		String currentUrl= driver.getCurrentUrl();
		
		if (appUrl.equals(currentUrl))  {
			System.out.println("expected and current URL matches");
		 }
		else {
			System.out.println("expected URL should be :" + appUrl);
			System.out.println("But actual URL is:" + currentUrl);
		}
			/html/body/div[2]/div[2]/ul/li[1]/a
*/		
//		Actions action = new Actions(driver);
//		WebElement we=driver.findElement(By.xpath("/html/body/div[2]/div[2]/ul/li[1]/a"));
//		action.moveToElement(we).moveToElement(driver.findElement(By.href("Car Seat Sale"))).click().build().perform();
		
		

		
	

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println(">>>" +ldt.getEmail());
		
		driver.manage().deleteAllCookies();  //to delete cache of browser;
	//	driver.manage().window().maximize();
	//	maxMemory();
	//	totalMemory();


		ShippingData sdt = dbconin.getShippingData();
		System.out.println(">>>" +sdt.getFname());


		Homepage hp = new Homepage(driver);
		Thread.sleep(5000);
		
		hp.navAccount();	

		Loginpage lp = new Loginpage(driver);
	//	lp.userAccountLogin(ldt);
		lp.userAccountLogin("WrongUser_RightPwd",ldt);
	//	driver.navigate().back();
		lp.userAccountLogin("RightUser_WrongPwd",ldt);
		lp.userAccountLogin("EmptyUser_EmptyPwd",ldt);
		lp.userAccountLogin("RightUser_RightPwd",ldt);
//		
		
//		boolean status = driver.findElement(By.name("email")).isDisplayed();
//		System.out.println(driver.findElement(By.name("email")).getLocation()+ "->is Location of element");
//	System.out.println("Status=" + status);
//		
//		if (status==true) {
//			lp.userAccountLogin(ldt);
		
//		}
//		else {
//			System.out.println("Status=" + status);
//			System.out.println("Username and password elements still not loaded/present");
//		}
		

		hp.navCarSeat();

		Categorypage ct = new Categorypage(driver);
		ct.navCarSeatSale();
		ct.navProducts();

		Productpage pdp = new Productpage(driver);
		pdp.addtoCart();

		Checkoutpage cop = new Checkoutpage(driver);
		cop.checkOut();

		Paymentpage pp = new Paymentpage(driver);
		pp.userEmail(sdt);
		pp.specialOfferCheck();
		pp.userShippingDetails(sdt);
		System.out.println(">>>in the end" +sdt.getFname());
		
		
				
		driver.quit();

	}





}
