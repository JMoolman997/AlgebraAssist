package com.JMoolman997.calculator.core;

import java.util.ArrayList;
import java.util.List;

class Lexer {
    private final String input;
    private int pos = 0;
    private char currentChar;

    Lexer(String input) {
        this.input = input;
        this.currentChar = input.charAt(pos);
    }

    private void advance() {
        pos++;
        if (pos < input.length()) {
            currentChar = input.charAt(pos);
        } else {
            currentChar = '\0';
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private String number() {
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }
            if (Character.isDigit(currentChar)) {
                tokens.add(new Token(TokenType.NUMBER, number()));
                continue;
            }
            switch (currentChar) {
                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+"));
                    break;
                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-"));
                    break;
                case '*':
                    tokens.add(new Token(TokenType.MULTIPLY, "*"));
                    break;
                case '/':
                    tokens.add(new Token(TokenType.DIVIDE, "/"));
                    break;
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, "("));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, ")"));
                    break;
                default:
                    throw new RuntimeException("Unexpected character: " + currentChar);
            }
            advance();
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}

enum TokenType {
    NUMBER, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN, EOF
}

class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
}