package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultsPage {
    private WebDriver driver;

    private By resultsTableRows = By.cssSelector("table.table tbody tr");

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public static class FlightInfo {
        public String airline;
        public String flight;
        public String depart;
        public String arrive;
        public double price;
        public WebElement chooseButton;
    }

    public List<FlightInfo> getAllFlights() {
        List<WebElement> rows = driver.findElements(resultsTableRows);
        List<FlightInfo> flights = new ArrayList<FlightInfo>();

        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() < 6) {
                continue;
            }
            FlightInfo fi = new FlightInfo();
            fi.chooseButton = cols.get(0).findElement(By.tagName("input")); // choose button
            fi.airline = cols.get(1).getText();
            fi.flight = cols.get(2).getText();
            fi.depart = cols.get(3).getText();
            fi.arrive = cols.get(4).getText();
            String priceText = cols.get(5).getText(); // e.g. "$472.56"
            priceText = priceText.replace("$", "").trim();
            try {
                fi.price = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                fi.price = Double.MAX_VALUE;
            }
            flights.add(fi);
        }
        return flights;
    }

    public List<FlightInfo> getCheapestTwoFlights() {
        List<FlightInfo> all = getAllFlights();
        if (all.size() == 0) {
            return new ArrayList<FlightInfo>();
        }
        // sort by price
        Collections.sort(all, new Comparator<FlightInfo>() {
            @Override
            public int compare(FlightInfo o1, FlightInfo o2) {
                return Double.compare(o1.price, o2.price);
            }
        });

        List<FlightInfo> result = new ArrayList<FlightInfo>();
        // pick first
        result.add(all.get(0));
        if (all.size() > 1) {
            result.add(all.get(1));
        }
        return result;
    }

    public void chooseFlight(FlightInfo fi) {
        // click choose button (which navigates to purchase page)
        fi.chooseButton.click();
    }
}

