
// Import av några bibliotek som klassen använder.
import java.util.ArrayList;
import java.util.Scanner;

// Denna klass innehåller alla metoder för all typ av medlemshantering, t ex logga in/ut, skapa medlemskonto och tillhörande logik i respektive metoder

public class AccountDB {
    // Håller VEM (personnummer) som för närvarande är inloggad i aktuell instans
    private static String currentlyLoggedIn;

    // En boolean för att spara ATT sessionen är inloggad (används för menyhanteringen bl a)
    private static boolean status;

    // Arraylist som håller olika Accounts-klasser - olika medlemskonton  - jämför med vår databas över medlemmar, dock utan databas.. :-)
    private static ArrayList<Account> accountDatabase = new ArrayList<>();


    // Metod för att skapa nytt medlemskonto
    public void createAccount() throws Exception {
        // Deklaration av olika variabler som temporärt håller indata från användaren
        String password;
        String name;
        String requestedPersonnummer;
        boolean personnummerControl;   // Används som returvariabel från Lunaalgoritmen som kontrollerar personnumret
        if (this.getStatus()) {    // Kontroll att användaren är inloggad - då går det inte att bli medlem igen. Man kan skapa flera medlemmar men inte som inloggad.
            throw new Exception("Du är inloggad och är redan medlem. Vill du skapa ett nytt medlemskonto måste du logga ut först.");
        } else {
            Scanner userInput = new Scanner(System.in);
            do {     // Do-while-loop till dess användaren anger giltigt personnummer
                System.out.print("Vänligen ange personnummer i följande format ÅÅMMDD-XXXX: ");
                requestedPersonnummer = userInput.nextLine();
                personnummerControl = luhnAlgorithm(requestedPersonnummer);   // Här kontrolleras att personnummer är ett personnummer i korrekt format enligt luhnalgoritmen
                if (!personnummerControl) {  // Ifall fel personnummerinmatning
                    System.out.println("Felaktigt personnummer, vänligen försök igen");
                }
            } while (!personnummerControl);  // Vi loopar till dess användaren angett korrekt personnummer

            // Kontroll så att inte samma person blir medlem dubbelt
            if (personnummerCheck(requestedPersonnummer)) {
                throw new Exception("Personnumret är redan registrerat, vänligen logga in");
            }

            // Nu är personnumret OK så vi mer om namn, lösenord och att välja längd på medlemskapet
            System.out.print("Vänligen ange namn: ");
            name = userInput.nextLine();

            System.out.print("Vänligen ange lösenord: ");
            // password = requestedPersonnummer + userInput.nextLine();
            password = userInput.nextLine();

            System.out.println("");
            System.out.println("PRISLISTA:");
            System.out.println("Medlemskap - 100 SEK");
            System.out.println("1-2 månader – 400 SEK/månad");
            System.out.println("3-6 månader – 350 SEK/månad");
            System.out.println("7-12 månader – 300 SEK/månad");
            System.out.println("Längre än 12 månader – 250 SEK/månad");
            System.out.println();

            int membershipPrice;      // Används för hålla hela medlemskostnaden
            int membershiplength;     // Temporär variabel för att beräkna medlemskapskostnaden
            do {
                System.out.print("Hur många månader vill du vara medlem: ");
                membershiplength = userInput.nextInt();
                membershipPrice = calcMembership(membershiplength);   // Metod som beräknar priset för hela medlemskapet
                if (membershiplength <=0){  // Felhantering ifall medlemen väljer för kort medlemskap
                    System.out.println("Felaktigt antal månader, vänligen försök igen");
                }
            } while (membershiplength <= 0);
            System.out.print("Totalt att betala: ");
            System.out.print(membershipPrice);
            System.out.println(" SEK");
            System.out.printf("Välkommen som medlem %s!%n", name);
            // Här skapas nya kontot och sparas ner i en ny instans av klassen Account
            accountDatabase.add(new Account(requestedPersonnummer, password, name));
        }
    }

    // Metod för att kontrollera så att det inte finns dubletter av en ny användare som skall läggas upp
    public boolean personnummerCheck(String personnummer) {
        for (Account c : accountDatabase){
            if (c.getPersonnummer().equals(personnummer)){
                return true;
            }
        }
        return false;
    }

    // Metod för att beräkna totalkostnaden för medlemskapet. Indata är antal månder. Olika pris per månad beroende på medlemskapets längd.
    public int calcMembership(int months) {
        int price;
        int totalprice;
        int baseMembership = 100;   // Ett baspris som läggs på medlemskapet oavsett längd.
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
        totalprice = price * months + baseMembership;  // Totalpriset beräknas här.
        return totalprice;   // returneras här
    }

    // Metod för att logga in en användare
    public void loggaIn() {
        Scanner userInput = new Scanner(System.in);
        String personnummerAttempt;
        String passwordAttempt;

        if (this.getStatus()) {
            System.out.println("Du är redan inloggad");    // Man kan inte logga in om man redan är inloggad..
        } else {
            // Vi frågar efter personnummer (som används som användarnamn) samt tillhörande lösenord.
            System.out.println("Vänligen logga in");
            System.out.print("Personnummer: ");
            personnummerAttempt = userInput.next();
            System.out.print("Lösenord: ");
            passwordAttempt = userInput.next();

            // Här sker kontrollen mot vår Accounts-databas att användaren finns och att det är rätt lösenord angett
            for (Account c : accountDatabase){
                if (c.getPersonnummer().equals(personnummerAttempt) && c.getPassword().equals(passwordAttempt)){
                    setStatus(true, personnummerAttempt);
                    System.out.printf("Välkommen %s!%n", c.getName());
                }
            }
            if (!getStatus()) {
                System.out.println("Fel inloggning!");
            }
        }
    }

    // Metod för att logga ut
    public void loggaut() {
        if (this.getStatus()) {
            setStatus(false, "NULL");
            System.out.println("Du är nu utloggad.");
        } else {
            System.out.println("Felaktigt menyval.");    // Man kan inte välja att logga ut ifall man inte är inloggad. Dolt i menyn men kontroll för säkerhetsskull.
        }
    }

    // Metod för att verifiera att ett personnummer uppfyller kraven enligt luhnalgoritmen
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


    // Metod för att returnera ifall en användare är inloggad eller ej
    public static boolean getStatus() {
        return status;
    }

    // Metod för att sätta ifall en session med en användare är inloggad eller ej.
    public void setStatus(boolean status, String currentlyLoggedIn) {
        this.status = status;
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    // Get-metod för att kontrollera VEM som är inloggad
    public static String getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }
}
