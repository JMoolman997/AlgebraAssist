package com.JMoolman997.calculator.ui;

import javax.swing.*;
import java.awt.*;

public class MultiPanelFrame extends JFrame {
    private DynamicPanel panel1, panel2, panel3, panel4, panel5, panel6;

    public MultiPanelFrame() {
        setTitle("Resizable Panels Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize dynamic panels
        panel1 = new DynamicPanel();
        panel2 = new DynamicPanel();
        panel3 = new DynamicPanel();
        panel4 = new DynamicPanel();
        panel5 = new DynamicPanel();
        panel6 = new DynamicPanel();

        // Create JSplitPanes
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel3, panel4);
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel5, panel6);

        JSplitPane splitPane4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1, splitPane2);
        JSplitPane splitPane5 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane4, splitPane3);

        // Set continuous layout for better user experience
        splitPane1.setContinuousLayout(true);
        splitPane2.setContinuousLayout(true);
        splitPane3.setContinuousLayout(true);
        splitPane4.setContinuousLayout(true);
        splitPane5.setContinuousLayout(true);

        // Add the top-level split pane to the frame
        add(splitPane5);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MultiPanelFrame::new);
    }
}
