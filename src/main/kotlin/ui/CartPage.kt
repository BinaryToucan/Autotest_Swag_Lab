package ui

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import ui.pages.BasePage
import ui.utils.Endpoints
import java.time.Duration

class CartPage(driver: WebDriver): BasePage(driver){

    private val cartItems = By.className("cart_item")
    private val checkoutButton = By.id("checkout")
    private val continueShoppingButton = By.id("continue-shopping")
    private val removeButton = By.id("remove-sauce-labs-backpack")

    private fun cartItem(itemName: String?): By {
        return By.xpath(
            "//div[@class='cart_item' and .//div[text()='" + itemName + "']]"
        )
    }

    fun getItemsCount(): Int =
        driver.findElements(cartItems).size

    fun clickCheckout() =
        driver.findElement(checkoutButton).click()

    fun continueShopping() =
        driver.findElement(continueShoppingButton).click()

    fun removeItem(itemName: String?) {
        val item = cartItem(itemName)

        driver.findElement(item)
            .findElement(removeButton)
            .click()

        WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.numberOfElementsToBe(item, 0))
    }

    fun shouldContainItem(itemName: String): Boolean {
        return driver.findElements(
            By.xpath("//div[text()='$itemName']")
        ).isNotEmpty()
    }

    override fun getPagePath(): String = Endpoints.CART_PAGE
}