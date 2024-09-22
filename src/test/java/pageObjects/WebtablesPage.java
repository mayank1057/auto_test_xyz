package pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebtablesPage {

	public WebDriver driver;

	public WebtablesPage(WebDriver driver) {
		this.driver = driver;
	}

	By addUserLink = By.xpath("//button[@type='add']");
	By firstNameTextBox = By.xpath("//input[@name='FirstName']");
	By lastNameTextBox = By.xpath("//input[@name='LastName']");
	By userNameTextBox = By.xpath("//input[@name='UserName']");
	By passwordTextBox = By.xpath("//input[@name='Password']");
	By emailTextBox = By.xpath("//input[@name='Email']");
	By cellPhoneTextBox = By.xpath("//input[@name='Mobilephone']");
	By saveButton = By.xpath("//button[@ng-click='save(user)']");
	By roleDropdown = By.xpath("//select[@name='RoleId']");
	By tableBody = By.xpath("//table/tbody/tr");

	By currentElementDeleteButton = By.xpath(".//button[text()='Delete']");
	By confirmDelete = By.xpath("//button[text()='OK']");


	public By getCustomerOption(String customerValue) {
		return By.xpath("//label[normalize-space()='" + customerValue + "']");
	}

	public By getRoleDropdownOption(String option) {
		return By.xpath("//option[text()='" + option + "']");
	}

	public By deleteButton(String username) {
		return By.xpath("//td[normalize-space()='" + username + "']/following-sibling::td/button[@ng-click='delUser()']");
	}

	public By getUsername(String username) {
		return By.xpath("//table/tbody/tr[td[normalize-space()='" + username + "']]");
	}

	public WebDriverWait getWebDriverWait(WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(10));  // Default to 10 seconds
	}

	public void waitAndClick(By locator) {
		getWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();
	}

	public String getCurrentPageUrl () {
		return driver.getCurrentUrl();
	}

	public String getCurrentPageTitle () {
		return driver.getTitle();
	}

	/**
	 * Add user method
	 * @param userData
	 */
	public void addUser(List<String> userData) {      
		WebElement addUser = driver.findElement(addUserLink);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", addUser);

		driver.findElement(getCustomerOption(userData.get(6))).click();
		driver.findElement(roleDropdown).click();
		driver.findElement(getRoleDropdownOption(userData.get(7))).click();

		Map<By, String> userFields = Map.of(
				firstNameTextBox, userData.get(0),
				lastNameTextBox, userData.get(1),
				userNameTextBox, userData.get(2),
				passwordTextBox, userData.get(3),
				emailTextBox, userData.get(4),
				cellPhoneTextBox, userData.get(5)
				);
		userFields.forEach((locator, value) -> driver.findElement(locator).sendKeys(value));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(saveButton));
	}


	/**
	 * Check if a user is present
	 * @param username
	 * @return true/false
	 */
	public boolean isUserPresent(String username) {
		return driver.findElement(getUsername(username)).isDisplayed();
	}


	/**
	 * Check if a user is not present
	 * @param username
	 * @return true/false
	 */
	public boolean isUserNotPresent(String username) {
		List<WebElement> rows = driver.findElements(tableBody);

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				if (cell.getText().contains(username)) {
					return false;  // Text found in a cell
				}
			}
		}
		return true;
	}

	/**
	 * Delete user method
	 * @param username
	 */
	public void deleteUser(String username) {
		try {
			Stream.of(deleteButton(username), confirmDelete)
			.forEach(locator -> waitAndClick(locator));
			getWebDriverWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(getUsername(username)));
			System.out.println("User " + username + " has been deleted successfully.");                    	
		} catch (Exception e) {
			System.out.println("User " + username + " was not found.");
		}
	}
}
