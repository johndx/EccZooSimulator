package com.test.zoo.utility;

import com.test.zoo.domain.*;
import com.test.zoo.service.ZooService;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * Data Loader Utility class to initialize the model with required domain data
 * and sufficient sample data to allow some representative queries to be made.
 */
@Component
public class DataLoader {

    private static final int MAX_ANIMAL_INSTANCES = 5;
    private ZooService zooService;

    private static final List<Class> AnimalClasses = Arrays.asList(Monkey.class, Giraffe.class, Elephant.class);


    /**
     * Wire up the Spring bean dependencies
     * @param zooService
     */
    public DataLoader(ZooService zooService) {
        this.zooService = zooService;
    }

    /**
     * Load required base data.
     */
    public   void loadBaseData() {

        Zoo sampleZoo = new Zoo();
        Map<String,List<Animal>> sampleAnimals = new HashMap<>();
        List<Animal> animalList;

        //Giraffe
        animalList = new ArrayList<>();
        for(int i=0;i<MAX_ANIMAL_INSTANCES;i++) {
            animalList.add(new Giraffe());
        }
        sampleAnimals.put(Giraffe.class.getSimpleName(), animalList);

        //Monkey
        animalList = new ArrayList<>();
        for(int i=0;i<MAX_ANIMAL_INSTANCES;i++) {
            animalList.add(new Monkey());
        }
        sampleAnimals.put(Monkey.class.getSimpleName(), animalList);


        //Elephant
        animalList = new ArrayList<>();
        for(int i=0;i<MAX_ANIMAL_INSTANCES;i++) {
            animalList.add(new Elephant());
        }
        sampleAnimals.put(Elephant.class.getSimpleName(), animalList);


        sampleZoo.setAnimals(sampleAnimals);
        zooService.initData(sampleZoo);

    }

}
