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
            // Token matchedToken = null;
            // for (Token prototype : tokenPrototypes) {
            //     if (prototype.matches(currentTokenString)) {
            //         matchedToken = prototype;
            //         break;
            //     }
            // }
            Token matchedToken = findMatchedToken(currentTokenString);

            // Continue to the next character if a match is found
            if (matchedToken != null) {
                pos++;
            // If no match is found, finalize the previous valid token    
            // } else if (currentTokenBuilder.length() > 1) {
            //     currentTokenBuilder.setLength(currentTokenBuilder.length() - 1);
            //     String finalTokenString = currentTokenBuilder.toString();
            //     // Token finalToken = null;
            //     // for (Token prototype : tokenPrototypes) {
            //     //     if (prototype.matches(finalTokenString)) {
            //     //         finalToken = prototype.createToken(finalTokenString);
            //     //         break;
            //     //     }
            //     // }
            //     Token finalToken = createFinalToken(finalTokenString);
            //     if (finalToken != null) {
            //         tokens.add(finalToken);
            //         currentTokenBuilder = new StringBuilder();  // Reset the builder after adding the token
            //     } else {
            //         throw new RuntimeException("Invalid token: " + finalTokenString);
            //     }
            // } else {
            //     throw new RuntimeException("Unexpected character: " + currentChar);
            // }
        } else {
            // Handle unmatched token scenario
            handleUnmatchedToken(tokens, currentTokenBuilder, currentChar);
        }
        }

        // Handle any remaining token after the loop
        finalizeRemainingToken(tokens, currentTokenBuilder);

        tokens.add(new EOFToken());
        return tokens;
    }

    /**
     * Tries to find a matching token prototype for the given input string.
     *
     * @param input the current token string to match
     * @return the matched token prototype, or null if no match is found
     */
    private Token findMatchedToken(String input) {
        for (Token prototype : tokenPrototypes) {
            if (prototype.matches(input)) {
                return prototype;
            }
        }
        return null;
    }

    /**
     * Handles the scenario when no matching token is found.
     * Finalizes the previous valid token and resets the token builder.
     *
     * @param tokens the list of tokens to add the finalized token to
     * @param currentTokenBuilder the current token builder
     */
    private void handleUnmatchedToken(List<Token> tokens, StringBuilder currentTokenBuilder, char currentChar) {
        if (currentTokenBuilder.length() > 1) {
            // Remove the last character and finalize the token
            currentTokenBuilder.setLength(currentTokenBuilder.length() - 1);
            String finalTokenString = currentTokenBuilder.toString();
            Token finalToken = createFinalToken(finalTokenString);
            if (finalToken != null) {
                tokens.add(finalToken);
                // Start new token with the unmatched character
                currentTokenBuilder.setLength(0);
                currentTokenBuilder.append(currentChar);
                pos++;
            } else {
                throw new RuntimeException("Invalid token: " + finalTokenString);
            }
        } else {
            throw new RuntimeException("Unexpected character: " + currentChar);
        }
    }
    

    /**
     * Finalizes any remaining token in the token builder after the main loop.
     *
     * @param tokens the list of tokens to add the finalized token to
     * @param currentTokenBuilder the current token builder
     */
    private void finalizeRemainingToken(List<Token> tokens, StringBuilder currentTokenBuilder) {
        if (currentTokenBuilder.length() > 0) {
            String finalTokenString = currentTokenBuilder.toString();
            Token finalToken = createFinalToken(finalTokenString);
            if (finalToken != null) {
                tokens.add(finalToken);
            } else {
                throw new RuntimeException("Invalid token: " + finalTokenString);
            }
        }
    }

    /**
     * Creates a final token from the given input string using the token prototypes.
     *
     * @param input the input string to create the token from
     * @return the created token, or null if no matching prototype is found
     */
    private Token createFinalToken(String input) {
        for (Token prototype : tokenPrototypes) {
            if (prototype.matches(input)) {
                return prototype.createToken(input);
            }
        }
        return null;
    }
}
