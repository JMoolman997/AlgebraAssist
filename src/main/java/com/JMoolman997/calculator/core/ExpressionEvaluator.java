package com.JMoolman997.calculator.core;

import java.util.*;

/**
 * The ExpressionEvaluator class provides methods to convert infix expressions to postfix (RPN) and evaluate them.
 * It supports basic arithmetic operations as well as trigonometric functions.
 */
public class ExpressionEvaluator {

    // Map to hold operator precedence levels
    private static final Map<String, Integer> precedence = new HashMap<>();

    // Map to hold operation implementations
    private final Map<String, Operation> operations = new HashMap<>();

    // Static block to initialize operator precedence levels
    static {
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("sin", 4);
        precedence.put("cos", 4);
        precedence.put("tan", 4);
        precedence.put("sec", 4);
        precedence.put("csc", 4);
        precedence.put("cot", 4);
        precedence.put("asin", 4);
        precedence.put("acos", 4);
        precedence.put("atan", 4);
    }

    /**
     * Constructor to initialize the operations map with corresponding operation implementations.
     */
    public ExpressionEvaluator() {
        operations.put("+", new Addition());
        operations.put("-", new Subtraction());
        operations.put("*", new Multiplication());
        operations.put("/", new Division());
        operations.put("sin", new Sine());
        operations.put("cos", new Cosine());
        operations.put("tan", new Tangent());
        operations.put("sec", new Secant());
        operations.put("csc", new Cosecant());
        operations.put("cot", new Cotangent());
        operations.put("asin", new Arcsine());
        operations.put("acos", new Arccosine());
        operations.put("atan", new Arctangent());
    }

    /**
     * Converts an infix expression to postfix (Reverse Polish Notation) using the Shunting Yard algorithm.
     *
     * @param tokens The array of strings representing the infix expression.
     * @return A list of strings representing the postfix expression.
     */
    public List<String> infixToPostfix(String[] tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                output.add(token);
            } else if (precedence.containsKey(token)) {
                // Pop operators with higher or equal precedence from the stack
                while (!operators.isEmpty() && precedence.get(token) <= precedence.get(operators.peek())) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                // Pop operators until the corresponding '(' is found
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                // Discard the '(' from the stack
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop();
                }
            }
        }

        // Pop all remaining operators from the stack
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }


    /**
     * Evaluates a postfix (Reverse Polish Notation) expression.
     * 
     * @param tokens The list of strings representing the postfix expression.
     * @return The result of the evaluation as a double.
     */
    public double evaluatePostfix(List<String> tokens) {
        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (operations.containsKey(token)) {
                Operation operation = operations.get(token);
                double result;
                if (isUnaryOperation(token)) {
                    double operand = stack.pop();
                    result = operation.execute(operand);
                } else {
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    result = operation.execute(operand1, operand2);
                }
                stack.push(result);
            }
        }

        return stack.pop();
    }

    /**
     * Checks if the given token is a numeric value.
     * 
     * @param token The string to be checked.
     * @return True if the token is numeric, otherwise false.
     */
    public boolean isNumeric(String token) {
        if (token == null) {
            return false;
        }
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the given operation is a unary operation.
     * 
     * @param token The operation to be checked.
     * @return True if the operation is unary, otherwise false.
     */
    private boolean isUnaryOperation(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("tan") ||
               token.equals("sec") || token.equals("csc") || token.equals("cot") ||
               token.equals("asin") || token.equals("acos") || token.equals("atan");
    }
}







