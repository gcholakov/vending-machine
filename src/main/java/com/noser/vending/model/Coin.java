package com.noser.vending.model;

import lombok.*;

import javax.validation.constraints.Pattern;

/**
 * Represents inserted coin.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Coin {

    /**
     * Value of the coin.
     */
    @Getter
    @Pattern(regexp = "^(10|20|50|100|200)$")
    private String value;

    @Override
    public String toString() {
        if (value == null)
            return "";

        if (value.length() <= 2) {
            return value + " st.";
        }

        return value.charAt(0) + " lv.";
    }
}
