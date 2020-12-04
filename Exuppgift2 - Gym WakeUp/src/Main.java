import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Account account1 = new Account();
        String usernameAttempt;
        String passwordAttempt;
        String personnummerAttempt;
        boolean personnummerControl;



        do {
            System.out.println("Please enter your Personnummer in the following format YYMMDD-XXXX: ");
            personnummerAttempt = userInput.nextLine();
            personnummerControl = luhnAlgorithm(personnummerAttempt);
            if (personnummerControl == false){
                System.out.println("Incorrect personnummer, please try again");
            }
        }
        while (personnummerControl == false);

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

    public static boolean luhnAlgorithm(String persnummer) {
        boolean result; // result variable gets created, this will later be returned after the method finishes

        //if-statement that ensures that the length is fitting, if not, returns false and ends the method
        if (persnummer.length() != 11){
            result = false;
            return result;
        }
        char[] persnummertoChar = removeCharAt(persnummer).toCharArray();// Removing the hyphen or "+" from the String personnummer with the method removeCharAt and converting the result into a charArray
        int[] persnummerToInt = new int[persnummertoChar.length]; // initializing an intArray that will be used to multiply the values later on. Due to the use of charArray all numbers from the personnummer will be separate values in the array

        //for-loop to enter the charArray values into the intArray
        for (int i = 0; i < persnummerToInt.length; i++) {
            persnummerToInt[i] = (int) persnummertoChar[i] - 48;
        }

        //for-loop to multiply every other value of the intArray with 2 and every other value with 1
        for (int i = 0; i < 9; i++) {
            if ((i % 2) == 0) {
                persnummerToInt[i] = persnummerToInt[i] * 2;
            } //else persnummerToInt[i] = persnummerToInt[i] * 1; // not necessary to multiply with 1?
        }
        //Returning the multiplied intArray into a String, this needs to be done so we can later on split all double-digit values into separate values (example splitting 10 into 1 and 0)
        persnummer = String.join("", IntStream.of(persnummerToInt).mapToObj(String::valueOf).toArray(String[]::new)); //

        //storing the String value of the personnummer in a charArray, this splits all double-digit values into two separate values
        persnummertoChar = persnummer.toCharArray();

        //new intArray needs to be created to fit the length of the new values, as it may be to long to fit into the old array
        int[] control = new int[persnummertoChar.length];

        //for-loop to enter the charArray values into the intArray
        for (int i = 0; i < control.length; i++) {
            control[i] = (int) persnummertoChar[i] - 48;
        }
         double sum = 0; // sum variable initialized
        // for-loop used to sum up all the values in the newly created intArray (variable "control")
        for (int i : control) {
            sum += i;
        }
         sum = sum / 10; //Sum gets divided by 10 and stored in the variable

        /*if-statment to double check that the value is not zero, as this would make result being true,
        the user could for example enter 000000-0000 and get a true result without below if-statment*/
        if (sum == 0){
            result = false;
            return result;
        }
        if (sum == Math.floor(sum)) {
            result = true;
        } else result = false;

        return result;
    }

    private static String removeCharAt(String s) {
        return s.substring(0, 6) + s.substring(6 + 1);
    }
}