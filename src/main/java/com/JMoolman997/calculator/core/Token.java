package com.JMoolman997.calculator.core;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public interface Token {
    enum TokenType {
        NUMBER, IDENTIFIER, OPERATOR, FUNCTION, CONSTANT, LPAREN, RPAREN, EOF
    }

    TokenType getType();
    String getValue();
    boolean matches(String input);
    Token createToken(String input);
}
abstract class AbstractToken implements Token {
    protected final String value;

    AbstractToken(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{type=" + getType() + ", value='" + value + "'}";
    }
}

class NumberToken extends AbstractToken {
    NumberToken(String value) {
        super(value);
    }

    @Override
    public TokenType getType() {
        return TokenType.NUMBER;
    }

    @Override
    public boolean matches(String input) {
        return input.matches("\\d+(\\.\\d+)?");
    }

    @Override
    public Token createToken(String input) {
        return new NumberToken(input);
    }
}

class IdentifierToken extends AbstractToken {
    IdentifierToken(String value) {
        super(value);
    }

    @Override
    public TokenType getType() {
        return TokenType.IDENTIFIER;
    }

    @Override
    public boolean matches(String input) {
        return input.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    @Override
    public Token createToken(String input) {
        return new IdentifierToken(input);
    }
}

class OperatorToken extends AbstractToken {
    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList("+", "-", "*", "/", "^"));

    OperatorToken(String value) {
        super(value);
    }

    @Override
    public TokenType getType() {
        return TokenType.OPERATOR;
    }

    @Override
    public boolean matches(String input) {
        return OPERATORS.contains(input);
    }

    @Override
    public Token createToken(String input) {
        return new OperatorToken(input);
    }
}

class FunctionToken extends AbstractToken {
    private static final Set<String> FUNCTIONS = new HashSet<>(Arrays.asList(
        "sin", "cos", "tan", "asin", "acos", "atan", "sqrt", "log", "ln"
    ));

    FunctionToken(String value) {
        super(value);
    }

    @Override
    public TokenType getType() {
        return TokenType.FUNCTION;
    }

    @Override
    public boolean matches(String input) {
        return FUNCTIONS.contains(input.toLowerCase());
    }

    @Override
    public Token createToken(String input) {
        return new FunctionToken(input.toLowerCase());
    }
}

class ConstantToken extends AbstractToken {
    private static final Map<String, Double> CONSTANTS = new HashMap<>();
    static {
        CONSTANTS.put("pi", Math.PI);
        CONSTANTS.put("e", Math.E);
    }

    ConstantToken(String value) {
        super(value);
    }

    @Override
    public TokenType getType() {
        return TokenType.CONSTANT;
    }

    @Override
    public boolean matches(String input) {
        return CONSTANTS.containsKey(input.toLowerCase());
    }

    @Override
    public Token createToken(String input) {
        return new ConstantToken(input.toLowerCase());
    }

    // public Double getValue() {
    //     return CONSTANTS.get(this.value);
    // }
}

class ParenToken extends AbstractToken {
    ParenToken(String value) {
        super(value);
    }

    @Override
    public TokenType getType() {
        return value.equals("(") ? TokenType.LPAREN : TokenType.RPAREN;
    }

    @Override
    public boolean matches(String input) {
        return input.equals("(") || input.equals(")");
    }

    @Override
    public Token createToken(String input) {
        return new ParenToken(input);
    }
}

class EOFToken implements Token {
    @Override
    public TokenType getType() {
        return TokenType.EOF;
    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public boolean matches(String input) {
        return false;
    }

    @Override
    public Token createToken(String input) {
        return this;
    }
}