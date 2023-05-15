import java.util.*;

public class CalculatorProject {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your equation: ");
        String equation = scan.nextLine();

        // parses the equation, separating numbers and operators

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

        // shows the result of convertion of infix to postfix
        ArrayList<String> postfix = infixToPostfix(infix);
        System.out.print("Postfix Equivalence: ");
        for (String s : postfix) {
            System.out.print(s + " ");
        }
        System.out.println();

        // shows the result of the postfix evalutation

        System.out.println("Result: " + evaluatePostfix(postfix));
    }

    public static ArrayList<String> infixToPostfix(ArrayList<String> infix) {
        ArrayList<String> postfix = new ArrayList<>();
        ArrayList<String> tempStorage = new ArrayList<>();

        for (int i = 0; i < infix.size(); i++) {
            String infixElement = infix.get(i);

            if (isNumber(infixElement)) {
                postfix.add(infixElement);

            } else if (infixElement.equals("(")) {
                tempStorage.add(infixElement);

            } else if (infixElement.equals(")")) {

                for (int storageElemIndex = tempStorage.size() - 1; storageElemIndex >= 0; storageElemIndex--) {

                    String storageElement = tempStorage.get(storageElemIndex);

                    if (storageElement.equals("(")) {
                        tempStorage.remove(storageElemIndex);
                        break;

                    }
                    postfix.add(storageElement);
                    tempStorage.remove(storageElemIndex);
                }

            } else {
                for (int storageElemIndex = tempStorage.size() - 1; storageElemIndex >= 0; storageElemIndex--) {

                    String storageElement = tempStorage.get(storageElemIndex);
                    if (storageElement.equals("(") || order(storageElement) < order(infixElement)) {
                        break;
                    }
                    postfix.add(storageElement);
                    tempStorage.remove(storageElemIndex);
                }
                tempStorage.add(infixElement);
            }
        }

        for (int storageElement = tempStorage.size() - 1; storageElement >= 0; storageElement--) {
            postfix.add(tempStorage.get(storageElement));
            tempStorage.remove(storageElement);
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