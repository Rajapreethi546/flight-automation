package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;

    // BlazeDemo has select elements with ids fromPort and toPort
    private By fromSelect = By.name("fromPort");
    private By toSelect = By.name("toPort");
    private By findFlightsButton = By.cssSelector("input[type='submit']");

    private String baseUrl = "http://blazedemo.com/";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToHomePage() {
        driver.get(baseUrl);
    }

    public void selectFrom(String fromCity) {
        Select sel = new Select(driver.findElement(fromSelect));
        sel.selectByVisibleText(fromCity);
    }

    public void selectTo(String toCity) {
        Select sel = new Select(driver.findElement(toSelect));
        sel.selectByVisibleText(toCity);
    }

    public void clickFindFlights() {
        driver.findElement(findFlightsButton).click();
    }
}
