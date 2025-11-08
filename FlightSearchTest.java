package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PurchasePage;
import pages.ResultsPage;
import pages.ResultsPage.FlightInfo;
import pages.utils.BaseTest;

import java.util.List;

public class FlightSearchTest extends BaseTest {

    @Test
    public void findCheapestFlightsAndOpenNewTab() throws InterruptedException {
        WebDriver driver = this.driver;

        HomePage home = new HomePage(driver);
        home.goToHomePage();

        // select source and destination (example: "Philadelphia" to "Boston")
        home.selectFrom("Paris");       // choose values available on blazedemo; adjust if needed
        home.selectTo("Buenos Aires"); // choose values available on blazedemo; adjust if needed

        // click search
        home.clickFindFlights();

        ResultsPage results = new ResultsPage(driver);

        // wait briefly for page to load (simple)
        Thread.sleep(1500);

        List<FlightInfo> cheapestTwo = results.getCheapestTwoFlights();

        // Print results to console
        if (cheapestTwo.size() == 0) {
            System.out.println("No flights found.");
            Assert.fail("No flights found on results page.");
        }

        System.out.println("Cheapest and second-cheapest flights:");

        for (int i = 0; i < cheapestTwo.size(); i++) {
            FlightInfo f = cheapestTwo.get(i);
            System.out.println("Rank " + (i + 1) + ": Airline=" + f.airline
                    + " | Flight=" + f.flight
                    + " | Depart=" + f.depart
                    + " | Arrive=" + f.arrive
                    + " | Price=$" + f.price);
        }

        // Additional scenario: click choose on the cheapest and verify purchase page
        FlightInfo cheapest = cheapestTwo.get(0);
        results.chooseFlight(cheapest);

        PurchasePage purchase = new PurchasePage(driver);
        // small wait for navigation
        Thread.sleep(1000);
        Assert.assertTrue(purchase.isAtPurchasePage(), "Should be on Purchase page after choosing a flight.");

        System.out.println("Navigated to Purchase page successfully for chosen flight.");

        // Now open a new tab in the same session and navigate to Google
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.google.com");

        System.out.println("Opened new tab and navigated to Google. Current title: " + driver.getTitle());

        // Optional: switch back to original tab (index 0) - demonstrate switching
        // Selenium doesn't provide direct index access; use window handles:
        java.util.List<String> handles = new java.util.ArrayList<String>(driver.getWindowHandles());
        // switch back to the first handle (original)
        if (handles.size() > 0) {
            driver.switchTo().window(handles.get(0));
            System.out.println("Switched back to original tab. Title: " + driver.getTitle());
        }
    }
}
