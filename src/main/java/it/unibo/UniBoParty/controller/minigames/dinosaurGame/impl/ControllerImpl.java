package it.unibo.UniBoParty.controller.minigames.dinosaurGame.impl;

import javax.swing.Timer;

import it.unibo.UniBoParty.controller.minigames.dinosaurGame.api.Controller;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Model;
import it.unibo.UniBoParty.view.minigames.dinosaurGame.api.View;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControllerImpl implements Controller {

    private final Model model;
    private final View view;
    private Timer timer;

    public ControllerImpl(Model model, View view) {
        this.model = model;
        this.view = view;

        setupTimer();
        setupKeyListener();
    }

    private void setupTimer() {
        timer = new Timer(12, e -> {
            model.update();
            view.repaint();
        });
        timer.start();
    }

    private void setupKeyListener() {
        view.getPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    model.jump();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    model.releaseJump();
                }
            }
        });

        view.getPanel().setFocusable(true);
        view.getPanel().requestFocusInWindow();
    }

    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();
    }
}
