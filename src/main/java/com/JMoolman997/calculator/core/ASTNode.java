package com.JMoolman997.calculator.core;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * The ASTNode interface defines the structure for nodes in the Abstract Syntax Tree.
 * Each node must implement the accept method for the visitor pattern.
 */
public interface ASTNode {
    /**
     * Accept a visitor to perform some operation on this node.
     * 
     * @param visitor the visitor to accept
     * @param <T> the return type of the visitor's operation
     * @return the result of the visitor's operation
     */
    <T> T accept(ASTVisitor<T> visitor);

    /**
     * Add a child node to this node.
     * 
     * @param node the child node to add
     */
    void add(ASTNode node);

    /**
     * Remove a child node from this node.
     * 
     * @param node the child node to remove
     */
    void remove(ASTNode node);

    /**
     * Get the list of child nodes.
     * 
     * @return the list of child nodes
     */
    List<ASTNode> getChildren();
}

/**
 * Represents a numeric value in the AST.
 */
class NumberNode implements ASTNode {
    double value;

    NumberNode(double value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitNumberNode(this);
    }

    @Override
    public void add(ASTNode node) {
        throw new UnsupportedOperationException("Cannot add to a leaf node");
    }

    @Override
    public void remove(ASTNode node) {
        throw new UnsupportedOperationException("Cannot remove from a leaf node");
    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.emptyList();
    }
}
class VariableNode implements ASTNode {
    String name;

    VariableNode(String name) {
        this.name = name;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitVariableNode(this);
    }

    @Override
    public void add(ASTNode node) {
        throw new UnsupportedOperationException("Cannot add to a leaf node");
    }

    @Override
    public void remove(ASTNode node) {
        throw new UnsupportedOperationException("Cannot remove from a leaf node");
    }

    @Override
    public List<ASTNode> getChildren() {
        return Collections.emptyList();
    }
}
/**
 * Represents a binary operation (e.g., addition, multiplication) in the AST.
 */
class BinaryOpNode implements ASTNode {
    String operator;
    private final List<ASTNode> children = new ArrayList<>();

    BinaryOpNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        add(left);
        add(right);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitBinaryOpNode(this);
    }

    @Override
    public void add(ASTNode node) {
        children.add(node);
    }

    @Override
    public void remove(ASTNode node) {
        children.remove(node);
    }

    @Override
    public List<ASTNode> getChildren() {
        return children;
    }
}

/**
 * Represents a unary operation (e.g., negation) in the AST.
 */
class UnaryOpNode implements ASTNode {
    String operator;
    private final List<ASTNode> children = new ArrayList<>();

    UnaryOpNode(String operator, ASTNode operand) {
        this.operator = operator;
        add(operand);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitUnaryOpNode(this);
    }

    @Override
    public void add(ASTNode node) {
        children.add(node);
    }

    @Override
    public void remove(ASTNode node) {
        children.remove(node);
    }

    @Override
    public List<ASTNode> getChildren() {
        return children;
    }
}

/**
 * Represents a function call (e.g., sin, cos) in the AST.
 */
class FunctionNode implements ASTNode {
    String name;
    private final List<ASTNode> arguments = new ArrayList<>();

    FunctionNode(String name, List<ASTNode> arguments) {
        this.name = name;
        this.arguments.addAll(arguments);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visitFunctionNode(this);
    }

    @Override
    public void add(ASTNode node) {
        arguments.add(node);
    }

    @Override
    public void remove(ASTNode node) {
        arguments.remove(node);
    }

    @Override
    public List<ASTNode> getChildren() {
        return arguments;
    }
}
