import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Account account1 = new Account();
        String usernameAttempt;
        String passwordAttempt;

        System.out.println("Please enter a valid personnummer");
        account1.setPersonNummer(userInput.nextLine());
        //if (account1.getPersonNummer().equals())
        System.out.println("Please choose a username: ");
        account1.setAccountName(userInput.nextLine());

        System.out.println("Please choose a password: ");
        account1.setAccountPassword(userInput.next());


        System.out.println("Please login");
        System.out.println("Username: ");
        usernameAttempt = userInput.next();
        if (usernameAttempt.toLowerCase().equals(account1.getAccountName().toLowerCase())) {
            System.out.println("Password: ");
            passwordAttempt = userInput.next();
            if (passwordAttempt.equals(account1.getAccountPassword())) {
                System.out.println("Welcome " + account1.getAccountName() + "!");
            }
        }
    }

    public static void luhnAlgorithm(String personnummer) {

    }
}