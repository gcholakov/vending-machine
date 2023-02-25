package com.noser.vending.service;

import com.noser.vending.datastore.Inventory;
import com.noser.vending.datastore.Vending;
import com.noser.vending.model.Coin;
import com.noser.vending.model.InventoryProduct;
import com.noser.vending.model.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the vending service.
 */
@Service
@Slf4j
public class VendingServiceImpl implements VendingService {

    private final Vending vending;
    private final Inventory inventory;

    public VendingServiceImpl(Vending vending, Inventory inventory) {
        this.vending = vending;
        this.inventory = inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCoin(Coin coin) {
        vending.insertCoin(coin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String returnCoins() {
        String coinsReturned = vending.getCoinsList().toString();
        log.info("Returning coins: {}", vending.getCoinsList());
        vending.getCoinsList().clear();
        return coinsReturned;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryProduct buyProduct(ProductType productType, String productName) {
        int totalAmount = vending.getTotalAmount();

        if (totalAmount == 0) {
            throw new IllegalArgumentException("Insert coins before buying!");
        }

        Optional<InventoryProduct> optionalProduct = inventory.getProducts().stream()
                .filter(p -> p.getProductType().equals(productType) &&
                        p.getName().equalsIgnoreCase(productName) &&
                        p.getPrice() <= totalAmount)
                .findFirst();

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found or too expensive!");
        }

        InventoryProduct inventoryProduct = optionalProduct.get();
        inventory.removeProduct(inventoryProduct.getProductId());
        vending.getCoinsList().clear();
        log.info("Product sold, tip: {} st.", totalAmount - inventoryProduct.getPrice());
        return inventoryProduct;
    }
}
