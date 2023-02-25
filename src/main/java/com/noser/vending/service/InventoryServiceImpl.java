package com.noser.vending.service;

import com.noser.vending.datastore.Inventory;
import com.noser.vending.model.InventoryProduct;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the inventory service.
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    private final Inventory inventory;

    public InventoryServiceImpl(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryProduct addProduct(InventoryProduct inventoryProduct) {
        inventory.addProduct(inventoryProduct);
        return inventoryProduct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeProduct(int productId) {
        inventory.removeProduct(productId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAllProducts() {
        inventory.clearAllProducts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateProduct(InventoryProduct inventoryProduct) {
        inventory.updateProduct(inventoryProduct);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<InventoryProduct> getProducts() {
        return inventory.getProducts();
    }
}
