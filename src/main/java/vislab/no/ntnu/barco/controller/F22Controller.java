package vislab.no.ntnu.barco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import vislab.no.ntnu.barco.driver.F22Projector;
import vislab.no.ntnu.DeviceManager;
import vislab.no.ntnu.providers.Device;


@Controller
@RequestMapping("/api/BarcoF22")
public class F22Controller extends DeviceManager {

    @RequestMapping("/getMute")
    public ResponseEntity<Integer> getMute(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getMuteSetting(), HttpStatus.OK);
    }
    @RequestMapping("/getPower")
    public ResponseEntity<Integer> getPower(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getPowerSetting(), HttpStatus.OK);
    }

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

    @RequestMapping("/getTestImage")
    public ResponseEntity<Integer> getTestImage(@RequestParam(value = "id") int id) throws IOException {
        F22Projector projector = getProjector(id);
        return new ResponseEntity<>(projector.getTestImage(), HttpStatus.OK);
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
        Device device = getActiveDevices().get(id);
        return (device instanceof F22Projector) ? (F22Projector) device : null;
    }

    @Override
    public String getDevicePage(int id) {
        if (getProjector(id) != null) {
            return "forward:/f22/f22.html";
        }
        return super.getDevicePage(id);
    }
}
