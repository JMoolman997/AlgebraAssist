package com.JMoolman997.calculator.core;

/**
 * The ASTVisitor interface defines methods for visiting different types of AST nodes.
 *
 * @param <T> the return type of the visitor's operations
 */
public interface ASTVisitor<T> {
    T visitNumberNode(NumberNode node);
    T visitVariableNode(VariableNode node);
    T visitBinaryOpNode(BinaryOpNode node);
    T visitUnaryOpNode(UnaryOpNode node);
    T visitFunctionNode(FunctionNode node);

}

