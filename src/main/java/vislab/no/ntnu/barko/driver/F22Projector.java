/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vislab.no.ntnu.barko.driver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vislab.no.ntnu.barko.commands.F22Command;
import vislab.no.ntnu.barko.commands.Brightness;
import vislab.no.ntnu.barko.commands.Contrast;
import vislab.no.ntnu.barko.commands.LampRuntime;
import vislab.no.ntnu.barko.commands.LampStatus;
import vislab.no.ntnu.barko.commands.LampTimeRemaining;
import vislab.no.ntnu.barko.commands.Mute;
import vislab.no.ntnu.barko.commands.Power;
import vislab.no.ntnu.barko.commands.PowerState;
import vislab.no.ntnu.barko.commands.TestImage;
import vislab.no.ntnu.barko.commands.ThermalStatus;
import vislab.no.ntnu.barko.commands.UnitTotalTime;
import vislab.no.ntnu.barko.BarkoF22Exception;
import vislab.no.ntnu.annotations.ProjectorSPI;
import vislab.no.ntnu.providers.Command;
import vislab.no.ntnu.providers.Projector;

/**
 * @author Kristoffer
 */
@ProjectorSPI
public class F22Projector implements F22Interface, Projector {
    private static final String MODEL = "F22";
    private static final String MAKE = "Barko";
    private InetAddress hostAddress;
    private int portNumber = 0;
    private CommunicationDriver cd;
    private int powerState = -1;
    private int powerSetting = 0;
    private int muteSetting = 0;
    private int brightness = 0;
    private int contrast = 0;
    private int runtime = 0;
    private int lamp1Runtime = 0;
    private int lamp2Runtime = 0;
    private int lamp1TimeRemaining = 0;
    private int lamp2TimeRemaining = 0;
    private int lamp1Status = 0;
    private int lamp2Status = 0;
    private int thermal = 0;
    private int testImage = 0;
    private Thread driver;
    private Socket socket;

    public F22Projector() {
    }

    @Override
    public String getDeviceName() {
        return getMake() + " " + getModel();
    }

    @Override
    public String getHostAddress() {
        return hostAddress.toString();
    }

    @Override
    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public boolean setIpAddress(String ipAddress) {
        try {
            hostAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            return false;
        }
        return true;
    }

    @Override
    public void setPort(int port) {
        portNumber = port;
    }

    @Override
    public boolean initialize() {
        try{
            cd = setUpDriver();
        } catch (IOException e){
            return false;
        }
        return cd != null;
    }


    /**
     * Sets up the communcation driver to be ready for use and returns it.
     * @return A ready to use CommunicationDriver.
     * @throws IOException When the connection cant be made.
     */
    private CommunicationDriver setUpDriver() throws IOException {
        CommunicationDriver communicationDriver = null;
        try {
            socket = new Socket(hostAddress, portNumber);
            communicationDriver = new CommunicationDriver(socket, new LampStatus(1), new PowerState());
            communicationDriver.setOnCommandReady(this::handleCommand);
            communicationDriver.setOnIssueCallback(this::handleError);
            driver = new Thread(communicationDriver);
            driver.start();
        } catch (BarkoF22Exception e) {
            return null;
        }
        return communicationDriver;
    }

    private synchronized void handleCommand(Command command) {
        processCommand(command);
        notifyAll();
    }

    /**
     * Handles errors that are returned up through the error callback.
     */
    private void handleError() {
        powerState = -1;
    }

