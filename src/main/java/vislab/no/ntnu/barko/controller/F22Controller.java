package vislab.no.ntnu.barko.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import vislab.no.ntnu.barko.driver.F22Projector;
import vislab.no.ntnu.DeviceManager;
import vislab.no.ntnu.providers.Projector;


@Controller
@RequestMapping("/BarkoF22")
public class F22Controller extends DeviceManager {

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

    @RequestMapping("/testImage")
    public ResponseEntity<String> testImage(@RequestParam(value = "id") int id, @RequestParam(value = "image") int image) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.testImageOn(image) + "", HttpStatus.OK);
    }

    @RequestMapping("/setContrast")
    public ResponseEntity<String> setContrast(@RequestParam(value = "id") int id, @RequestParam(value = "value") int value) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.setContrast(value) + "", HttpStatus.OK);
    }

    @RequestMapping("/setBrightness")
    public ResponseEntity<String> setBrightness(@RequestParam(value = "id") int id, @RequestParam(value = "value") int value) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.setBrightness(value) + "", HttpStatus.OK);
    }

    protected F22Projector getProjector(int id) {
        Projector projector = (Projector) getActiveDevices().get(id);
        return (projector instanceof F22Projector) ? (F22Projector) projector : null;
    }

    @Override
    public String getDevicePage(int id) {
        if (getProjector(id) != null) {
            return "forward:/f22/f22.html";
        }
        return super.getDevicePage(id);
    }
}