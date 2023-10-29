package to.toapp1;

import java.util.Scanner;

public class InputValidator {

    public static double getValidMonthlySalary(Scanner scanner) {
        double monthly_salary = 0.0;
        boolean validInput = false;

        while (!validInput) {
            try {
                monthly_salary = Double.parseDouble(scanner.nextLine());
                if (monthly_salary < 0) {
                    System.out.println("Monthly salary cannot be negative. Please enter a non-negative value:");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for monthly salary:");
            }
        }

        return monthly_salary;
    }

    public static Long getValidId(Scanner scanner) {
        Long id = 0L;
        boolean validInput = false;

        while (!validInput) {
            try {
                id = Long.parseLong(scanner.nextLine());
                if (id < 0) {
                    System.out.println("Invalid ID. Please enter a correct value:");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter a correct value:");
            }
        }



        return id;
    }

    public static String getValidString(Scanner scanner) {
        String string = "";
        boolean validInput = false;

        while (!validInput) {
            string = scanner.nextLine();
            if (string.matches("^[a-zA-Z]+$")) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter correct text, without numbers or special characters:");
            }
        }
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();

        return string;
    }

    public static double getValidUpdatedMonthlySalary(String str_monthly_salary, Scanner scanner) {
        double monthly_salary = 0.0;
        boolean validInput = false;

        try {
            monthly_salary = Double.parseDouble(str_monthly_salary);
            if (monthly_salary < 0) {
                System.out.println("Monthly salary cannot be negative. Please enter a non-negative value:");
            } else {
                validInput = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for monthly salary:");
        }

        while (!validInput) {
            try {
                monthly_salary = Double.parseDouble(scanner.nextLine());
                if (monthly_salary < 0) {
                    System.out.println("Monthly salary cannot be negative. Please enter a non-negative value:");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for monthly salary:");
            }
        }

        return monthly_salary;
    }

    public static String getValidUpdatedString(String input_string, Scanner scanner) {
        String string = input_string;
        boolean validInput = false;

        if (string.matches("^[a-zA-Z]+$")) {
            validInput = true;
        } else {
            System.out.println("Invalid input. Please enter correct text, without numbers or special characters:");
        }

        while (!validInput) {
            string = scanner.nextLine();
            if (string.matches("^[a-zA-Z]+$")) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter correct text, without numbers or special characters:");
            }
        }
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();

        return string;
    }

}
