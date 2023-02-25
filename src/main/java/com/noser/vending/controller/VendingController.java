package com.noser.vending.controller;

import com.noser.vending.model.Coin;
import com.noser.vending.model.InventoryProduct;
import com.noser.vending.model.Product;
import com.noser.vending.service.VendingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for vending endpoints.
 */
@RestController
@RequestMapping("/vending")
@Tag(name = "Vending", description = "Endpoints for vending functions.")
public class VendingController {

    private final VendingService vendingService;

    public VendingController(VendingService vendingService) {
        this.vendingService = vendingService;
    }

    /**
     * Endpoint for inserting a coin.
     *
     * @param coin to add, its productId will be generated automatically
     */
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(
            summary = "Inserts a coin.",
            description = "Inserts a coin in the machine.",
            tags = {"Vending"},
            responses = {
                    @ApiResponse(
                            description = "Accepted",
                            responseCode = "202",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Coin.class))
                    ),
                    @ApiResponse(description = "Invalid coin.", responseCode = "400", content = @Content)
            }
    )
    public void insertCoin(@RequestBody @Valid Coin coin) {
        vendingService.insertCoin(coin);
    }

    /**
     * Endpoint for returning all inserted coins.
     *
     * @return list of coins inserted so far
     */
    @Operation(summary = "Returns all coins.")
    @DeleteMapping("/reset")
    public String returnCoins() {
        return vendingService.returnCoins();
    }

    /**
     * Endpoint for buying product.
     *
     * @param product product to buy. Only the type and the name are taken into account currently
     * @return the product
     */
    @Operation(summary = "Buys a product.")
    @PostMapping("/buy")
    public InventoryProduct buyProduct(@RequestBody @Valid Product product) {
        return vendingService.buyProduct(product.getProductType(), product.getName());
    }
}
