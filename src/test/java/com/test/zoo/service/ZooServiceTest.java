package com.test.zoo.service;

import com.test.zoo.domain.Animal;
import com.test.zoo.domain.Zoo;
import com.test.zoo.utility.DataLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.IntSupplier;
import static com.test.zoo.service.ZooService.AGEING_ALGO;
import static com.test.zoo.service.ZooService.FEEDING_ALGO;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Junit Test class for the service tier
 */
class ZooServiceTest {


    private static final Set<String> KEY_SET = new HashSet<>(Arrays.asList("Monkey", "Giraffe", "Elephant"));
    private static final int MAX_ITERATIONS = 5;
    private static final int MAX_LAMBDA_ITERATIONS = 20;

    private static final int INT_ZERO = 0;
    private static final BigDecimal FULL_HEALTH = new BigDecimal(100);


    /* Service clas to exercise */
    ZooService zooService = new ZooService();


    /**
     * Initialise the lambda definitions and load a sample
     * initialised data set, at the start of each Test.
     */
    @BeforeEach
    void setUp() {
        zooService.initLambdaMap();
        DataLoader dataLoader = new DataLoader(this.zooService);
        dataLoader.loadBaseData();
    }

    @AfterEach
    void tearDown() {
        this.zooService.initData(null);
    }

    /**
     * Age the Animals and check for the Appropriate behaviour.
     */
    @Test
    void VerifyAnimalsAgeCorrectly() {

        /* Ensure the Animals are thoroughly Aged */
        for( int i=0;i<MAX_ITERATIONS; i++) {
            zooService.ageAnimals();
        }
        Map<String, List<Animal>> animalList = coreIntegrityTest();
        for(String animalKey : KEY_SET) {
            List<Animal> animals = animalList.get(animalKey);
            for( Animal agedAnimal: animals) {
                assertThat(agedAnimal.getHealth().compareTo(FULL_HEALTH) < 0).isTrue();
            }
        }
    }


    @Test
    void VerifyAnimalsFedCorrectly() {

        /* Ensure the Animals are thoroughly Fed */
        for( int i=0;i<MAX_ITERATIONS; i++) {
            zooService.feedAnimals();
        }
        Map<String, List<Animal>> animalList = coreIntegrityTest();
        for(String animalKey : KEY_SET) {
            List<Animal> animals = animalList.get(animalKey);
            for( Animal agedAnimal: animals) {
                assertThat(agedAnimal.getHealth().compareTo(FULL_HEALTH) == 0).isTrue();
            }
        }
    }

    @Test
    void VerifyLambdaFunctionsWithinRange() {

       Map<String, IntSupplier> algos = zooService.getAlgorithmLambdaMap();

       // Verify the Ageing Algorithm
       verifyAlgoRange(algos.get(AGEING_ALGO), INT_ZERO, 20);

       // Verify the Ageing Algorithm
       verifyAlgoRange(algos.get(FEEDING_ALGO), 10, 25);

    }

    private void verifyAlgoRange(IntSupplier intSupplier, int rangeMin, int rangeMax) {

        for(int i=0;i<MAX_LAMBDA_ITERATIONS;i++) {
            int value = Math.abs(intSupplier.getAsInt());
            assertThat(value >= rangeMin).isTrue();
            //System.out.println("value" + value);
            assertThat(value <= rangeMax).isTrue();
        }

    }

    private Map<String, List<Animal>> coreIntegrityTest() {
        Zoo agedZoo = zooService.retrieveZoo();

        Map<String, List<Animal>> agedAnimals = agedZoo.getAnimals();
        Set<String> animalKeys = agedAnimals.keySet();
        assertThat(animalKeys.containsAll(KEY_SET)).isTrue();

        for(String animalKey : KEY_SET) {
            List<Animal> animalList = agedAnimals.get(animalKey);
            assertThat(animalList != null).isTrue();
            assertThat(animalList.stream().count() == 5).isTrue();
        }
        return agedAnimals;
    }
}