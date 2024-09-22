package stepDefinitions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.WebtablesPage;
import utils.TestContextSetup;

public class WebtablesPageStepDefinition {
	
	public WebDriver driver;
	public WebtablesPage webtablesPage;
	TestContextSetup testContextSetup;
	
	
	public WebtablesPageStepDefinition(TestContextSetup testContextSetup)
	{
		this.testContextSetup=testContextSetup;
		this.webtablesPage = testContextSetup.pageObjectManager.getWebtablesPage();
	}
	
	@Given("^I am on the webtables page$")
    public void navigateToWebTablesPage() {
		Assert.assertTrue(webtablesPage.getCurrentPageUrl().contains("webtables"), "User is redirected to correct url");
		Assert.assertTrue(webtablesPage.getCurrentPageTitle().contains("WebTables"), "User lands on WebTables page");
    }

	
	@When("^I add a user with the following details:$")
	public void addUser(DataTable userDetails) {
	    List<String> userData = userDetails.asList(String.class);

	    webtablesPage.addUser(userData);
	}

    @Then("^I should see the user \\\"([^\\\"]*)\\\" in the webtables$")
    public void verifyUserInTable(String username) {
        Assert.assertTrue(webtablesPage.isUserPresent(username), username+" is displayed in the table");
    }

    @When("^I delete the user \"([^\"]*)\"$")
    public void deleteUser(String username) {
        webtablesPage.deleteUser(username);
    }

    @Then("^I should not see the user \"([^\"]*)\" in the webtables$")
    public void verifyUserNotInTable(String username) {
        Assert.assertTrue(webtablesPage.isUserNotPresent(username), username+" is not displayed in the table");
    }
   

}
