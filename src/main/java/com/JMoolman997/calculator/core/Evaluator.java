package com.JMoolman997.calculator.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
// import java.util.function.Function;

class Evaluator implements ASTVisitor<Double> {
    private final Map<String, UnaryOperator<Double>> unaryFunctions;
    private final Map<String, BinaryOperator<Double>> binaryFunctions;
    // private final Map<String, Function<Double, Double>> mathFunctions;
    private final Map<String, Double> variables = new HashMap<>();
   
    public Evaluator() {
        // Initialize unary functions
        unaryFunctions = new HashMap<>();
        unaryFunctions.put("sin", Math::sin);
        unaryFunctions.put("cos", Math::cos);
        unaryFunctions.put("tan", Math::tan);
        unaryFunctions.put("csc", x -> 1 / Math.sin(x));
        unaryFunctions.put("sec", x -> 1 / Math.cos(x));
        unaryFunctions.put("cot", x -> 1 / Math.tan(x));
        unaryFunctions.put("asin", Math::asin);
        unaryFunctions.put("acos", Math::acos);
        unaryFunctions.put("atan", Math::atan);
        unaryFunctions.put("abs", Math::abs);
        unaryFunctions.put("sqrt", Math::sqrt);

        binaryFunctions = new HashMap<>();
        binaryFunctions.put("+", (a, b) -> a + b);
        binaryFunctions.put("-", (a, b) -> a - b);
        binaryFunctions.put("*", (a, b) -> a * b);
        binaryFunctions.put("/", (a, b) -> a / b);
        binaryFunctions.put("^", (a, b) -> Math.pow(a, b));
        
    }

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
        double leftValue = children.get(0).accept(this);
        double rightValue = children.get(1).accept(this);

        if (binaryFunctions.containsKey(node.operator)) {
            return binaryFunctions.get(node.operator).apply(leftValue, rightValue);
        } else {
            throw new IllegalArgumentException("Unknown binary operator: " + node.operator);
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

