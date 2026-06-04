package ui.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.enums.SortingOption;
import ui.enums.TestUser;
import ui.pages.InventoryPage;
import ui.pages.LoginPage;
import ui.utils.Endpoints;

import java.util.ArrayList;
import java.util.List;

public class InventoryTest extends BaseTest {

    // Перед каждым тестом авторизуемся под существующим юзером
    @BeforeEach
    public void login() {
        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.open();
        loginPage.login(TestUser.STANDARD_USER);
    }

    @Test
    public void userCanAddProductToCart() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addFirstProductToCart();
        Assertions.assertEquals( "1", inventoryPage.getCartBadgeText() );
    }

    @Test
    public void removeButtonShouldAppear() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addFirstProductToCart();
        Assertions.assertTrue( inventoryPage.isRemoveButtonDisplayed() );
    }

    @Test
    public void userCanOpenCartPage() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.openCart();
        Assertions.assertTrue(
                driver.getCurrentUrl()
                        .contains(Endpoints.CART_PAGE)
        );
    }

    @ParameterizedTest
    @EnumSource(SortingOption.class)
    public void productsShouldBeSorted(
            SortingOption sortingOption
    ) {

        InventoryPage inventoryPage =
                new InventoryPage(driver);

        inventoryPage.selectSortingOption(sortingOption);

        validateSorting(
                sortingOption.extract(inventoryPage),
                sortingOption
        );
    }

    private <T extends Comparable<T>> void validateSorting(
            List<T> actualList,
            SortingOption sortingOption
    ) {
        List<T> expectedList = new ArrayList<>(actualList);
        expectedList.sort( sortingOption.getComparator() );
        Assertions.assertEquals( expectedList, actualList);
    }
}
