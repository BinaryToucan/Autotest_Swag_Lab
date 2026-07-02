package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.enums.SortingOption;
import ui.utils.Endpoints;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    // Карточка продукта
    private final By inventoryItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By addToCartButton = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private final By removeFromCartButton = By.cssSelector("[data-test='remove-sauce-labs-backpack']");

    // Корзина
    private final By cartButton = By.className("shopping_cart_link");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");

    // Фильтр
    private final By sortingDropdown = By.className("product_sort_container");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPagePath() {
        return Endpoints.INVENTORY_PAGE;
    }

    public void addFirstProductToCart() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

        wait.until(driver ->
                !driver.findElements(shoppingCartBadge).isEmpty()
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));
    }

    public String getCartBadgeText() {
        return driver.findElement(shoppingCartBadge).getText();
    }

    public boolean isRemoveButtonDisplayed() {
        return driver.findElement(removeFromCartButton).isDisplayed();
    }

    public void openCart() {
        driver.findElement(cartButton).click();
    }

    public void selectSortingOption(SortingOption sortingOption) {
        Select select = new Select(driver.findElement(sortingDropdown));
        select.selectByValue(sortingOption.getText());
    }

    public List<String> getProductNames() {
        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return driver.findElements(productPrices)
                .stream()
                .map(element -> element.getText()
                        .replace("$", "")
                )
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}
