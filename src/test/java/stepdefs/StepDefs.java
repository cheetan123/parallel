package stepdefs;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class StepDefs {

	Scenario scenario;
	WebDriver driver;

	@Before
	public void SetUp(Scenario s) {
		scenario = s;
	}

	@Given("I open the Browser and Navigate to the URL {string}")
	public void i_open_the_Browser_and_Navigate_to_the_URL(String string) throws Exception {

		DesiredCapabilities cap = new DesiredCapabilities();
		// cap.setBrowserName(BrowserType.CHROME);
		cap.setBrowserName(BrowserType.CHROME);

		// System.setProperty("webdriver.chrome.driver",
		// "/Users/chetanlashkari/Documents/work/chromedriver");

		driver = new RemoteWebDriver(new URL("http://192.168.29.190:4444/wd/hub"), cap);
		driver.get(string);
		driver.manage().window().maximize();
		scenario.write("Opened the Browser with URL: " + string);
		Thread.sleep(30000);

	}

	@Given("I hit the GET request for reqres")
	public void getRequest() {

		Response res = given().when().log().all().get("https://reqres.in/api/users");

		res.prettyPrint();

		Assert.assertEquals(200, res.getStatusCode());
	}

	@After
	public void teatDown() {
		if (driver != null) {
			driver.close();
		}
	}

}
