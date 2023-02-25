package com.noser.vending.datastore;

import com.noser.vending.model.InventoryProduct;
import com.noser.vending.model.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simulates inventory data storage. Could be replaced with H2 or similar.
 * It starts with some products preloaded.
 */
@Component
@Slf4j
public class Inventory {

    private final int MAX_PRODUCTS_OF_TYPE = 10;
    private int nextProductId = 0;

    /**
     * The list of products.
     */
    private final List<InventoryProduct> productsInventory = new ArrayList<>();

    /**
     * Adds a product, checking if products of the same type are not more than 10.
     *
     * @param inventoryProduct the product to add to the list
     * @return newly added product
     */
    public InventoryProduct addProduct(InventoryProduct inventoryProduct) {
        long count = getProductsCountByType(inventoryProduct.getProductType());

        if (count == MAX_PRODUCTS_OF_TYPE) {
            throw new IllegalArgumentException("Machine is full with products of type " + inventoryProduct.getProductType());
        }

        inventoryProduct.setProductId(generateNewProductId());
        productsInventory.add(inventoryProduct);
        return inventoryProduct;
    }

    /**
     * Removes a product if it exists, otherwise throws exception.
     *
     * @param productId identifier of the product
     */
    public void removeProduct(int productId) {
        Optional<InventoryProduct> optionalProduct = productsInventory.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst();

        if (optionalProduct.isPresent()) {
            productsInventory.remove(optionalProduct.get());
        } else {
            throw new IllegalArgumentException("ProductId not found: " + productId);
        }
    }

    /**
     * Replaces a product in the list.
     *
     * @param inventoryProduct new product
     */
    public void updateProduct(InventoryProduct inventoryProduct) {
        if (productsInventory.contains(inventoryProduct)) {
            productsInventory.set(productsInventory.indexOf(inventoryProduct), inventoryProduct);
        } else {
            throw new IllegalArgumentException("Product not found: " + inventoryProduct);
        }
        log.info(productsInventory.toString());
    }

    /**
     * Returns products list.
     *
     * @return the list
     */
    public List<InventoryProduct> getProducts() {
        return productsInventory;
    }

    /**
     * Removes all products - used for testing purposes.
     */
    public void clearAllProducts() {
        productsInventory.clear();
    }

    /**
     * Returns the number of products by type. Used for validation.
     *
     * @param productType the type to count products of
     * @return products number with type provided
     */
    private long getProductsCountByType(ProductType productType) {
        return productsInventory.stream()
                .filter(p -> p.getProductType().equals(productType))
                .count();
    }

    /**
     * Generates products identifier.
     *
     * @return new identifier
     */
    private int generateNewProductId() {
        return ++nextProductId;
    }

    /**
     * Helper method to start with some products in the machine.
     * For testing purposes only!
     */
    @PostConstruct
    private void fillProductsInMachine() {
        InventoryProduct inventoryProduct = new InventoryProduct("Chips", 235, ProductType.FOOD);
        inventoryProduct.setProductId(generateNewProductId());
        productsInventory.add(inventoryProduct);

        inventoryProduct = new InventoryProduct("Mars", 180, ProductType.FOOD);
        inventoryProduct.setProductId(generateNewProductId());
        productsInventory.add(inventoryProduct);

        inventoryProduct = new InventoryProduct("Coca-cola", 320, ProductType.DRINK);
        inventoryProduct.setProductId(generateNewProductId());
        productsInventory.add(inventoryProduct);

        inventoryProduct = new InventoryProduct("Fanta", 250, ProductType.DRINK);
        inventoryProduct.setProductId(generateNewProductId());
        productsInventory.add(inventoryProduct);
    }
}
