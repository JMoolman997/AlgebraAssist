package com.JMoolman997.calculator.ui;

import javax.swing.*;
import java.awt.*;

public abstract class DynamicPanel extends JPanel {
    private CardLayout cardLayout;

    public DynamicPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        initializeModes();
    }

    protected abstract void initializeModes();

    protected void addMode(String name, JComponent component) {
        add(component, name);
    }

    public void switchMode(String mode) {
        cardLayout.show(this, mode);
    }
}
