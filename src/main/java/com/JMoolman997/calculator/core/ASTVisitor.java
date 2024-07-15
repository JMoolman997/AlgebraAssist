package com.JMoolman997.calculator.core;

import java.util.List;
import java.util.stream.Collectors;

public interface ASTVisitor<T> {
    T visitNumberNode(NumberNode node);
    T visitBinaryOpNode(BinaryOpNode node);
    T visitUnaryOpNode(UnaryOpNode node);
    T visitFunctionNode(FunctionNode node);

}

class Evaluator implements ASTVisitor<Double> {

    @Override
    public Double visitNumberNode(NumberNode node) {
        return node.value;
    }

    @Override
    public Double visitBinaryOpNode(BinaryOpNode node) {
        double left = node.left.accept(this);
        double right = node.right.accept(this);
        switch (node.operator) {
            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/": return left / right;
            default : throw new IllegalArgumentException("Unknown operator: " + node.operator);
        }
    }

    @Override
    public Double visitUnaryOpNode(UnaryOpNode node) {
        double operand = node.operand.accept(this);
        switch (node.operator) {
            case "-": return -operand;
            default: throw new RuntimeException("Unknown operator: " + node.operator);
        }
    }

    @Override
    public Double visitFunctionNode(FunctionNode node) {
        List<Double> evaluatedArgs = node.arguments.stream()
            .map(arg -> arg.accept(this))
            .collect(Collectors.toList());
        
        switch(node.name) {
            case "sin": return Math.sin(evaluatedArgs.get(0));
            case "cos": return Math.cos(evaluatedArgs.get(0));
            case "tan": return Math.tan(evaluatedArgs.get(0));
            // case "pow": return Math.pow(evaluatedArgs.get(0), evaluatedArgs.get(1));
            default: throw new RuntimeException("Unknown function: " + node.name);
        }
    }    
}