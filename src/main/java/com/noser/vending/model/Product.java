package com.noser.vending.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Product with only base attributes.
 */
@Getter
@Setter
public class Product {

    /**
     * The name of the product.
     */
    @NotBlank
    protected String name;

    /**
     * Type - DRINK|FOOD.
     */
    protected ProductType productType;
}
