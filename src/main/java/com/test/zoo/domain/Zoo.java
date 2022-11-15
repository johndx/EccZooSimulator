package com.test.zoo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Domain class, realised as a Pojo that models the 'Zoo' and
 * acts as a container for all the Animals.
 * Provides segregation of the types of animals via the
 * subdivision into a Map, but with the 'getAnimalsAsList()'
 * to allow iteration over all the types of animals.

 */
public class Zoo implements Serializable {

    private static final long serialVersionUID = 1L;

    /*  Instance Data   */
    private Map<String, List<Animal>> animals = new HashMap<>();


    public Map<String, List<Animal>> getAnimals() {
        return animals;
    }

    @JsonIgnore
    public List<Animal> getAnimalsAsList() {
        return animals.values().stream().flatMap( li -> li.stream()).collect(Collectors.toList());
    }

    public void setAnimals(Map<String,List<Animal>> animals) {
        this.animals = animals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zoo zoo = (Zoo) o;
        return Objects.equals(animals, zoo.animals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animals);
    }


    @Override
    public String toString() {
        return "Zoo{" +
                "animals=" + animals +
                '}';
    }

    /**
     * Ensure there are Animals in the zoo.
     * @return true or false
     */
    public boolean hasNoAnimals() {
        return animals.isEmpty();
    }
}
