import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner userInput = new Scanner(System.in);
        AccountDB database = new AccountDB();
        Passbokning passbokning = new Passbokning();
        int menyVal;
        do {
            printMeny(database.getStatus());
            menyVal = userInput.nextInt();
            userInput.nextLine();
            switch(menyVal) {
                case 1:
                    database.loggaIn();
                    break;
                case 2:
                    try {
                        database.createAccount();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                }
                    break;
                case 3:
                    passbokning.bokaPass();
                    break;
                case 4:
                    database.loggaut();
                    break;
                case 5:
                    System.out.println("Välkommen åter.");
                    break;
                default:
                    System.out.println("Fel menyval");
            }
        }
        while (menyVal != 5);
    }

    public static void printMeny(boolean status) {
        if (status) {
            System.out.println("3. Boka pass");
            System.out.println("4. Logga ut");
            System.out.println("5. Avsluta");
        } else {
            System.out.println("1. Logga in");
            System.out.println("2. Bli medlem");
            System.out.println("5. Avsluta");
        }
    }
}