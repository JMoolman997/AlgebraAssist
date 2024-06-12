package com.JMoolman997.calculator.ui;

import javax.swing.*;
import java.awt.*;

public class DynamicPanel extends JPanel {
    private CardLayout cardLayout;

    public DynamicPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Add preconfigured components for different modes
        add(createComponentForMode1(), "Mode1");
        add(createComponentForMode2(), "Mode2");
        add(createComponentForMode3(), "Mode3");
    }

    private JComponent createComponentForMode1() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Mode 1"));
        return panel;
    }

    private JComponent createComponentForMode2() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Mode 2"));
        return panel;
    }

    private JComponent createComponentForMode3() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Mode 3"));
        return panel;
    }

    public void switchMode(String mode) {
        cardLayout.show(this, mode);
    }
}
