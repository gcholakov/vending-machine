package com.noser.vending.controller;

import com.google.gson.Gson;
import com.noser.vending.model.Coin;
import com.noser.vending.model.InventoryProduct;
import com.noser.vending.model.Product;
import com.noser.vending.model.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VendingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testing valid coin insertion.
     *
     * @throws Exception on performing post
     */
    @Test
    void insertCoin_whenValid_thenSuccessful() throws Exception {
        Coin coin = new Coin("50");
        mockMvc.perform(post("/vending/insert")
                        .content(new Gson().toJson(coin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Testing invalid coin insertion.
     *
     * @throws Exception on performing post
     */
    @Test
    void insertCoin_whenInvalid_thenBadRequest() throws Exception {
        Coin coin = new Coin("30");
        MvcResult result = mockMvc.perform(post("/vending/insert")
                        .content(new Gson().toJson(coin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultValidation = result.getResponse().getContentAsString();
        assertTrue(resultValidation.contains("must match"));
    }

    /**
     * Tests returning of all coins.
     *
     * @throws Exception on performing post
     */
    @Test
    void returnCoins_whenSuccessful_thenReturned200() throws Exception {
        Coin coin = new Coin("10");
        mockMvc.perform(post("/vending/insert")
                        .content(new Gson().toJson(coin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        MvcResult result = mockMvc.perform(delete("/vending/reset"))
                .andExpect(status().isOk())
                .andReturn();
        String resultReset = result.getResponse().getContentAsString();
        assertTrue(resultReset.contains("10 st."));
    }

    /**
     * Tests buying product when the coins amount is enough.
     *
     * @throws Exception on performing post
     */
    @Test
    void buyProduct_whenAmountEnough_thenSuccessful() throws Exception {
        Coin coin = new Coin("200");
        mockMvc.perform(post("/vending/insert")
                        .content(new Gson().toJson(coin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        coin = new Coin("50");
        mockMvc.perform(post("/vending/insert")
                        .content(new Gson().toJson(coin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Product wantedProduct = new Product();
        wantedProduct.setProductType(ProductType.DRINK);
        wantedProduct.setName("fanta");
        MvcResult result = mockMvc.perform(post("/vending/buy")
                        .content(new Gson().toJson(wantedProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String resultProduct = result.getResponse().getContentAsString();
        InventoryProduct boughtProduct = new Gson().fromJson(resultProduct, InventoryProduct.class);
        assertEquals("Fanta", boughtProduct.getName());
    }

    /**
     * Tests buying product when the coins amount is not enough.
     *
     * @throws Exception on performing post
     */
    @Test
    void buyProduct_whenAmountNotEnough_thenBadRequest() throws Exception {
        Coin coin = new Coin("50");
        mockMvc.perform(post("/vending/insert")
                        .content(new Gson().toJson(coin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Product wantedProduct = new Product();
        wantedProduct.setProductType(ProductType.DRINK);
        wantedProduct.setName("fanta");
        MvcResult result = mockMvc.perform(post("/vending/buy")
                        .content(new Gson().toJson(wantedProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultMessage = result.getResponse().getContentAsString();
        assertTrue(resultMessage.contains("Product not found or too expensive!"));
    }
}