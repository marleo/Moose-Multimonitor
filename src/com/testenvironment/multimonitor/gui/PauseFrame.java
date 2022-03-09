package com.testenvironment.multimonitor.gui;

import com.testenvironment.multimonitor.Config;
import com.testenvironment.multimonitor.experiment.Experiment;
import com.testenvironment.multimonitor.experiment.TrialBlocks;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseFrame extends JPanel implements MouseInputListener {
    Experiment experiment;
    TrialBlocks trialBlocks;


    public PauseFrame(Experiment experiment) {
        this.experiment = experiment;
        this.trialBlocks = TrialBlocks.getTrialblocks();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Config.PAUSE_BACKGROUNDCOLOR);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        Font font = new Font(Config.FONT_STYLE, Font.PLAIN, Config.PAUSETEXT_FONT_SIZE);
        FontMetrics fontMetrics = g2d.getFontMetrics(font);

        int x = (this.getWidth() - fontMetrics.stringWidth(Config.PAUSETEXT)) / 2;
        int y = (this.getHeight() - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        g2d.setColor(Config.PAUSETEXT_COLOR);
        g2d.setFont(font);
        g2d.drawString(Config.PAUSETEXT, x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
        trialBlocks.setPauseTrial(false);
        trialBlocks.setResumeTrial(true);
        experiment.drawFrames();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

