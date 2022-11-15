package com.test.zoo.domain;


import java.math.BigDecimal;

public class Elephant  extends Animal {

    private static final BigDecimal MIN_HEALTH = new BigDecimal(70);

    /**
     * Constructor
     */
    public Elephant() {
        super(MIN_HEALTH);
    }

    @Override
    protected void updateHealthStatus() {
        if (getHealth().compareTo(this.getMinimumHealth()) < 0 ) {
            if (getStatus() == HealthStatus.HEALTHY) {
                setStatus(HealthStatus.CANNOT_WALK);
            } else {
                setStatus(HealthStatus.DEAD);
            }
        } else {
            setStatus(HealthStatus.HEALTHY);
        }
    }
}
