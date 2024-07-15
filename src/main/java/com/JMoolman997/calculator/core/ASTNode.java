package com.JMoolman997.calculator.core;

import java.util.List;

public interface ASTNode {
    <T> T accept(ASTVisitor<T> visitor);
}

class NumberNode implements ASTNode {
    double value;

    NumberNode(double value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitNumberNode(this);
    }
}

class BinaryOpNode implements ASTNode {
    String operator;
    ASTNode left;
    ASTNode right;

    BinaryOpNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBinaryOpNode(this);
    }
}
class UnaryOpNode implements ASTNode {
    String operator;
    ASTNode operand;
    
    UnaryOpNode(String operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnaryOpNode(this);
    }
}

class FunctionNode implements ASTNode {
    String name;
    List<ASTNode> arguments;
    
    FunctionNode(String name, List<ASTNode> arguments) {
        this.name = name;
        this.arguments = arguments;
    }
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFunctionNode(this);
    }
}