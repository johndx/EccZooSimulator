package com.test.zoo.web.rest;

import com.test.zoo.domain.Zoo;
import com.test.zoo.service.ZooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/api")
public class ZooController {

    ZooService zooService;

    private final Logger log = LoggerFactory.getLogger(ZooController.class);

    /**
     * Autowire the Zoo Service
     * @param zooService
     */
    public ZooController(ZooService zooService) {
        this.zooService = zooService;
    }


    /**
     * {@code GET  /api/zoo/animals} : get all the Animals in the Zoo
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of the account, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zoo/animals")
    public ResponseEntity<Zoo> getZooAnimals() {

        log.info("REST request to get all the Animals in the Zoo");

        Zoo zoo = zooService.retrieveZoo();
        HttpHeaders responseHeaders = new HttpHeaders();
        if (zoo.hasNoAnimals() ) {
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(zoo, responseHeaders, HttpStatus.OK);
        }
    }


    /**
     * {@code POST  /api/zoo/animals/feed} : Feed all the Animals in the Zoo.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with success message and URI of the GET request to retrieve all Animals.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zoo/animals/feed")
    public ResponseEntity<String> feedAnimals() throws URISyntaxException {

        log.info("REST request to Feed the animals");
        zooService.feedAnimals();

        return ResponseEntity
                .created(new URI("/api/zoo/animals/"))
                .body("All animals have been fed");
    }

}
