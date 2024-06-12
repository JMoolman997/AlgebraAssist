package com.JMoolman997.calculator.ui;

import javax.swing.*;
import java.awt.*;


public class MultiPanelFrame extends JFrame {
    private PanelModeSetup panelModeSetup;
    private PanelGraphingDisplay panelGraphingDisplay;
    private PanelEnvironmentDisplay panelEnvironmentDisplay;
    private PanelModeGui panelModeGui;
    private PanelTextUi panelTextUi;
    private PanelModeInfo panelModeInfo;

    public MultiPanelFrame() {
        setTitle("Resizable Panels Example");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize dynamic panels
        panelModeSetup = new PanelModeSetup();
        panelGraphingDisplay = new PanelGraphingDisplay();
        panelEnvironmentDisplay = new PanelEnvironmentDisplay();
        panelModeGui = new PanelModeGui();
        panelTextUi = new PanelTextUi();
        panelModeInfo = new PanelModeInfo();

        // Create JSplitPanes
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelModeSetup, panelGraphingDisplay);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, panelEnvironmentDisplay);
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelModeGui, panelTextUi);
        JSplitPane splitPane4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane3, panelModeInfo);
        JSplitPane splitPane5 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane2, splitPane4);

        // Set continuous layout for better user experience
        splitPane1.setContinuousLayout(true);
        splitPane2.setContinuousLayout(true);
        splitPane3.setContinuousLayout(true);
        splitPane4.setContinuousLayout(true);
        splitPane5.setContinuousLayout(true);

        // Set initial divider locations (optional)
        splitPane1.setDividerLocation(0.5);
        splitPane2.setDividerLocation(0.33);
        splitPane3.setDividerLocation(0.5);
        splitPane4.setDividerLocation(0.67);
        splitPane5.setDividerLocation(0.5);

        // Add the top-level split pane to the frame
        add(splitPane5);

        // Add a button to switch modes
        JButton switchModeButton = new JButton("Switch Panel 1 to Mode 2");
        switchModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelModeSetup.switchMode("Mode2");
            }
        });

        add(switchModeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MultiPanelFrame::new);
    }
}
