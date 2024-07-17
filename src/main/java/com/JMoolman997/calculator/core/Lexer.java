package com.JMoolman997.calculator.core;

import java.util.List;

public interface Lexer {

    List<Token> tokenize();    
}


class Main {
    public static void main(String[] args) {
        String input = "3 + 5 * (2 - 8)";

        Lexer lexer = new SimpleLexer(input);
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