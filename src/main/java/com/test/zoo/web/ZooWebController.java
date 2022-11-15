package com.test.zoo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.test.zoo.event.ElevatedTimeSimulator;
import com.test.zoo.service.ZooService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.format.DateTimeFormatter;


/**
 *
 */
@Controller
public class ZooWebController {

    private ZooService zooService;

    private ElevatedTimeSimulator elevatedTimeSimulator;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     * @param zooService
     * @param elevatedTimeSimulator
     */
    public ZooWebController(ZooService zooService, ElevatedTimeSimulator elevatedTimeSimulator) {
        this.zooService = zooService;
        this.elevatedTimeSimulator = elevatedTimeSimulator;
    }


    /**
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/console/zoo")
    public String zooConsole(Model model) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        model.addAttribute("Ticks", elevatedTimeSimulator.getTicks());
        model.addAttribute("SimElapsedTime", elevatedTimeSimulator.getSimElapsedTime().format(formatter));
        model.addAttribute("StartTime", elevatedTimeSimulator.getStartTime().format(formatter));
        model.addAttribute("CurrentTime", elevatedTimeSimulator.getCurrentTime().format(formatter));

        model.addAttribute("Zoo", ObjectToJsonConverter(zooService.retrieveZoo()));
        return "console";
    }


    protected String ObjectToJsonConverter(final Object pojoObject) throws Exception {

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(pojoObject);
        return requestJson;
    }

}