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

    private double factor() {
        Token token = currentToken;
        if (token.type == TokenType.NUMBER) {
            eat(TokenType.NUMBER);
            return Double.parseDouble(token.value);
        } else if (token.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            double result = expression();
            eat(TokenType.RPAREN);
            return result;
        }
        throw new RuntimeException("Unexpected token: " + token.type);
    }

    private double term() {
        double result = factor();
        while (currentToken.type == TokenType.MULTIPLY || currentToken.type == TokenType.DIVIDE) {
            Token token = currentToken;
            if (token.type == TokenType.MULTIPLY) {
                eat(TokenType.MULTIPLY);
                result *= factor();
            } else if (token.type == TokenType.DIVIDE) {
                eat(TokenType.DIVIDE);
                result /= factor();
            }
        }
        return result;
    }

    private double expression() {
        double result = term();
        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            Token token = currentToken;
            if (token.type == TokenType.PLUS) {
                eat(TokenType.PLUS);
                result += term();
            } else if (token.type == TokenType.MINUS) {
                eat(TokenType.MINUS);
                result -= term();
            }
        }
        return result;
    }

    double parse() {
        return expression();
    }
}
class Main {
    public static void main(String[] args) {
        String input = "3 + 5 * (2 - 8)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        double result = parser.parse();

        System.out.println("Result: " + result);
    }
}
