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
        while (Character.isDigit(currentChar) || currentChar == '.') {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    private String identifier() {
        StringBuilder result = new StringBuilder();
        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
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
            if (Character.isLetter(currentChar)) {
                String identifier = identifier();
                tokens.add(new Token(TokenType.IDENTIFIER, identifier));
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
                case '=':
                    tokens.add(new Token(TokenType.EQUALS, "="));
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
    NUMBER, IDENTIFIER, PLUS, MINUS, MULTIPLY, DIVIDE, LPAREN, RPAREN, EQUALS, EOF
}

class Token {
    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
class Main {
    public static void main(String[] args) {
        String input = "3 + 5 * (2 - 8)";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }

        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        Evaluator evaluator = new Evaluator();
        double result = ast.accept(evaluator);

        System.out.println("Result: " + result);
    }
}