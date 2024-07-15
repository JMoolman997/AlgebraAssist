package com.JMoolman997.calculator.core;

import java.util.List;

class Parser {
    private final List<Token> tokens;
    private int currentTokenIndex = 0;
    private Token currentToken;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(currentTokenIndex);
    }

    private void advance() {
        currentTokenIndex++;
        if (currentTokenIndex < tokens.size()) {
            currentToken = tokens.get(currentTokenIndex);
        }
    }

    private void eat(TokenType type) {
        if (currentToken.type == type) {
            advance();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.type);
        }
    }

    private ASTNode factor() {
        Token token = currentToken;
        if (token.type == TokenType.NUMBER) {
            eat(TokenType.NUMBER);
            return new NumberNode(Double.parseDouble(token.value));
        } else if (token.type == TokenType.IDENTIFIER) {
            eat(TokenType.IDENTIFIER);
            return new VariableNode(token.value);
        } else if (token.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            ASTNode node = expression();
            eat(TokenType.RPAREN);
            return node;
        } else if (token.type == TokenType.MINUS) {
            eat(TokenType.MINUS);
            return new UnaryOpNode("-", factor());
        }
        throw new RuntimeException("Unexpected token: " + token.type);
    }

    private ASTNode term() {
        ASTNode node = factor();
        while (currentToken.type == TokenType.MULTIPLY || currentToken.type == TokenType.DIVIDE) {
            Token token = currentToken;
            if (token.type == TokenType.MULTIPLY) {
                eat(TokenType.MULTIPLY);
                node = new BinaryOpNode("*", node, factor());
            } else if (token.type == TokenType.DIVIDE) {
                eat(TokenType.DIVIDE);
                node = new BinaryOpNode("/", node, factor());
            }
        }
        return node;
    }

    private ASTNode expression() {
        ASTNode node = term();
        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            Token token = currentToken;
            if (token.type == TokenType.PLUS) {
                eat(TokenType.PLUS);
                node = new BinaryOpNode("+", node, term());
            } else if (token.type == TokenType.MINUS) {
                eat(TokenType.MINUS);
                node = new BinaryOpNode("-", node, term());
            }
        }
        return node;
    }

    ASTNode parse() {
        return expression();
    }
}

