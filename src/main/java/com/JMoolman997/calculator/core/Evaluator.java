package com.JMoolman997.calculator.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Evaluator implements ASTVisitor<Double> {
    private final Map<String, Double> variables = new HashMap<>();

    @Override
    public Double visitNumberNode(NumberNode node) {
        return node.value;
    }

    @Override
    public Double visitVariableNode(VariableNode node) {
        if (variables.containsKey(node.name)) {
            return variables.get(node.name);
        } else {
            throw new RuntimeException("Undefined variable: " + node.name);
        }
    }

    @Override
    public Double visitBinaryOpNode(BinaryOpNode node) {
        List<ASTNode> children = node.getChildren();
        double left = children.get(0).accept(this);
        double right = children.get(1).accept(this);
        switch (node.operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    throw new IllegalArgumentException("Division by zero");
                }
                return left / right;
            default:
                throw new IllegalArgumentException("Unknown operator: " + node.operator);
        }
    }

    @Override
    public Double visitUnaryOpNode(UnaryOpNode node) {
        double operand = node.getChildren().get(0).accept(this);
        switch (node.operator) {
            case "-":
                return -operand;
            default:
                throw new RuntimeException("Unknown operator: " + node.operator);
        }
    }

    @Override
    public Double visitFunctionNode(FunctionNode node) {
        List<Double> evaluatedArgs = node.getChildren().stream()
            .map(arg -> arg.accept(this))
            .collect(Collectors.toList());

        switch (node.name) {
            case "sin":
                if (evaluatedArgs.size() != 1)
                    throw new IllegalArgumentException("sin function requires 1 argument");
                return Math.sin(evaluatedArgs.get(0));
            case "cos":
                if (evaluatedArgs.size() != 1)
                    throw new IllegalArgumentException("cos function requires 1 argument");
                return Math.cos(evaluatedArgs.get(0));
            case "tan":
                if (evaluatedArgs.size() != 1)
                    throw new IllegalArgumentException("tan function requires 1 argument");
                return Math.tan(evaluatedArgs.get(0));
            case "pow":
                if (evaluatedArgs.size() != 2)
                    throw new IllegalArgumentException("pow function requires 2 arguments");
                return Math.pow(evaluatedArgs.get(0), evaluatedArgs.get(1));
            default:
                throw new RuntimeException("Unknown function: " + node.name);
        }
    }

    public void setVariable(String name, Double value) {
        variables.put(name, value);
    }
}