    /**
     * Queues up a command and waits for the response. This method blocks.
     *
     * @param command the command to queue.
     */
    private synchronized void sendAndWait(F22Command command) {
        boolean error = false;
        while((cd == null || !cd.queueCommand(command)) && !error){
            try {
                cd = setUpDriver();
            } catch (IOException e) {
                error = true;
                powerState = -1;
                try{
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        while (command.getResponse() == null && !error) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logger.getLogger(F22Projector.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    /**
     * Powers on the barko f22 projector.
     *
     * @return returns 1 if everything went like it should.
     */
    @Override
    public int powerOn() {
        try {
            Power power = new Power(Power.ON);
            sendAndWait(power);
            return power.getPowerSetting();
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Powers off the barko f22 projector.
     *
     * @return returns 0 if everything went like it should.
     */
    @Override
    public int powerOff() {
        try {
            Power power = new Power(Power.OFF);
            sendAndWait(power);
            return power.getPowerSetting();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    /**
     * Returns The manufacturer of the device.
     * @return The manufacturer of the device.
     */
    @Override
    public String getMake() {
        return MAKE;
    }

    /**
     * Returns the device model.
     * @return the device model.
     */
    @Override
    public String getModel() {
        return MODEL;
    }

    /**
     * Mutes the image on the barko f22 projector.
     * @return returns 1 if everything went like it should.
     */
    @Override
    public int mute() {
        try {
            Mute mute = new Mute(Mute.ON);
            sendAndWait(mute);
            return mute.getMuteSetting();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    /**
     * UnMutes the image on the barko f22 projector.
     * @return returns 0 if everything went like it should.
     */
    @Override
    public int unMute() {
        try {
            Mute mute = new Mute(Mute.OFF);
            sendAndWait(mute);
            return mute.getMuteSetting();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    /**
     * Gets the current brightness setting on the projector
     * @return the current brightness setting on the projector
     */
    @Override
    public int fetchBrightness() {
        Brightness brightness = new Brightness();
        sendAndWait(brightness);
        return brightness.getBrightness();
    }

    /**
     * Sets the brightness setting on the projector and returns it.
     * @param value the brightness value to set.
     * @return the brightness setting on the projector
     */
    @Override
    public int setBrightness(int value) {
        try {
            Brightness brightness = new Brightness(value, true);
            sendAndWait(brightness);
            return brightness.getBrightness();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    @Override
    public int fetchContrast() {
        Contrast contrast = new Contrast();
        sendAndWait(contrast);
        return contrast.getContrast();
    }

    @Override
    public int setContrast(int value) {
        try {
            Contrast contrast = new Contrast(value, true);
            sendAndWait(contrast);
            return contrast.getContrast();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    public int fetchPowerState() {
        PowerState powerState = new PowerState();
        sendAndWait(powerState);
        return powerState.getPowerState();
    }

    @Override
    public int fetchLampRuntime(int lampNum) {
        try {
            LampRuntime lampRuntime = new LampRuntime(lampNum);
            sendAndWait(lampRuntime);
            return lampRuntime.getLampRuntime();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    @Override
    public int getLampRemaining(int lampNum) {
        try {
            LampTimeRemaining lampTimeRemaining = new LampTimeRemaining(lampNum);
            sendAndWait(lampTimeRemaining);
            return lampTimeRemaining.getLampTimeRemaining();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    @Override
    public int fetchTotalRuntime() {
        UnitTotalTime unitTotalTime = new UnitTotalTime();
        sendAndWait(unitTotalTime);
        return unitTotalTime.getTotalRuntime();
    }

    @Override
    public int getLampStatus(){
        return fetchLampStatus(1);
    }
    @Override
    public int fetchLampStatus(int lampNum) {
        try {
            LampStatus lampStatus = new LampStatus(lampNum);
            sendAndWait(lampStatus);
            return lampStatus.getLampStatus();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    @Override
    public int fetchTemperature() {
        ThermalStatus thermalStatus = new ThermalStatus();
        sendAndWait(thermalStatus);
        return thermalStatus.getThermal();
    }

    @Override
    public int testImageOn(int testImageNum) {
        try {
            TestImage testImage = new TestImage(testImageNum);
            sendAndWait(testImage);
            return testImage.getTestImage();
        } catch (BarkoF22Exception e) {
            return -1;
        }
    }

    @Override
    public int testImageOff() {
        TestImage testImage = new TestImage();
        sendAndWait(testImage);
        return testImage.getTestImage();
    }
    public synchronized boolean processCommand(Command command) {
        if (!(command instanceof F22Command)) {
            return false;
        }
        try {
            F22Command f22Command = (F22Command) command;
            if (f22Command.getCmd().split(" ")[0].equals(new Contrast().getCmd())) {
                contrast = ((Contrast) f22Command).getContrast();
                return true;
            } else if (f22Command.getCmd().split(" ")[0].equals(new Brightness().getCmd())) {
                brightness = ((Brightness) f22Command).getBrightness();
                return true;
            } else if (f22Command.getCmd().equals(new LampRuntime(1).getCmd())) {
                lamp1Runtime = ((LampRuntime) f22Command).getLampRuntime();
                return true;
            } else if (f22Command.getCmd().equals(new LampRuntime(2).getCmd())) {
                lamp2Runtime = ((LampRuntime) f22Command).getLampRuntime();
                return true;
            } else if (f22Command.getCmd().equals(new LampStatus(1).getCmd())) {
                lamp1Status = ((LampStatus) f22Command).getLampStatus();
                return true;
            } else if (f22Command.getCmd().equals(new LampStatus(2).getCmd())) {
                lamp2Status = ((LampStatus) f22Command).getLampStatus();
                return true;
            } else if (f22Command.getCmd().equals(new LampTimeRemaining(1).getCmd())) {
                lamp1TimeRemaining = ((LampTimeRemaining) f22Command).getLampTimeRemaining();
                return true;
            } else if (f22Command.getCmd().equals(new LampTimeRemaining(2).getCmd())) {
                lamp2TimeRemaining = ((LampTimeRemaining) f22Command).getLampTimeRemaining();
                return true;
            } else if (f22Command.getCmd().equals(new Mute().getCmd())) {
                muteSetting = ((Mute) f22Command).getMuteSetting();
                return true;
            } else if (f22Command.getCmd().equals(new Power().getCmd())) {
                powerSetting = ((Power) f22Command).getPowerSetting();
                return true;
            } else if (f22Command.getCmd().equals(new PowerState().getCmd())) {
                powerState = ((PowerState) f22Command).getPowerState();
                return true;
            } else if (f22Command.getCmd().equals(new TestImage(1).getCmd())) {
                testImage = ((TestImage) f22Command).getTestImage();
                return true;
            } else if (f22Command.getCmd().equals(new ThermalStatus().getCmd())) {
                thermal = ((ThermalStatus) f22Command).getThermal();
                return true;
            } else if (f22Command.getCmd().equals(new UnitTotalTime().getCmd())) {
                runtime = ((UnitTotalTime) f22Command).getTotalRuntime();
                return true;
            }
        } catch (BarkoF22Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int getPowerState(){
        return powerState;
    }

    public int getPowerSetting() {
        return powerSetting;
    }

    public int getMuteSetting() {
        return muteSetting;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getContrast() {
        return contrast;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getLamp1Runtime() {
        return lamp1Runtime;
    }

    public int getLamp2Runtime() {
        return lamp2Runtime;
    }

    public int getLamp1TimeRemaining() {
        return lamp1TimeRemaining;
    }

    public int getLamp2TimeRemaining() {
        return lamp2TimeRemaining;
    }

    public int getLamp1Status() {
        return lamp1Status;
    }

    public int getLamp2Status() {
        return lamp2Status;
    }

    public int getThermal() {
        return thermal;
    }

    public int getTestImage() {
        return testImage;
    }
}
