package com.testenvironment.multimonitor.experiment;

import com.testenvironment.multimonitor.Config;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class TrialBlocks {
    private static TrialBlocks instance = null;
    private final Map<Integer, ArrayList<Point>> position;
    private final ArrayList<Constellation> trials;
    private final ArrayList<ArrayList<Constellation>> blocks;
    private int numMonitors;
    private int trialNum;

    private TrialBlocks() {
        this.numMonitors = 0;
        this.position = new HashMap<>();
        this.trials = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.trialNum = 0;
    }

    public static TrialBlocks getTrialblocks() {
        if (instance == null) {
            instance = new TrialBlocks();
        }
        return instance;
    }

    /**
     * Creates Zones for given Monitor and puts them into position Map
     *
     * @param monitorWidth - monitor width in px
     * @param monitorHeight - monitor height in px
     * @param insetSize - Size of Window insets
     */
    public void addMonitor(int monitorWidth, int monitorHeight, int insetSize) {
        this.numMonitors++; //start with monitor 1

        int monitorRows = Config.MONITOR_ZONES[0];
        int monitorCols = Config.MONITOR_ZONES[1];

        int rectWidth = Config.STARTFIELD_WIDTH;
        int rectHeight = Config.STARTFIELD_HEIGHT;
        int padding = Config.PADDING;
        ArrayList<Point> xyCoords = new ArrayList<>();

        int leftEdge = padding + rectWidth / 2;
        int rightEdge = monitorWidth - rectWidth / 2 - padding;
        int topEdge = rectHeight + insetSize + padding;
        int bottomEdge = monitorHeight - rectHeight / 2 - insetSize - padding;

        int xCoord = leftEdge;
        int yCoord = topEdge;

        xyCoords.add(new Point(xCoord, yCoord));

        for (int i = 1; i < monitorRows; i++) {
            for (int j = 1; j < monitorCols; j++) {
                int xStep = (rightEdge - leftEdge) / (monitorCols - 1);
                xCoord += xStep;
                xyCoords.add(new Point(xCoord, yCoord));
            }
            int yStep = (bottomEdge - topEdge) / (monitorRows - 1);
            yCoord += yStep;
            xCoord = leftEdge;
            xyCoords.add(new Point(xCoord, yCoord));
        }

        position.put(numMonitors, xyCoords);
    }

    /**
     * Adds new Trial to the trial ArrayList
     *
     * @param monitorStart - startmonitor
     * @param monitorGoal - endmonitor
     * @param posStart - position in monitorzones (starting with 0 top left and going right)
     * @param posGoal - goal position in monitorzones
     * @param goalWidth - width of the goalcircle / -rectangle in px
     * @param goalHeight - height of the goalcircle / -rectangle in px
     */
    public void addTrial(int monitorStart, int monitorGoal, int posStart, int posGoal, int goalWidth, int goalHeight) {

        trials.add(new Constellation(
                monitorStart,
                monitorGoal,
                position.get(monitorStart).get(posStart),
                position.get(monitorGoal).get(posGoal),
                trialNum++,
                goalWidth,
                goalHeight
        ));
    }

    /**
     * Here you can add more Trials.
     * The trialorder for the following blocks gets generated below.
     */
    public void generateTrials() {
        addTrial(2, 1, 0, 0, 10, 10);
        addTrial(1, 2, 1, 9, 15, 15);
        addTrial(2, 1, 2, 4, 10, 10);
        addTrial(1, 2, 6, 8, 15, 15);
        addTrial(2, 1, 2, 7, 10, 10);
        addTrial(1, 2, 1, 3, 15, 15);

        blocks.add(trials);

        for (int i = 1; i < Config.BLOCKS; i++) {
            //Collections.shuffle(trials);
            ArrayList<Constellation> nextBlock = new ArrayList<>();
            ArrayList<ArrayList<Constellation>> seperateTrials = new ArrayList<>();

            // Get different startMonitors
            ArrayList<Integer> maxStart = new ArrayList<>();
            for (Constellation t : trials) {
                if (!maxStart.contains(t.getMonitorStart())) {
                    maxStart.add(t.getMonitorStart());
                }
            }

            //Seperate different startMonitors in seperate Lists
            for (int j = 1; j <= numMonitors; j++) {
                int finalX = j;
                ArrayList<Constellation> filteredTrials = trials.stream()
                        .filter(n -> n.getMonitorStart() == finalX)
                        .collect(Collectors.toCollection(ArrayList::new));
                Collections.shuffle(filteredTrials);
                seperateTrials.add(filteredTrials);
            }

            Constellation nextConst = seperateTrials.get(0).get(0);
            seperateTrials.get(0).remove(nextConst);

            while (nextConst != null) {
                nextBlock.add(nextConst);
                nextConst = getNextConstellation(seperateTrials, nextConst.getMonitorEnd());
            }

            for (ArrayList<Constellation> constellations : seperateTrials) {
                nextBlock.addAll(constellations);
            }

            /*
             * Debug: Prints Trialblocks with corresponding monitors & trialnumbers
             */
            System.out.println("Trialblock " + (i + 1) + ":");
            for (Constellation constellation : nextBlock) {
                System.out.println(constellation.getMonitorStart() + " - " + constellation.getMonitorEnd() + ": " + constellation.getTrialNum());
            }
            blocks.add(nextBlock);
        }
    }

    /**
     * Tries to find a Constellation, where Startmonitor == previous Endmonitor.
     * Otherwise returns null.
     *
     * @param constellations - Array containing constellations arrays splitted by startmonitor
     * @param endMonitor - Previous Endmonitor
     */
    public Constellation getNextConstellation(ArrayList<ArrayList<Constellation>> constellations, int endMonitor) {
        Constellation nextConst = null;

        for (ArrayList<Constellation> constellation : constellations) {
            for (Constellation c : constellation) {
                if (c.getMonitorStart() == endMonitor) {
                    nextConst = c;
                    constellation.remove(c);
                    break;
                }
            }
        }
        return nextConst;
    }

    public ArrayList<ArrayList<Constellation>> getBlocks() {
        return blocks;
    }

    /**
     * Add errortrial back to trials.
     *
     * @param trial - Constellation to push back
     */
    public void pushBackTrial(Constellation trial, int currentBlock) {
        trials.add(trial);
        blocks.get(currentBlock - 1).add(trial);
    }

    /**
     * remove duplicates (errors duplicate the trials)
     */
    public void resetTrialblock() {
        ArrayList<Constellation> removedErrors = new ArrayList<>();
        for(int i = 0; i < trialNum; i++) {
            removedErrors.add(trials.get(i));
        }
        trials.clear();
        trials.addAll(removedErrors);

        for(ArrayList<Constellation> constellations : blocks) {
            ArrayList<Constellation> remErrors = new ArrayList<>();

            for(int i = 0; i < trialNum; i++) {
                remErrors.add(constellations.get(i));
            }

            constellations.clear();
            constellations.addAll(remErrors);
        }
    }
}