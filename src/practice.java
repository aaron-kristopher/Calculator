import java.util.*;

public class practice {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the notation: ");
        String num = sc.nextLine();
        sc.close();
        double answer = evaluate(num);
        System.out.println("Answer: " + answer);
    }

    public static double evaluate(String num) {
        String[] operator = num.split("(?<=[+\\-*/^])|(?=[+\\-*/^])");
        double answer = Double.parseDouble(operator[0]);
        String op = null;
        for (int i = 1; i < operator.length; i += 2) {
            double value = Double.parseDouble(operator[i + 1]);
            if (operator[i].equals("^")) {
                answer = Math.pow(answer, value);
            } else if (operator[i].equals("*") || operator[i].equals("/")) {
                for (int j = 1; j < operator.length; i++) {
                    if (operator[j].equals("*")) {
                        op = String.valueOf(operator[j]);
                        double m = calculator(answer, value, op);
                    } else {
                        double d = calculator(answer, value, op);
                    }
                }
            } else if (operator[i].equals("+") || operator[i].equals("-")) {
                for (int k = 1; k < operator.length; k++) {
                    if (operator[k].equals("+")) {
                        op = String.valueOf(operator[k]);
                        double a = calculator(answer, value, op);
                    } else {
                        double s = calculator(answer, value, op);
                    }
                }
            }
        }
        return answer;
    }

    private static double calculator(double answer, double value, String op) {
        switch (op) {
            case "+":
                return answer + value;
            case "-":
                return answer - value;
            case "*":
                return answer * value;
            case "/":
                return answer / value;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}