import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        Account account1 = new Account();
        int menyVal;

        do {
            printMeny(account1.getStatus());
            menyVal = userInput.nextInt();
            userInput.nextLine();
            switch(menyVal) {
                case 1:
                    account1.loggaIn();
                    break;
                case 2:
                    account1.bliMedlem();
                    break;
                case 3:
                    account1.bokaPass();
                    break;
                case 4:
                    System.out.println("Välkommen åter.");
                    break;
                default:
                    System.out.println("Fel menyval");
                    // code block
            }
        }
        while (menyVal != 4);
    }

    public static void printMeny(boolean status) {
        if (status) {
            System.out.println("3. Boka pass");
            System.out.println("4. Avsluta");
        } else {
            System.out.println("1. Logga in");
            System.out.println("2. Bli medlem");
            System.out.println("4. Avsluta");
        }

    }


}