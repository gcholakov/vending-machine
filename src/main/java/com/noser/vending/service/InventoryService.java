package com.noser.vending.service;

import com.noser.vending.model.InventoryProduct;

import java.util.List;

/**
 * Service for inventory functions.
 */
public interface InventoryService {

    /**
     * For adding a product.
     *
     * @param inventoryProduct new product
     * @return new product with generated identifier
     */
    InventoryProduct addProduct(InventoryProduct inventoryProduct);

    /**
     * To remove a product by productId.
     *
     * @param productId identifier of the product to remove
     */
    void removeProduct(int productId);

    /**
     * Removes all products from inventory (used for testing purposes).
     */
    void clearAllProducts();

    /**
     * For updating a product. Uses productId to identify the product in inventory.
     *
     * @param inventoryProduct the product that will replace existing one in the inventory
     */
    void updateProduct(InventoryProduct inventoryProduct);

    /**
     * Returns a list of all products (used for testing purposes).
     *
     * @return list of all products in inventory
     */
    List<InventoryProduct> getProducts();
}
