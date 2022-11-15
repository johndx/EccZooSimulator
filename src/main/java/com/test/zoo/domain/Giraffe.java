package com.test.zoo.domain;

import java.math.BigDecimal;

public class Giraffe  extends Animal {

    private static final BigDecimal MIN_HEALTH = new BigDecimal(50);
    /**
     * Constructor
     */
    public Giraffe() {
        super(MIN_HEALTH);
    }

}