package ui.tests

import annotations.UiTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ui.CartPage
import ui.pages.InventoryPage

@UiTest
class CartTests: BaseAuthTest() {

    @Test
    fun itemShouldBeDisplayedInCart() {

        val inventoryPage = InventoryPage(driver)

        inventoryPage.addFirstProductToCart()

        val cartPage = CartPage(driver)
        cartPage.open()
        Assertions.assertTrue(cartPage.shouldContainItem("Sauce Labs Backpack"))
        Assertions.assertEquals(1, cartPage.getItemsCount())
    }

    @Test
    fun userCanRemoveProductFromCart() {

        val inventoryPage = InventoryPage(driver)
        inventoryPage.addFirstProductToCart()

        val cartPage = CartPage(driver)
        cartPage.open()
        cartPage.removeItem("Sauce Labs Backpack")

        Assertions.assertFalse(cartPage
            .shouldContainItem("Sauce Labs Backpack"))
        Assertions.assertEquals(0, cartPage.getItemsCount())
    }
}