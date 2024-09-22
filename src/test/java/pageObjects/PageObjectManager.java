package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
	
	public WebDriver driver;
	public WebtablesPage webtablesPage;
	
	public PageObjectManager(WebDriver driver)
	{
		this.driver = driver;
	}	
	
	public WebtablesPage getWebtablesPage()
	{	
	webtablesPage= new WebtablesPage(driver);
	 return webtablesPage;
	}
	
}
