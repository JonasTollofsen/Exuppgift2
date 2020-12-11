import java.util.ArrayList;
import java.util.Scanner;

public class AccountDB {
    private static String currentlyLoggedIn;
    private static boolean status;
    private static ArrayList<Account> accountDatabase = new ArrayList<>();

    public void createAccount() throws Exception {
        ArrayList<Account> accountList = new ArrayList<>();
        accountList = accountDatabase;
        String password;
        String name;
        String requestedPersonnummer;
        boolean personnummerControl;

            if (this.getStatus()) {
                throw new Exception("Du är inloggad och är redan medlem.");
            } else {
                Scanner userInput = new Scanner(System.in);

            do {
                System.out.println("Vänligen ange personnummer i följande format ÅÅMMDD-XXXX: ");
                requestedPersonnummer = userInput.nextLine();
                personnummerControl = luhnAlgorithm(requestedPersonnummer);
                    if (!personnummerControl) {
                        System.out.println("Felaktigt personnummer, vänligen försök igen");
                    }
                } while (!personnummerControl);

            if (personnummerCheck(requestedPersonnummer)) {
                throw new Exception("Personnumret är redan registrerat, vänligen logga in");
            }

            System.out.println("Vänligen ange namn: ");
            name = userInput.nextLine();

            System.out.println("Vänligen ange lösenord: ");
            password = requestedPersonnummer + userInput.nextLine();

            System.out.println("");
            System.out.println("PRISLISTA:");
            System.out.println("Medlemskap - 100 SEK");
            System.out.println("1-2 månader – 400 SEK/månad");
            System.out.println("3-6 månader – 350 SEK/månad");
            System.out.println("7-12 månader – 300 SEK/månad");
            System.out.println("Längre än 12 månader – 250 SEK/månad");
            System.out.println();

            int membershipPrice;
            int membershiplength;
            do {
            System.out.println("Hur många månader vill du vara medlem: ");
            membershiplength = userInput.nextInt();
            membershipPrice = calcMembership(membershiplength);
            if (membershiplength <=0){
                System.out.println("Felaktigt antal månader, vänligen försök igen");
            }
            } while (membershiplength <= 0);
            System.out.print("Totalt att betala: ");
            System.out.print(membershipPrice);
            System.out.println(" SEK");
            System.out.printf("Välkommen som medlem %s!%n", name);

                accountList.add(new Account(requestedPersonnummer, password, name));
                }
            }

    public boolean personnummerCheck(String personnummer) {
        if (accountDatabase.contains(personnummer)) {
            return true;
        } else return false;
    }

    public int calcMembership(int months) {
        int price;
        int totalprice;
        int baseMembership = 100;
        if (months >= 1 && months <= 2) {
            price = 400;
        } else {
            if (months <= 6) {
                price = 350;
            } else {
                if (months > 12) {
                    price = 250;
                } else {
                    price = 300;
                }
            }
        }
        totalprice = price * months + baseMembership;
        return totalprice;
    }

    public void loggaIn() {
        Scanner userInput = new Scanner(System.in);
        String personnummerAttempt;
        String passwordAttempt;

        if (this.getStatus()) {
            System.out.println("Du är redan inloggad");
        } else {

            System.out.println("Vänligen logga in");
            System.out.println("Personnummer: ");
            personnummerAttempt = userInput.next();
            System.out.println("Lösenord: ");
            passwordAttempt = userInput.next();

            passwordAttempt = personnummerAttempt + passwordAttempt;

            for (Account c : accountDatabase){
                if (c.getPersonnummer().equals(personnummerAttempt) && c.getPassword().equals(passwordAttempt)){

                setStatus(true, personnummerAttempt);
                System.out.printf("Välkommen %s!%n", c.getName());
                }
            }
            if (!getStatus()){
                System.out.println("Fel inloggning!");
            }
        }
    }

    public void loggaut() {
        setStatus(false, "NULL");
        System.out.println("Du är utloggad");
    }

    public static boolean luhnAlgorithm(String persnummer) {
        boolean result; // Result-variabel skapas, denna kommer senare att returneras när metoden avslutas

        //if villkor som ser till att längden på det personnummer användaren har angivit passar, dvs. är 11 bokstäver
        if (persnummer.length() != 11) {
            result = false;
            return result; // om längden är felaktig returneras false och metoden avslutas
        }

        /*
        Tar bort strecket (-) eller + från strängen persnummer med metoden removeCharAt och lägger in
        resultatet i ett charArray
        */
        char[] persnummertoChar = removeCharAt(persnummer).toCharArray();

        /*
        initialiserar ett intArray som kommer att användas för att multiplicera värden i den andra kommande for-loop
        Pågrund av att ett charArray har använts kommer alla nummer att hanteras individuellt
        */
        int[] persnummerToInt = new int[persnummertoChar.length];

        // for-loop för att lägga in alla värden från charArray in till intArray som skapades ovan
        for (int i = 0; i < persnummerToInt.length; i++) {
            persnummerToInt[i] = (int) persnummertoChar[i] - 48;
            // persnummertoChar.getNumericValue(i);
        }

        int tmp, n = 0; //Temporära variabler som används i for-loopen nedan
        int sum = 0; //Variabel för att slå ihop summan av alla tal
        //for-loop för att multiplicera vartannat nummer med 2
        for (int i = 0; i < 10; i++) {
            if ((i % 2) == 0) {
                tmp = persnummerToInt[i] * 2;
            } else {
                tmp = persnummerToInt[i] * 1;
            }
            while (tmp > 0) {     // Loop för att ta ut digit sum av talet
                n = tmp % 10;
                sum = sum + n;
                tmp = tmp / 10;
            }
        }
        double sumWithDecimal;
        sumWithDecimal = (double) sum / 10; //Sum blir dividerat med 10 och lagrat i variabeln (Sum)

        /*
        if-villkor för att kolla så att värdet av sum-variabeln inte är noll, då detta skulle ge result = true,
        Exempelvis skulle användaren kunna ange 000000-0000 som ett äkta personnummer utan nedan if-villkor.
        */
        if (sumWithDecimal == 0) {
            result = false;
            return result;
        }

        /*
        if-villkor för att se till att värdet i sum-variabeln är lika stor som om man skulle skicka in variabeln i metoden
        "floor" i klassen "Math" (Math.floor). Sammafattat kollar detta efter att sum = ett heltal.
        */

        if (sumWithDecimal == Math.floor(sumWithDecimal)) {
            result = true;
        } else result = false;
        return result;
    }

    //Metod för att ta bort strecket(-) eller + från personnumret
    private static String removeCharAt(String s) {
        return s.substring(0, 6) + s.substring(6 + 1);
    }


    public static boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status, String currentlyLoggedIn) {
        this.status = status;
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    public static String getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }
}
