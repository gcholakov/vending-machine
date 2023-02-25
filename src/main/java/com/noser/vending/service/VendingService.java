package com.noser.vending.service;

import com.noser.vending.model.Coin;
import com.noser.vending.model.InventoryProduct;
import com.noser.vending.model.ProductType;

/**
 * Service for vending functions.
 */
public interface VendingService {

    /**
     * For inserting a coin.
     *
     * @param coin inserted coin
     */
    void insertCoin(Coin coin);

    /**
     * Returns all coins.
     */
    String returnCoins();

    /**
     * Buys a product with currently inserted coins, no change returned (thanks for the tip).
     * @param productType type of product
     * @param productName name of product
     * @return the product bought
     */
    InventoryProduct buyProduct(ProductType productType, String productName);
}
