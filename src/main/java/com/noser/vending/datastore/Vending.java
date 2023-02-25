package com.noser.vending.datastore;

import com.noser.vending.model.Coin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains vending data.
 */
@Component
@Slf4j
public class Vending {

    /**
     * The list of coins inserted.
     */
    @Getter
    private final List<Coin> coinsList = new ArrayList<>();

    /**
     * Inserts a coin to the list.
     * @param coin inserted coin
     */
    public void insertCoin(Coin coin) {
        coinsList.add(coin);
    }

    /**
     * Calculates the total amount of inserted coins.
     * @return total amount in coins
     */
    public int getTotalAmount() {
        return coinsList.stream().mapToInt(c -> Integer.parseInt(c.getValue())).sum();
    }
}
