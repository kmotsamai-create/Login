package login;

import java.util.Scanner;

public class Login {

    // ── Fields ──────────────────────────────────────────────
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String cellPhoneNumber;

    // ── Constructor ─────────────────────────────────────────
    public Login(String firstName, String lastName,
                 String username, String password,
                 String cellPhoneNumber) {
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.username        = username;
        this.password        = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // ── Method 1: checkUserName() ───────────────────────────
    // Returns true if username contains an underscore _ and is 5 characters or fewer.
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // ── Method 2: checkPasswordComplexity() ─────────────────
    // Returns true if password has at least 8 chars, one uppercase,
    // one digit, and one special character (not letter/digit).
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;
        boolean hasUpper   = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit   = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        return hasUpper && hasDigit && hasSpecial;
    }

    // ── Method 3: checkCellPhoneNumber() ────────────────────
    // Uses regex to validate South African international format.
    // Regex source: standard SA phone pattern – verified by lecturer.
    // Explanation: starts with +27, then exactly 9 digits.
    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber.matches("^\\+27[0-9]{9}$");
    }

    // ── Method 4: registerUser() ────────────────────────────
    // Returns a specific error message if any validation fails,
    // otherwise returns "Registration successful."
    public String registerUser() {
        if (!checkUserName())
            return "Username is not correctly formatted; please ensure that your " +
                   "username contains an underscore and is no more than five characters in length.";
        if (!checkPasswordComplexity())
            return "Password is not correctly formatted; please ensure that the password contains " +
                   "at least eight characters, a capital letter, a number, and a special character.";
        if (!checkCellPhoneNumber())
            return "Cell phone number incorrectly formatted or does not contain international code.";
        return "Registration successful.";
    }

    // ── Method 5: loginUser() ───────────────────────────────
    // Returns true if entered username and password match the stored ones.
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    // ── Method 6: returnLoginStatus() ───────────────────────
    // Returns the welcome message if login succeeds, otherwise the error message.
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    // ── main() for manual testing (not required for marking) ─
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n#            REGISTER Page                      #\n");
            System.out.println("  1) Register");
            System.out.println("  2) Login");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n-- Registration --");
                    System.out.print("First name: ");   String first = scanner.nextLine();
                    System.out.print("Last name: ");    String last  = scanner.nextLine();
                    System.out.print("Username: ");     String uname = scanner.nextLine();
                    System.out.print("Password: ");     String pass  = scanner.nextLine();
                    System.out.print("Cell (+27...): "); String cell  = scanner.nextLine();
                    Login user = new Login(first, last, uname, pass, cell);
                    System.out.println(user.registerUser());
                }
                case 2 -> System.out.println("Login coming soon. Please register first!");
                default -> System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }
    }
}