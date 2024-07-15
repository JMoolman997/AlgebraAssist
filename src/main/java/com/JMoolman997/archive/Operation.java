package com.JMoolman997.calculator.core;

/**
 * This interface represents a mathematical operation that can be performed on
 * operands (numbers). Each operation must be able to execute a predefined
 * number of operands and return a result as a double.
 *
 * @author JMoolman997
 */
public interface Operation {

    /**
     * Executes the operation on the given operands and returns the result as a
     * double.
     *
     * @param operands
     *            the operands to perform the operation on.
     * @return the result of the operation.
     * @throws IllegalArgumentException
     *             if the number of operands is not equal to the expected number.
     */
    double execute(double... operands);
}

/**
 * Implementation of the addition operation.
 */
class Addition implements Operation {

    @Override
    public double execute(double... operands) {
        return operands[0] + operands[1];
    }
}

/**
 * Implementation of the subtraction operation.
 */
class Subtraction implements Operation {

    @Override
    public double execute(double... operands) {
        return operands[0] - operands[1];
    }
}

/**
 * Implementation of the multiplication operation.
 */
class Multiplication implements Operation {

    @Override
    public double execute(double... operands) {
        return operands[0] * operands[1];
    }
}

/**
 * Implementation of the division operation.
 */
class Division implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands[1] == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return operands[0] / operands[1];
    }
}

/**
 * Implementation of the sine operation.
 */
class Sine implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Sine function requires exactly one operand.");
        }
        return Math.sin(operands[0]);
    }
}

/**
 * Implementation of the cosine operation.
 */
class Cosine implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Cosine function requires exactly one operand.");
        }
        return Math.cos(operands[0]);
    }
}

/**
 * Implementation of the tangent operation.
 */
class Tangent implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Tangent function requires exactly one operand.");
        }
        return Math.tan(operands[0]);
    }
}

/**
 * Implementation of the secant operation.
 */
class Secant implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Secant function requires exactly one operand.");
        }
        return 1 / Math.cos(operands[0]);
    }
}

/**
 * Implementation of the cosecant operation.
 */
class Cosecant implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Cosecant function requires exactly one operand.");
        }
        return 1 / Math.sin(operands[0]);
    }
}

/**
 * Implementation of the cotangent operation.
 */
class Cotangent implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Cotangent function requires exactly one operand.");
        }
        return 1 / Math.tan(operands[0]);
    }
}


/**
 * Implementation of the arcsine operation.
 */
class Arcsine implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Arcsine function requires exactly one operand.");
        }
        return Math.asin(operands[0]);
    }
}

/**
 * Implementation of the arccosine operation.
 */
class Arccosine implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Arccosine function requires exactly one operand.");
        }
        return Math.acos(operands[0]);
    }
}

/**
 * Implementation of the arctangent operation.
 */
class Arctangent implements Operation {

    @Override
    public double execute(double... operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Arctangent function requires exactly one operand.");
        }
        return Math.atan(operands[0]);
    }
}









