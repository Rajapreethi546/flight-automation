package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PurchasePage {
    private WebDriver driver;

    private By title = By.tagName("h2"); // Purchase page has an h2 with "Purchase"
    private By price = By.xpath("//p[contains(text(),'Price:')]");

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitleText() {
        try {
            return driver.findElement(title).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isAtPurchasePage() {
        String t = getTitleText();
        return t != null && t.toLowerCase().contains("purchase");
    }
}
