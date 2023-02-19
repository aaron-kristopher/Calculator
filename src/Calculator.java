import java.util.*;

public class App {
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

        String[] stringNumbers = notation.split("[e\\*\\/+-]");
        String[] operationsArray = notation.split("([0-9]+)");

        convertToNumbers(numbers, stringNumbers);
        Collections.addAll(operations, operationsArray);

        System.out.printf("Answer: %s", doEquation(numbers, operations, answer));
    }

    static String getNotation() {
        System.out.print("Enter notation: ");
        return scanner.nextLine();
    }

    static void convertToNumbers(ArrayList<Double> numbers, String[] stringNumbers) {
        for (String number : stringNumbers) {
            numbers.add((double) Integer.parseInt(number));
        }
    }

    static double doEquation(ArrayList<Double> numbers, ArrayList<String> operations, double answer) {

        if (operations.contains("e")) {

            for (int i = 0; i < operations.size(); i++) {

                if (operations.get(i).equals("e")) {
                    answer = Math.pow(numbers.get(i - 1),
                            numbers.get(i));
                    operations.remove(i);
                    numbers.remove(i);
                    numbers.set(i - 1, answer);
                }
            }
        }

        if (operations.contains("*") || operations.contains("/")) {

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
