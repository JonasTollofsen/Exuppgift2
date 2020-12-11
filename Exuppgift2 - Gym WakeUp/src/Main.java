import java.util.Scanner;

// Vårt huvudprogram börjar och körs här.
public class Main {
    public static void main(String[] args) throws Exception {
        // Variabler deklareras och nya instanser av klasserna AccountDB och passbokning skapas.
        Scanner userInput = new Scanner(System.in);
        AccountDB database = new AccountDB();
        Passbokning passbokning = new Passbokning();
        int menyVal;  // Temporär variabel för menyval
        // Do-While-loop som hanterar hela menyvalssystemet. Loopar så länge användaren inte avslutar
        do {
            printMeny(database.getStatus());
            menyVal = userInput.nextInt();  // Användaren gör sitt menyval här
            userInput.nextLine(); // Fixar bug i java för user-inputs
            // Här avgörs vilket menyval som användaren gjort och respektive metod kallas
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

    // Metod för att skriva ut huvudmenyn. Den anpassar sig beroende på om användaren är inloggad eller inte.
    public static void printMeny(boolean status) {
        if (status) {
            System.out.println("3. Boka pass");
            System.out.println("4. Logga ut");
            System.out.println("5. Avsluta");
            System.out.print("Ange menyval: ");
        } else {
            System.out.println("1. Logga in");
            System.out.println("2. Bli medlem");
            System.out.println("5. Avsluta");
            System.out.print("Ange menyval: ");
        }
    }
}