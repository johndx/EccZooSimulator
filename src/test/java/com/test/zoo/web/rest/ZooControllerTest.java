package com.test.zoo.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring integration tests with Mock MVC Tier.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ZooControllerTest extends ControllerBaseTests {

    private final String BASE_URL = "/api";


    @PostConstruct
    public void initTest() {

    }

    /**
     * Verify that a basic POST to '/zoo/animals' will correctly retrieve
     * the Zoo and all constituent animals.
     * Using Mock MVC to emulate a REST call.
     * @throws Exception
     */
    @Test
    @Transactional
    public void mockValidGetShouldReturnZooAndPass() throws Exception {

        String url = BASE_URL + "/zoo/animals";

        MvcResult result  = getMockMvc().perform(get(url))
                                .andExpect(status().isOk())
                                .andReturn();
        String responseContent = result.getResponse().getContentAsString();

        // Verify the Content of the returned Payload
        assertThat(responseContent != null).isTrue();
        assertThat(responseContent.length() > 0).isTrue();
        assertThat(responseContent.contains("Monkey")).isTrue();
        assertThat(responseContent.contains("Giraffe")).isTrue();
        assertThat(responseContent.contains("Elephant")).isTrue();

    }


    /**
     * Verify that
     * Using Mock MVC to emulate a REST call.
     * @throws Exception
     */
    @Test
    @Transactional
    public void mockValidPostToFeedAnimalsShouldPass() throws Exception {

        String expectedResponseContent = "All animals have been fed";
        String expectedResponseUrl = BASE_URL + "/zoo/animals/";

        String url = BASE_URL + "/zoo/animals/feed";

        MvcResult result  =  getMockMvc().perform(post(url).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent.equalsIgnoreCase(expectedResponseContent)).isTrue();

        List<String> locations = result.getResponse().getHeaders("Location");
        url = locations.get(0);
        assertThat(url.equals(expectedResponseUrl)).isTrue();

        getMockMvc().perform(get(url))
                .andExpect(status().isOk());
    }





}