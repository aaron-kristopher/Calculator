import java.util.Scanner;

public class CalculatorGPT {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a mathematical equation: ");
        String equation = scanner.nextLine();

        double result = evaluateExpression(equation);
        System.out.println("Result: " + result);
    }

    private static double evaluateExpression(String equation) {
        // Remove all whitespaces from the equation
        equation = equation.replaceAll("\\s", "");

        // Evaluate parentheses first
        while (equation.contains("(")) {
            int openIndex = equation.lastIndexOf("(");
            int closeIndex = equation.indexOf(")", openIndex);

            if (closeIndex == -1) {
                throw new IllegalArgumentException("Unbalanced parentheses");
            }

            String subExpression = equation.substring(openIndex + 1, closeIndex);
            double subResult = evaluateExpression(subExpression);

            equation = equation.substring(0, openIndex) + subResult + equation.substring(closeIndex + 1);
        }

        // Evaluate exponentiation (^) next
        while (equation.contains("^")) {
            int index = equation.indexOf("^");
            double operand1 = getOperand(equation, index, true);
            double operand2 = getOperand(equation, index, false);

            double result = Math.pow(operand1, operand2);
            equation = equation.substring(0, index - String.valueOf(operand1).length()) + result
                    + equation.substring(index + String.valueOf(operand2).length() + 1);
        }

        // Evaluate multiplication (*) and division (/) next
        while (equation.contains("*") || equation.contains("/")) {
            int index = equation.indexOf("*");

            if (index == -1 || (equation.indexOf("/") != -1 && equation.indexOf("/") < index)) {
                index = equation.indexOf("/");
            }

            double operand1 = getOperand(equation, index, true);
            double operand2 = getOperand(equation, index, false);

            double result = equation.charAt(index) == '*' ? operand1 * operand2 : operand1 / operand2;
            equation = equation.substring(0, index - String.valueOf(operand1).length()) + result
                    + equation.substring(index + String.valueOf(operand2).length() + 1);
        }

        // Evaluate addition (+) and subtraction (-) last
        double result = 0.0;
        String[] tokens = equation.split("(?=[+-])");

        for (String token : tokens) {
            double operand = Double.parseDouble(token.substring(1));
            result += (token.charAt(0) == '+') ? operand : -operand;
        }

        return result;
    }

    private static double getOperand(String equation, int operatorIndex, boolean beforeOperator) {
        int index = beforeOperator ? equation.lastIndexOf("+", operatorIndex - 1)
                : equation.indexOf("+", operatorIndex + 1);

        if (index == -1 || equation.lastIndexOf("-", operatorIndex - 1) > index
                || equation.indexOf("-", operatorIndex + 1) < index) {
            index = beforeOperator ? equation.lastIndexOf("-", operatorIndex - 1)
                    : equation.indexOf("-", operatorIndex + 1);
        }

        if (index == -1) {
            index = beforeOperator ? 0 : equation.length();
        }

        return Double.parseDouble(equation.substring(index, beforeOperator ? operatorIndex : operatorIndex + 1));
    }
}
