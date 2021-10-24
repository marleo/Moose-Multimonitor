package com.testenvironment.multimonitor;

import java.awt.*;

public class Config {

    /**
     * Logging
     */
    public static int USER_ID = 1;
    public static String LOG_PATH = "logs/";
    public static String MOUSE_LOG = "mouselog_";
    public static String TIMING_LOG = "timinglog_";
    public static String LOG_NAME = "moose_log_";
    public static String TESTTYPE = "NO_MOOSE";
    /**
     * Test
     */
    public static int NUM_TRIALS = 20;

    /**
     *  Testpanel
     */
    public static Color TESTBACKGROUND_COLOR = Color.LIGHT_GRAY;

    /**
     *  Startbutton
     */
    public static Color STARTFIELD_COLOR = new Color(8, 161, 54);
    public static Color STARTFIELD_PRESSED_COLOR = new Color(4, 90, 29);
    public static Color STARTFIELD_COLOR_TEXT = Color.WHITE;
    public static int STARTFIELD_FONT_SIZE = 12;
    public static int STARTFIELD_HEIGHT = 20;
    public static int STARTFIELD_WIDTH = 50;

    /**
     *  Text in upper left corner
     */
    public static int INFOTEXT_X = 20;
    public static int INFOTEXT_Y = 30;
    public static Color INFOTEXT_COLOR = Color.BLACK;
    public static int INFOTEXT_FONT_SIZE = 18;

    /**
     *  General Fontstyle
     */
    public static String FONT_STYLE = "Sans-Serif";

    /**
     *  Goal-Circle
     */
    public static Color GOALCIRCLE_COLOR = new Color(208, 33, 33);;
    public static Color GOALCIRCLE_PRESSED_COLOR = new Color(118, 18, 18);
    public static int GOALCIRCLE_RAD = 15;
}