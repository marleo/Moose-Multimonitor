package com.testenvironment.multimonitor.logging;

import com.testenvironment.multimonitor.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MouseLogger {

    private static MouseLogger instance = null;
    private final int participant;
    private PrintWriter logFile;
    private String fileName;

    private int trialNumber;
    private int blockNumber;
    private int monitorNr;
    private int monitorWidth;
    private int monitorHeight;
    private int windowWidth;
    private int windowHeight;
    private int mouseX;
    private int mouseY;
    private int mousePressed;
    private int mouseReleased;
    private int mouseEntered;
    private int mouseExited;
    private int mouseMoved;
    private int trialNumberInSet;

    private MouseLogger() {
        this.fileName = Config.LOG_PATH + Config.EVENTS_LOG + "_" + Config.TESTTYPE + "_" + Config.USER_ID + ".txt";
        createLogFile();
        writeToLog(createHeaderString());
        this.participant = Config.USER_ID;
        this.trialNumber = 0;
        this.blockNumber = 0;
        this.monitorNr = 0;
        this.monitorWidth = 0;
        this.monitorHeight = 0;
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.mouseX = 0;
        this.mouseY = 0;
        this.mousePressed = 0;
        this.mouseReleased = 0;
        this.mouseEntered = 0;
        this.mouseExited = 0;
        this.mouseMoved = 0;
        this.trialNumberInSet = 0;
    }

    public static MouseLogger getMouseLogger() {
        if (instance == null) {
            instance = new MouseLogger();
        }
        return instance;
    }

    public void createLogFile() {
        try {
            while (new File(this.fileName).exists()) {
                Config.USER_ID += 100;
                this.fileName = Config.LOG_PATH + Config.TRIALS_LOG + "_" + Config.TESTTYPE + "_" + Config.USER_ID + ".txt";
            }
            logFile = new PrintWriter(new FileWriter(this.fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createHeaderString() {
        return "participant" + ";" +
                "trialNumber" + ";" +
                "blockNumber" + ";" +
                "trialNumberInSet" + ";" +
                "MonitorNr" + ";" +
                "MonitorWidth" + ";" +
                "MonitorHeight" + ";" +
                "WindowWidth" + ";" +
                "WindowHeight" + ";" +
                "MouseX" + ";" +
                "MouseY" + ";" +
                "MousePressed" + ";" +
                "MouseReleased" + ";" +
                "MouseEntered" + ";" +
                "MouseExited" + ";" +
                "MouseMoved" + ";" +
                "timeStamp" + ";"
                ;
    }

    public void generateLogString() {
        String logString = participant + ";" +
                trialNumber + ";" +
                blockNumber + ";" +
                trialNumberInSet + ";" +
                monitorNr + ";" +
                monitorWidth + ";" +
                monitorHeight + ";" +
                windowWidth + ";" +
                windowHeight + ";" +
                mouseX + ";" +
                mouseY + ";" +
                mousePressed + ";" +
                mouseReleased + ";" +
                mouseEntered + ";" +
                mouseExited + ";" +
                mouseMoved + ";" +
                System.currentTimeMillis() + ";";

        writeToLog(logString);

        this.mousePressed = 0;
        this.mouseReleased = 0;
        this.mouseEntered = 0;
        this.mouseExited = 0;
        this.mouseMoved = 0;
    }

    public void writeToLog(String log) {
        log = log + "\n";
        Path path = Paths.get(this.fileName);

        try {
            Files.writeString(path, log, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endLog() {
        System.out.println("Logging ended");
        logFile.close();
    }

    public void setTrialNumberInSet(int trialNumberInSet) {
        this.trialNumberInSet = trialNumberInSet;
    }

    public int getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(int trialNumber) {
        this.trialNumber = trialNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getMonitorNr() {
        return monitorNr;
    }

    public void setMonitorNr(int monitorNr) {
        this.monitorNr = monitorNr;
    }

    public int getMonitorWidth() {
        return monitorWidth;
    }

    public void setMonitorWidth(int monitorWidth) {
        this.monitorWidth = monitorWidth;
    }

    public int getMonitorHeight() {
        return monitorHeight;
    }

    public void setMonitorHeight(int monitorHeight) {
        this.monitorHeight = monitorHeight;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(int mousePressed) {
        this.mousePressed = mousePressed;
    }

    public int getMouseReleased() {
        return mouseReleased;
    }

    public void setMouseReleased(int mouseReleased) {
        this.mouseReleased = mouseReleased;
    }

    public int getMouseEntered() {
        return mouseEntered;
    }

    public void setMouseEntered(int mouseEntered) {
        this.mouseEntered = mouseEntered;
    }

    public int getMouseExited() {
        return mouseExited;
    }

    public void setMouseExited(int mouseExited) {
        this.mouseExited = mouseExited;
    }


    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getMouseMoved() {
        return mouseMoved;
    }

    public void setMouseMoved(int mouseMoved) {
        this.mouseMoved = mouseMoved;
    }

}
