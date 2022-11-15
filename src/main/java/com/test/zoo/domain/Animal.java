package com.test.zoo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.IntSupplier;

/**
 * This class models the common attributes of a
 * 'generic' Animal and is the base class for the
 * specific animals in this zoo.
 * It cannot be instantiated as it is abstract.
 */
public abstract class Animal implements Serializable {


    /*            Static data           */
    private static final BigDecimal FULL_HEALTH = new BigDecimal(100);

    private static final long serialVersionUID = 1L;
    private static final BigDecimal ONE_HUNDRED  = new BigDecimal(100);

    private final Logger log = LoggerFactory.getLogger(Animal.class);

    /*        Animal instance data      */
    private  BigDecimal health;
    private  HealthStatus status;

    private BigDecimal minimumHealth;

    /**
     * An Animal will always be created with 100% health and
     * a 'Healthy' status.
     */
    protected Animal() {
        this(BigDecimal.ONE);
    }

    protected Animal(final BigDecimal minimumHealth) {
        this.minimumHealth = minimumHealth;
        this.health = FULL_HEALTH;
        this.status = HealthStatus.HEALTHY;
    }

    public BigDecimal getHealth() {
        return health;
    }

    public HealthStatus getStatus() {
        return status;
    }

    protected void setStatus(HealthStatus status) {
        this.status = status;
    }

    public BigDecimal getMinimumHealth() {
        return minimumHealth;
    }

    private void setHealth(BigDecimal health) {
        this.health = health;
    }

    /*
     * Calculates the percentage of the current health that will be applied
     * as an adjustment to it, by either adding ( feeding) or
     * subtracting ( ageing), the percentage from the current health.
     * The 'lifecycleAlgo' can provide either a -ve or +ve value and
     * the BigDecimal.add() method will appropriately add or subtract this.
     */
    public void updateHealth(IntSupplier lifecycleAlgo) {

        if (status != HealthStatus.DEAD) {
            log.warn("\n Random Value : " + BigDecimal.valueOf(lifecycleAlgo.getAsInt())
                    + " current health : " + getHealth()
                    + " percentage : " + getHealth().multiply(BigDecimal.valueOf(lifecycleAlgo.getAsInt())).divide(ONE_HUNDRED));
            log.warn("Class :" + this.getClass().getSimpleName());

            // Calculate the percentage of the current health to apply as an adjustment.
            BigDecimal pctReduction = getHealth().multiply(BigDecimal.valueOf(lifecycleAlgo.getAsInt())).divide(ONE_HUNDRED);
            BigDecimal revisedHealth = getHealth().add(pctReduction);

            // Boundary Check the new value, to ensure remain within limits of a Percentage ( 0 -> 100 )
            BigDecimal boundedHealth = revisedHealth.compareTo(BigDecimal.ZERO) > 0 ? revisedHealth : BigDecimal.ZERO;
            boundedHealth = boundedHealth.compareTo(ONE_HUNDRED) > 0 ? ONE_HUNDRED : boundedHealth;
            setHealth(boundedHealth);
            updateHealthStatus();
        }
    }


    protected void updateHealthStatus() {
        if (getHealth().compareTo(this.getMinimumHealth()) < 0 ) {
            setStatus(HealthStatus.DEAD);
        }
    }



}
