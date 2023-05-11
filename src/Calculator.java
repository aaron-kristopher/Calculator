import java.util.*;

public class Calculator {
    static Scanner scanner = new Scanner(System.in);
    static String run;

    public static void main(String[] args) {
        do {
            runCalculator();
        } while (isRerun());

    }

    static void runCalculator() {
        double answer = 0.0;

        String notation = getNotation();

        // Splits by mathematical operation & returns only numbers
        // Splits by numbers of varying digits & returns only operations

        String[] tokens = notation.split("\\s+");

        // String[] stringNumbers = notation.split("[\\(\\)\\^\\*\\/+-]");

        ArrayList<Double> numbers = parseNumbers(tokens);
        ArrayList<String> operations = parseMathOperations(tokens);

        // Converts operations array to arrayList for easier manipulation

        String newNotation = displayNotation(numbers, operations);

        System.out.printf("\n\nAnswer: %s", calculate(numbers, operations, newNotation, answer));

    }

    private static ArrayList<String> parseMathOperations(String[] tokens) {
        ArrayList<String> operations = new ArrayList<>();

        for (String token : tokens) {
            if (token.matches("[\\(\\)\\^+\\-*/]")) {
                operations.add(token);
            }
        }

        return operations;
    }

    private static ArrayList<Double> parseNumbers(String[] tokens) {
        ArrayList<Double> numbers = new ArrayList<>();

        for (String token : tokens) {
            if (token.matches("([0-9]+)")) {
                if (!token.equals(""))
                    numbers.add((double) Integer.parseInt(token));
            }
        }

        return numbers;
    }

    static String getNotation() {
        System.out.print("\nEnter notation: ");
        return scanner.nextLine();
    }

    // Converts string numbers to double for future calculation

    // static void convertToNumbers(ArrayList<Double> numbers, String[]
    // stringNumbers) {
    // for (String number : stringNumbers) {
    // if (!number.equals(""))
    // numbers.add((double) Integer.parseInt(number));
    // }
    // }

    static String displayNotation(ArrayList<Double> numbers, ArrayList<String> operations) {

        return "Numbers: " + numbers + "\nOperations: " + operations;
    }

    static double calculate(ArrayList<Double> numbers, ArrayList<String> operations, String notation, double answer) {

        if (operations.contains("(")) {

            int parenthesisCount = 0;

            for (String operation : operations) {
                if (operation.equals("("))
                    parenthesisCount++;
            }

            int subsetStart = operations.lastIndexOf("(");
            int subsetEnd = operations.indexOf(")");

            ArrayList<String> operationSublist = new ArrayList<>();
            operationSublist.addAll(operations.subList(subsetStart + 1, subsetEnd));
            ArrayList<Double> numberSublist = new ArrayList<>();
            numberSublist.addAll(numbers.subList(subsetStart - (parenthesisCount - 1), subsetEnd));

            for (int i = subsetStart; i <= subsetEnd; i++) {
                if (i != subsetEnd)
                    numbers.remove(subsetStart - (parenthesisCount - 1));
                operations.remove(subsetStart);
            }

            numbers.add(subsetStart - (parenthesisCount - 1),
                    calculate(numberSublist, operationSublist, notation, answer));

            return calculate(numbers, operations, notation, answer);

        }

        if (operations.contains("^")) { // Checks for exponent denoted by symbol '^'

            for (int i = 0; i < operations.size(); i++) {

                // Gets numbers on both sides of the operation to do calculation
                // The numbers to be calculated will always precede and succeed the operation
                // Hence accessing them by operation index

                if (operations.get(i).equals("^")) {

                    double numBefore = numbers.get(i);
                    double numAfter = numbers.get(i + 1);

                    answer = Math.pow(numBefore, numAfter);

                    System.out.printf("Exponents: %s ^ %s = %s\n\n", numBefore, numAfter, answer);

                    numbers.set(i, answer);
                    operations.remove(i);
                    numbers.remove(i + 1);

                    String newNotation = displayNotation(numbers, operations);

                    // Recurses to ensure all instances of the operaiton are done before
                    // following the next order of operation

                    if (numbers.size() != 0)
                        return calculate(numbers, operations, newNotation, answer);
                }
            }
        }

        // Catches either operation and iterates over operations array
        // to follow operation rule precedence

        if (operations.contains("*") || operations.contains("/")) {

            // Ensures that since both operations are of equal value,
            // whichever operation comes first is evaluated

            for (int i = 0; i < operations.size(); i++) {

                if (operations.get(i).equals("*")) {

                    double numBefore = numbers.get(i);
                    double numAfter = numbers.get(i + 1);

                    answer = numbers.get(i) * numbers.get(i + 1);

                    System.out.printf("Multiplication: %s * %s = %s\n\n", numBefore, numAfter, answer);

                    numbers.set(i, answer);
                    operations.remove(i);
                    numbers.remove(i + 1);

                    String newNotation = displayNotation(numbers, operations);

                    if (numbers.size() != 0)
                        return calculate(numbers, operations, newNotation, answer);

                } else if (operations.get(i).equals("/")) {

                    double numBefore = numbers.get(i);
                    double numAfter = numbers.get(i + 1);

                    answer = numbers.get(i) / numbers.get(i + 1);

                    System.out.printf("Division: %s / %s = %s\n\n", numBefore, numAfter, answer);

                    numbers.set(i, answer);
                    operations.remove(i);
                    numbers.remove(i + 1);

                    String newNotation = displayNotation(numbers, operations);

                    if (numbers.size() != 0)
                        return calculate(numbers, operations, newNotation, answer);
                }
            }
        }

        if (operations.contains("+") || operations.contains("-")) {

            for (int i = 0; i < operations.size(); i++) {

                if (operations.get(i).equals("+")) {

                    double numBefore = numbers.get(i);
                    double numAfter = numbers.get(i + 1);

                    answer = numbers.get(i) + numbers.get(i + 1);

                    System.out.printf("Addition: %s + %s = %s\n\n", numBefore, numAfter, answer);

                    numbers.set(i, answer);
                    operations.remove(i);
                    numbers.remove(i + 1);

                    String newNotation = displayNotation(numbers, operations);

                    if (numbers.size() != 0)
                        return calculate(numbers, operations, newNotation, answer);

                } else if (operations.get(i).equals("-")) {

                    double numBefore = numbers.get(i);
                    double numAfter = numbers.get(i + 1);

                    answer = numbers.get(i) - numbers.get(i + 1);

                    System.out.printf("Subtraction: %s - %s = %s\n\n", numBefore, numAfter, answer);

                    numbers.set(i, answer);
                    operations.remove(i);
                    numbers.remove(i + 1);

                    String newNotation = displayNotation(numbers, operations);

                    if (numbers.size() != 0)
                        return calculate(numbers, operations, newNotation, answer);
                }
            }
        }

        return answer;
    }

    static boolean isRerun() {
        System.out.print("\nDo you want to do another calculation? (Y/N): ");
        run = scanner.nextLine().toLowerCase();
        return run.equals("y");
    }
}
