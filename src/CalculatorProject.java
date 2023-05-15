import java.util.*;

public class CalculatorProject {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter your equation: ");
        String equation = scanner.nextLine();

        // parses the equation, separating numbers and operators
        ArrayList<String> infix = convertToInfix(equation);

        // shows the result of convertion of infix to postfix
        ArrayList<String> postfix = infixToPostfix(infix);

        System.out.print("Postfix Equivalence: ");
        for (String postfixElement : postfix) {
            System.out.print(postfixElement + " ");
        }
        System.out.println();

        // shows the result of the postfix evalutation

        System.out.println("Result: " + evaluatePostfix(postfix));
    }

    private static ArrayList<String> convertToInfix(String equation) {
        ArrayList<String> infix = new ArrayList<>();
        for (int i = 0; i < equation.length(); i++) {
            char equationCharacter = equation.charAt(i);
            if (Character.isDigit(equationCharacter) || equationCharacter == '.') {
                int digitCharacter = i;
                while (digitCharacter < equation.length() && (Character.isDigit(equation.charAt(digitCharacter))
                        || equation.charAt(digitCharacter) == '.')) {
                    digitCharacter++;
                }
                infix.add(equation.substring(i, digitCharacter));
                i = digitCharacter - 1;
            } else {
                infix.add(Character.toString(equationCharacter));
            }
        }
        return infix;
    }

    public static ArrayList<String> infixToPostfix(ArrayList<String> infix) {
        ArrayList<String> postfix = new ArrayList<>();
        ArrayList<String> operationArray = new ArrayList<>();

        for (int i = 0; i < infix.size(); i++) {
            String infixElement = infix.get(i);

            if (isNumber(infixElement)) {
                postfix.add(infixElement);

            } else if (infixElement.equals("(")) {
                operationArray.add(infixElement);

            } else if (infixElement.equals(")")) {

                for (int operationIndex = operationArray.size() - 1; operationIndex >= 0; operationIndex--) {

                    String operation = operationArray.get(operationIndex);

                    if (operation.equals("(")) {
                        operationArray.remove(operationIndex);
                        break;

                    }
                    postfix.add(operation);
                    operationArray.remove(operationIndex);
                }

            } else {
                for (int operationIndex = operationArray.size() - 1; operationIndex >= 0; operationIndex--) {

                    String operation = operationArray.get(operationIndex);

                    if (operation.equals("(") || order(operation) < order(infixElement)) {
                        break;
                    }
                    postfix.add(operation);
                    operationArray.remove(operationIndex);
                }
                operationArray.add(infixElement);
            }
        }

        for (int operation = operationArray.size() - 1; operation >= 0; operation--) {
            postfix.add(operationArray.get(operation));
            operationArray.remove(operation);
        }

        return postfix;
    }

    static double evaluatePostfix(ArrayList<String> postfix) {
        ArrayList<Double> answer = new ArrayList<>();

        for (String postfixElement : postfix) {
            if (isNumber(postfixElement)) {
                answer.add(Double.parseDouble(postfixElement));
                System.out.println("Updated List: " + answer.toString());
            } else {
                double secondNum = answer.remove(answer.size() - 1);
                double firstNum = answer.remove(answer.size() - 1);
                double result = 0;
                if (postfixElement.equals("+")) {
                    result = firstNum + secondNum;
                } else if (postfixElement.equals("-")) {
                    result = firstNum - secondNum;
                } else if (postfixElement.equals("*")) {
                    result = firstNum * secondNum;
                } else if (postfixElement.equals("/")) {
                    result = firstNum / secondNum;
                } else if (postfixElement.equals("^")) {
                    result = Math.pow(firstNum, secondNum);

                }
                answer.add(result);

            }
        }

        return answer.get(0);
    }

    static boolean isNumber(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    static int order(String s) {
        if (isNumber(s)) {
            return 0;
        } else if (s.equals("+") || s.equals("-")) {
            return 1;
        } else if (s.equals("*") || s.equals("/")) {
            return 2;
        } else if (s.equals("^")) {
            return 3;
        } else {
            return -1;
        }

    }
}