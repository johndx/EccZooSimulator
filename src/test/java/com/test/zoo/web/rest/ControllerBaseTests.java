package com.test.zoo.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


/**
 * Abstract base class for holding shared functionality
 * across common tests classes.
 */
public abstract class ControllerBaseTests {

    private final String API_BASE_URL = "/api";

    @Autowired
    private MockMvc mockMvc;

    /* Define at object level as expensive to construct */
    private ObjectMapper mapper = new ObjectMapper();


    public MockMvc getMockMvc() {
        return mockMvc;
    }

    /*
     * Jackson JSON Mapping
     */
    protected String ObjectToJsonConverter(final Object pojoObject) throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(pojoObject);
        return requestJson;
    }

    /*
     * Jackson Object Mapping - convert a source Json object representation
     * into a target object, returned as a base Java object.
     */
    protected  Object JsonToObjectConverter(final String sourceJson, final Class targetClass) throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Object targetObject = mapper.readValue(sourceJson, targetClass);
        return targetObject;
    }



}
