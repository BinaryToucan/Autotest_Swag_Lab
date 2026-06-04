package ui.enums;

import ui.pages.InventoryPage;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public enum SortingOption {
    A_TO_Z(
            "az",
            InventoryPage::getProductNames,
            Comparator.naturalOrder()),
    Z_TO_A(
            "za",
            InventoryPage::getProductNames,
            Comparator.reverseOrder()),
    LOW_TO_HIGH(
            "lohi",
            InventoryPage::getProductPrices,
            Comparator.naturalOrder()),
    HIGH_TO_LOW(
            "hilo",
            InventoryPage::getProductPrices,
            Comparator.reverseOrder());

    private final String value;
    // Что сортируем
    private final Function<
                InventoryPage,
                List<? extends Comparable>
                > extractor;
    // Как сортируем
    private final Comparator comparator;

    SortingOption(
            String value,
            Function<
                    InventoryPage,
                    List<? extends Comparable>
                    > extractor,
            Comparator comparator) {
        this.value = value;
        this.extractor = extractor;
        this.comparator = comparator;
    }

    public String getText() {
        return value;
    }

    public List<? extends Comparable> extract(
            InventoryPage inventoryPage
    ) {
        return extractor.apply(inventoryPage);
    }

    public Comparator getComparator() { return comparator; }
}
