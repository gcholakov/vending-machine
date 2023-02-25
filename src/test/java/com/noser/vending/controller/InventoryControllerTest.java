package com.noser.vending.controller;

import com.google.gson.Gson;
import com.noser.vending.model.InventoryProduct;
import com.noser.vending.model.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests validation for no more than 10 products of the same type.
     *
     * @throws Exception from preform() method
     */
    @Test
    void addProduct_whenMoreThan10SameType_thenBadRequest() throws Exception {
        mockMvc.perform(delete("/inventory/clear")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < 10; i++) {
            InventoryProduct inventoryProduct = new InventoryProduct("Chips", 250, ProductType.FOOD);
            inventoryProduct.setProductId(i);
            mockMvc.perform(post("/inventory/add")
                            .content(new Gson().toJson(inventoryProduct))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        InventoryProduct inventoryProduct = new InventoryProduct("Chips", 250, ProductType.FOOD);
        inventoryProduct.setProductId(10);
        mockMvc.perform(post("/inventory/add")
                        .content(new Gson().toJson(inventoryProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests adding product with empty name and invalid price.
     *
     * @throws Exception from preform() method
     */
    @Test
    void addProduct_whenInvalidProduct_thenBadRequest() throws Exception {
        InventoryProduct inventoryProduct = new InventoryProduct(" ", 0, ProductType.FOOD);
        MvcResult result = mockMvc.perform(post("/inventory/add")
                        .content(new Gson().toJson(inventoryProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultProduct = result.getResponse().getContentAsString();
        assertTrue(resultProduct.contains("must not be blank"));
    }

    /**
     * Tests product removal when it exists.
     *
     * @throws Exception from preform() method
     */
    @Test
    void removeProduct_whenSuccess_thenReturned200() throws Exception {
        InventoryProduct inventoryProduct = new InventoryProduct("Chips", 250, ProductType.FOOD);
        mockMvc.perform(post("/inventory/add")
                        .content(new Gson().toJson(inventoryProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/inventory/remove/{productId}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Tests product removal when it doesn't exists.
     *
     * @throws Exception from preform() method
     */
    @Test
    void removeProduct_whenNotFound_thenReturned400() throws Exception {
        mockMvc.perform(delete("/inventory/remove/{productId}", "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests successful update of product.
     *
     * @throws Exception from preform() method
     */
    @Test
    void updateProduct_whenSuccess_thenReturned200() throws Exception {
        InventoryProduct inventoryProduct = new InventoryProduct("Fanta Lemon", 150, ProductType.DRINK);
        MvcResult result = mockMvc.perform(post("/inventory/add")
                        .content(new Gson().toJson(inventoryProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultProduct = result.getResponse().getContentAsString();
        InventoryProduct newInventoryProduct = new Gson().fromJson(resultProduct, InventoryProduct.class);
        assertEquals("Fanta Lemon", newInventoryProduct.getName());

        InventoryProduct inventoryProductUpdated = new InventoryProduct("New Fanta Lemon", newInventoryProduct.getPrice(),
                newInventoryProduct.getProductType());
        inventoryProductUpdated.setProductId(newInventoryProduct.getProductId());
        mockMvc.perform(put("/inventory/update")
                        .content(new Gson().toJson(inventoryProductUpdated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Tests products retrieval.
     *
     * @throws Exception from preform() method
     */
    @Test
    void getProducts_whenProductAdded_notEmptyResponse() throws Exception {
        InventoryProduct inventoryProduct = new InventoryProduct( "Fanta", 150, ProductType.DRINK);
        MvcResult result = mockMvc.perform(post("/inventory/add")
                        .content(new Gson().toJson(inventoryProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String resultProduct = result.getResponse().getContentAsString();
        InventoryProduct newInventoryProduct = new Gson().fromJson(resultProduct, InventoryProduct.class);
        assertEquals(inventoryProduct.getProductType(), newInventoryProduct.getProductType());
        assertEquals(inventoryProduct.getPrice(), newInventoryProduct.getPrice());
        assertEquals(inventoryProduct.getName(), newInventoryProduct.getName());

        result = mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andReturn();
        String products = result.getResponse().getContentAsString();
        List<InventoryProduct> productsList = new Gson().fromJson(products, ArrayList.class);
        assertFalse(productsList.isEmpty());
    }
}