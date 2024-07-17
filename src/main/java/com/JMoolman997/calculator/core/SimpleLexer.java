package com.JMoolman997.calculator.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleLexer implements Lexer {
    private final List<Token> tokenPrototypes;
    private final String input;
    private int pos = 0;

    /**
     * Constructs a SimpleLexer with the specified input string.
     * Initializes the list of token prototypes.
     *
     * @param input the input string to tokenize
     */    
    SimpleLexer(String input) {
        this.input = input;
        this.tokenPrototypes = Arrays.asList(
            new NumberToken(""),
            new IdentifierToken(""),
            new OperatorToken(""),
            new FunctionToken(""),
            new ConstantToken(""),
            new ParenToken("")
        );
    }

    /**
     * Tokenizes the input string and returns a list of tokens.
     *
     * @return a list of tokens representing the input string
     * @throws RuntimeException if an invalid token or unexpected character is encountered
     */    
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentTokenBuilder = new StringBuilder();

        while (pos < input.length()) {
            char currentChar = input.charAt(pos);

            // Skip whitespace characters
            if (Character.isWhitespace(currentChar)) {
                pos++;
                continue;
            }

            currentTokenBuilder.append(currentChar);
            String currentTokenString = currentTokenBuilder.toString();
            
            // Try to match the current token string with any token prototype
            Token matchedToken = null;
            for (Token prototype : tokenPrototypes) {
                if (prototype.matches(currentTokenString)) {
                    matchedToken = prototype;
                    break;
                }
            }

            // Continue to the next character if a match is found
            if (matchedToken != null) {
                pos++;
            // If no match is found, finalize the previous valid token    
            } else if (currentTokenBuilder.length() > 1) {
                currentTokenBuilder.setLength(currentTokenBuilder.length() - 1);
                String finalTokenString = currentTokenBuilder.toString();
                Token finalToken = null;
                for (Token prototype : tokenPrototypes) {
                    if (prototype.matches(finalTokenString)) {
                        finalToken = prototype.createToken(finalTokenString);
                        break;
                    }
                }
                if (finalToken != null) {
                    tokens.add(finalToken);
                    currentTokenBuilder = new StringBuilder();  // Reset the builder after adding the token
                } else {
                    throw new RuntimeException("Invalid token: " + finalTokenString);
                }
            } else {
                throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }

        // Handle any remaining token after the loop
        if (currentTokenBuilder.length() > 0) {
            String finalTokenString = currentTokenBuilder.toString();
            Token finalToken = null;
            for (Token prototype : tokenPrototypes) {
                if (prototype.matches(finalTokenString)) {
                    finalToken = prototype.createToken(finalTokenString);
                    break;
                }
            }
            if (finalToken != null) {
                tokens.add(finalToken);
            } else {
                throw new RuntimeException("Invalid token: " + finalTokenString);
            }
        }

        tokens.add(new EOFToken());
        return tokens;
    }
}
