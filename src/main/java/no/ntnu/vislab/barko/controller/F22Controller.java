package no.ntnu.vislab.barko.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;

import no.ntnu.vislab.barko.driver.F22Projector;
import no.ntnu.vislab.vislabcontroller.dummybase.DummyBase;
import no.ntnu.vislab.vislabcontroller.dummybase.DummyDevice;
import no.ntnu.vislab.vislabcontroller.webcontroller.MainController;

@Controller
@RequestMapping("/BarkoF22")
public class F22Controller extends MainController {

    @RequestMapping("/mute")
    public ResponseEntity<Integer> muteImage(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.mute(), HttpStatus.OK);
    }

    @RequestMapping("/unMute")
    public ResponseEntity<Integer> unMuteImage(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.unMute(), HttpStatus.OK);
    }

    @RequestMapping("/powerState")
    public ResponseEntity<String> powerState(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getPowerState() + "", HttpStatus.OK);
    }

    @RequestMapping("/lampStatus")
    public ResponseEntity<String> getLampStatus(@RequestParam(value = "id") int id, @RequestParam(value = "lampNum") int lampNum) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.fetchLampStatus(lampNum) + "", HttpStatus.OK);
    }
    @RequestMapping("/lampRuntime")
    public ResponseEntity<String> getLampRuntime(@RequestParam(value = "id") int id, @RequestParam(value = "lampNum") int lampNum) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.fetchLampRuntime(lampNum) + "", HttpStatus.OK);
    }
    
    @RequestMapping("/getContrast")
    public ResponseEntity<Integer> getContrast(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.fetchContrast(), HttpStatus.OK);
    }
    @RequestMapping("/getBrightness")
    public ResponseEntity<Integer> getBrightness(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.fetchBrightness(), HttpStatus.OK);
    }
    @RequestMapping("/getThermal")
    public ResponseEntity<Integer> getThermal(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.fetchTemperature(), HttpStatus.OK);
    }
    @RequestMapping("/getProjectorRuntime")
    public ResponseEntity<Integer> getProjectorRuntime(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.fetchTotalRuntime(), HttpStatus.OK);
    }

    @Override
    protected F22Projector getProjector(int id){
        return (F22Projector) super.getProjector(id);
    }
}