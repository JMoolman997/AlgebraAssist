package com.JMoolman997.calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlgebraAssistGUI extends JFrame {

    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton addButton, subtractButton, multiplyButton, divideButton, equalsButton, clearButton;
    private JPanel buttonPanel;

    private double firstNumber;
    private char operation;

    public AlgebraAssistGUI() {
        setTitle("Simple Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        initComponents();
        setupLayout();
        setupListeners();

        setVisible(true);
    }

    private void initComponents() {
        displayField = new JTextField(10);
        displayField.setEditable(true);
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);

        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");
        equalsButton = new JButton("=");
        clearButton = new JButton("C");

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(Integer.toString(i));
        }

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
    }

    private void setupLayout() {
        add(displayField, BorderLayout.NORTH);

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(addButton);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subtractButton);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(multiplyButton);

        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(clearButton);
        buttonPanel.add(equalsButton);
        buttonPanel.add(divideButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void setupListeners() {
        CalculatorActionListener actionListener = new CalculatorActionListener();

        // Add action listeners to number buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i].addActionListener(actionListener);
        }

        addButton.addActionListener(actionListener);
        subtractButton.addActionListener(actionListener);
        multiplyButton.addActionListener(actionListener);
        divideButton.addActionListener(actionListener);
        equalsButton.addActionListener(actionListener);
        clearButton.addActionListener(actionListener);
    }

    private class CalculatorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
                displayField.setText(displayField.getText() + command);
            } else if (command.charAt(0) == 'C') {
                displayField.setText("");
            } else if (command.charAt(0) == '=') {
                double secondNumber = Double.parseDouble(displayField.getText());
                double result = 0;

                switch (operation) {
                    case '+':
                        result = firstNumber + secondNumber;
                        break;
                    case '-':
                        result = firstNumber - secondNumber;
                        break;
                    case '*':
                        result = firstNumber * secondNumber;
                        break;
                    case '/':
                        if (secondNumber != 0) {
                            result = firstNumber / secondNumber;
                        } else {
                            JOptionPane.showMessageDialog(AlgebraAssistGUI.this, "Cannot divide by zero!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                }

                displayField.setText(Double.toString(result));
            } else {
                firstNumber = Double.parseDouble(displayField.getText());
                operation = command.charAt(0);
                displayField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AlgebraAssistGUI::new);
    }
}
