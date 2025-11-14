package classes;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    public static int integer(Scanner intScanner) {
        int integer = 0;
        while (integer == 0) try {
            String input = intScanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("Please enter a valid number: ");
                continue;
            }

            integer = Integer.parseInt(input);
            if (integer <= 0) System.out.print("Your input should be greater than zero. Please try again: ");

            else break;
        } catch (NumberFormatException e) {
            System.out.print("Invalid input. Please enter a valid number: ");
        }
        return integer;
    }

    public static String string(Scanner stringScanner, String regEx) {
        while (true) {
            String input = stringScanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.print("Please enter a valid input: ");
                continue;
            }

            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(input);

            if (matcher.matches()) return input;

            System.out.print("Your input does not match the required format. Please try again: ");
        }
    }
}
