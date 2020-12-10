import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner userInput = new Scanner(System.in);
        Account account = new Account();
        int menyVal;
        do {
            printMeny(account.getStatus());
            menyVal = userInput.nextInt();
            userInput.nextLine();
            switch(menyVal) {
                case 1:
                    account.loggaIn();
                    break;
                case 2:
                    try {
                        account.bliMedlem();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                }
                    break;
                case 3:
                    account.bokaPass();
                    break;
                case 4:
                    account.loggaut();
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
            System.out.println("4. logga ut");
            System.out.println("5. avsluta");
        } else {
            System.out.println("1. Logga in");
            System.out.println("2. Bli medlem");
            System.out.println("5. Avsluta");
        }
    }
}