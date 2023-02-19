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

        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<String> operations = new ArrayList<>();
        double answer = 0.0;

        String notation = getNotation();

        // Splits by mathematical operation & returns only numbers
        // Splits by numbers of varying digits & returns only operations

        String[] stringNumbers = notation.split("[e\\*\\/+-]");
        String[] operationsArray = notation.split("([0-9]+)");

        convertToNumbers(numbers, stringNumbers);

        // Converts operations array to arrayList for easier manipulation
        Collections.addAll(operations, operationsArray);

        System.out.printf("Answer: %s", doEquation(numbers, operations, answer));
    }

    static String getNotation() {
        System.out.print("Enter notation: ");
        return scanner.nextLine();
    }

    // Converts string numbers to double for future calculation

    static void convertToNumbers(ArrayList<Double> numbers, String[] stringNumbers) {
        for (String number : stringNumbers) {
            numbers.add((double) Integer.parseInt(number));
        }
    }

    static double doEquation(ArrayList<Double> numbers, ArrayList<String> operations, double answer) {

        if (operations.contains("e")) { // Checks for exponent denoted by symbol 'e'

            for (int i = 0; i < operations.size(); i++) {

                // Gets numbers on both sides of the operation to do calculation
                // The numbers to be calculated will always precede and succeed the operation
                // Hence accessing them by operation index

                if (operations.get(i).equals("e")) {
                    answer = Math.pow(numbers.get(i - 1),
                            numbers.get(i));
                    operations.remove(i);
                    numbers.remove(i);
                    numbers.set(i - 1, answer);

                    // Recurses to ensure all instances of the operaiton are done before
                    // following the next order of operation

                    if (operations.size() != 1)
                        return doEquation(numbers, operations, answer);
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
                    answer = numbers.get(i - 1)
                            * numbers.get(i);
                    operations.remove(i);
                    numbers.remove(i);
                    numbers.set(i - 1, answer);

                    if (operations.size() != 1)
                        return doEquation(numbers, operations, answer);

                } else if (operations.get(i).equals("/")) {

                    answer = numbers.get(i - 1)
                            / numbers.get(i);

                    operations.remove(i);
                    numbers.remove(i);
                    numbers.set(i - 1, answer);

                    if (operations.size() != 1)
                        return doEquation(numbers, operations, answer);
                }
            }
        }

        if (operations.contains("+") || operations.contains("-")) {

            for (int i = 0; i < operations.size(); i++) {

                if (operations.get(i).equals("+")) {

                    answer = numbers.get(i - 1)
                            + numbers.get(i);

                    operations.remove(i);
                    numbers.remove(i);
                    numbers.set(i - 1, answer);

                    if (operations.size() != 1)
                        return doEquation(numbers, operations, answer);

                } else if (operations.get(i).equals("-")) {

                    answer = numbers.get(i - 1)
                            - numbers.get(i);

                    operations.remove(i);
                    numbers.remove(i);
                    numbers.set(i - 1, answer);

                    if (operations.size() != 1)
                        return doEquation(numbers, operations, answer);
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
