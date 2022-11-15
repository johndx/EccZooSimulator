package com.test.zoo.domain;

import java.math.BigDecimal;

public class Monkey  extends Animal {

    private static final BigDecimal MIN_HEALTH = new BigDecimal(30);

    /**
     * Constructor
     */
    public Monkey() {
        super(MIN_HEALTH);
    }


}
