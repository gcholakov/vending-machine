package com.noser.vending.controller;

import com.noser.vending.model.InventoryProduct;
import com.noser.vending.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for inventory endpoints.
 */
@RestController
@RequestMapping("/inventory")
@Slf4j
@Tag(name = "Inventory", description = "Endpoints for managing products in inventory.")
public class InventoryController {
    /**
     * Reference to inventory service.
     */
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Endpoint for adding a product.
     *
     * @param inventoryProduct product to add, its productId will be generated automatically
     * @return newly added product
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds a product.")
    public InventoryProduct addProduct(@RequestBody @Valid InventoryProduct inventoryProduct) {
        inventoryService.addProduct(inventoryProduct);
        return inventoryProduct;
    }

    /**
     * Endpoint for removing a product.
     *
     * @param productId identifier of the product to remove
     */
    @DeleteMapping("/remove/{productId}")
    @Operation(summary = "Deletes a product by productId.")
    public void removeProduct(@PathVariable int productId) {
        inventoryService.removeProduct(productId);
    }

    /**
     * Endpoint for updating a product.
     *
     * @param inventoryProduct for update
     */
    @PutMapping("/update")
    @Operation(summary = "Updates a product.")
    public void updateProduct(@RequestBody @Valid InventoryProduct inventoryProduct) {
        inventoryService.updateProduct(inventoryProduct);
    }

    /**
     * Facility endpoint to retrieve all products.
     *
     * @return a list of products
     */
    @GetMapping
    @Operation(summary = "Returns all products.")
    public List<InventoryProduct> getProducts() {
        return inventoryService.getProducts();
    }

    /**
     * Facility endpoint for removing all products.
     */
    @DeleteMapping("/clear")
    @Operation(summary = "Deletes all products.")
    public void clearAllProducts() {
        inventoryService.clearAllProducts();
    }
}
