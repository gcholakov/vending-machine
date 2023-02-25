package com.noser.vending.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * Represents a product in the machine.
 */
@Getter
@ToString
public class InventoryProduct extends Product {

    public InventoryProduct(String name, int price, ProductType productType) {
        this.name = name;
        this.price = price;
        this.productType = productType;
    }

    /**
     * Product identifier, set automatically when product is added to inventory.
     */
    @Setter
    @JsonProperty(index = 1)
    private int productId;

    /**
     * Price of the product in stotinki. Must be at least 1 - just for validation demo.
     */
    @Min(1)
    private final int price;

    /**
     * Overridden to compare products by productId only.
     *
     * @param obj product to compare to
     * @return true if the products have the same identifiers
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InventoryProduct) {
            InventoryProduct inventoryProduct = (InventoryProduct) obj;
            return inventoryProduct.getProductId() == this.getProductId();
        }

        return false;
    }
}
