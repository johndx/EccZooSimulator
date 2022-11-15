package com.test.zoo.service;

import com.test.zoo.domain.Animal;
import com.test.zoo.domain.Zoo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

/**
 * Singleton Service class, to model the application
 * of Business functionality and Business rules for the Zoo.
 */
@Service
public class ZooService {

    private final Logger log = LoggerFactory.getLogger(ZooService.class);
    static final String AGEING_ALGO = "AGEING_ALGO";
    static final String FEEDING_ALGO = "FEEDING_ALGO";
    private static final int FEED_RANGE_VALUE_MIN = 10;
    private static final int FEED_RANGE_VALUE_MAX = 25;
    private static final int AGEING_RANGE_VALUE_MAX = 20;
    private static final int MINUS_ONE = -1;


    /**
     * Map of Supplier</T> Functional Interfaces, categorised by role.
     */
    private Map<String, IntSupplier> algorithmLambdaMap = new HashMap<>();

    private Zoo zoo;

    public Zoo retrieveZoo() {
        return zoo;
    }

    public Map<String, IntSupplier> getAlgorithmLambdaMap() {
        return algorithmLambdaMap;
    }

    /**
     * Age all the Animals in the Zoo.
     */
    public void ageAnimals() {
        if(zooHasAnimals()) {
            apply(zoo.getAnimalsAsList(), algorithmLambdaMap.get(AGEING_ALGO));
        }
    }


    /**
     * Feed all the Animals in the Zoo.
     * Note that the  Feeding Algorithm will be executed once for
     * each type of Animal and then applied to each instance of that
     * type.
     * The new Functional Interface lambda will just return this
     * constant value.
     *
     */
    public void feedAnimals() {

        if(zooHasAnimals()) {
            zoo.getAnimals().values().stream().forEach(e -> {
                                        int rangedFeedAmount = algorithmLambdaMap.get(FEEDING_ALGO).getAsInt();
                                        // Define new FI lambda with constant value of executed feed algo.
                                        log.warn("Feed Value for Animal list is : " + rangedFeedAmount);
                                        IntSupplier feedAnimalsAlgo = () -> rangedFeedAmount;
                                                                apply(e,feedAnimalsAlgo);
                                    });
        }
    }

    @PostConstruct
    void initLambdaMap() {
        IntSupplier ageAnimalsAlgo = () -> (int) ((Math.random() * AGEING_RANGE_VALUE_MAX) * MINUS_ONE);
        IntSupplier feedAnimalsAlgo = () -> FEED_RANGE_VALUE_MIN + (int)(Math.random() * ((FEED_RANGE_VALUE_MAX - FEED_RANGE_VALUE_MIN) + 1));

        algorithmLambdaMap.put(AGEING_ALGO, ageAnimalsAlgo);
        algorithmLambdaMap.put(FEEDING_ALGO, feedAnimalsAlgo);
    }


    /**
     * Initialise the sample Zoo , from an externally generated
     * Dataset.
     * @param zoo
     */
    public void initData(final Zoo zoo){
        this.zoo = zoo;
    }

    /*
     * Apply the required logic defined in the Functional Interface
     * against the supplied List of animals.
     */
    private void apply(final List<Animal> animalList, final IntSupplier lifecycleAlgo) {

        log.warn("Updating the Animal data set a new Feed or Age request");
        animalList.stream().forEach(e -> e.updateHealth(lifecycleAlgo));
    }

    private boolean zooHasAnimals() {
        // Gate - Data integrity check
        if ( zoo == null || zoo.hasNoAnimals()) {
            log.error("There are no Animals to look after");
            return false;
        } else {
            return true;
        }
    }

}
