import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Account {

    private static ArrayList<String> personnummerList = new ArrayList<String>();
    private static ArrayList<String> passwordList = new ArrayList<String>();
    private String[][][] passbokning = { { { "", "", ""}, { "", "", "" }, { "", "", "" } }, { { "", "", "" }, { "", "", "" }, { "", "", "" } }, { { "", "", "" }, { "", "", "" }, { "", "", "" } } };
    private boolean status;
    private String currentlyLoggedIn;
    private int membershipPrice;
    private int membershipLenght;

    public void bliMedlem() throws Exception {
        if (this.getStatus()) {
            throw new Exception("Du är inloggad och är redan medlem.");
        } else {
            Scanner userInput = new Scanner(System.in);
            String requestedPersonnummer;
            boolean personnummerControl;

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
            } else personnummerList.add(requestedPersonnummer);

            System.out.println("Vänligen ange lösenord: ");
            passwordList.add(requestedPersonnummer + userInput.nextLine());

            System.out.println("");
            System.out.println("PRISLISTA:");
            System.out.println("Medlemskap - 100 SEK");
            System.out.println("1-2 månader – 400 SEK/månad");
            System.out.println("3-6 månader – 350 SEK/månad");
            System.out.println("7-12 månader – 300 SEK/månad");
            System.out.println("Längre än 12 månader – 250 SEK/månad");
            System.out.println();

            System.out.println("Hur många månader vill du vara medlem: ");
            this.membershipLenght = userInput.nextInt();
            this.membershipPrice = calcMembership(this.membershipLenght);
            System.out.print("Totalt att betala: ");  // FIXA!
            System.out.print(this.membershipPrice);
            System.out.println(" SEK");
            System.out.println("Välkommen som medlem!");
        }
    }

    public boolean personnummerCheck(String username) {
        if (personnummerList.contains(username)) {
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
            if (personnummerList.contains(personnummerAttempt) && passwordList.contains(passwordAttempt)){
                this.setStatus(true, personnummerAttempt);
                System.out.println("Välkommen!");
            } else {
                System.out.println("Fel inloggning!");
            }
        }
    }

    public void loggaut() {
        setStatus(false, "NULL");
        System.out.println("Du är utloggad");
    }

    public void bokaPass() {
        if (this.getStatus()) {
            Scanner userInput = new Scanner(System.in);
            int x, y, z = 0;
            int ybokad, zbokad;
            boolean success = false;
            boolean available = false;
            String val;
            String[] passtyp = new String[]{"Spinning", "Aerobics", "Yoga"};
            // Loopa till dess användaren lyckats boka ett pass eller valt att avsluta
            do {
                ybokad = -1;  // Initialiserar variabler som skall hålla redan på om användaren har ett pass bokat redan. Sätts till -1 för att samtidigt kunna använda som flagga om > 0 för ifall medlem har bokning eller ej. Måste sättas varje körning.
                zbokad = -1;  // Initialiserar variabler som skall hålla redan på om användaren har ett pass bokat redan. Sätts till -1 för att samtidigt kunna använda som flagga om > 0 för ifall medlem har bokning eller ej. Måste sättas varje körning.
                for (int counter = 0; counter <= 2; counter++) {
                    System.out.printf("%d. %s%n", counter + 1, passtyp[counter]);
                }
                System.out.print("Vilket pass vill du boka eller välj 0 för att avsluta? ");
                x = userInput.nextInt() - 1;
                if (x >= 0 && x < 3) {    // Kontroll att användaren gjort ett giltigt val
                    System.out.println();
                    System.out.println("        Plats A   Plats B   Plats C");
                    for (y = 0; y < 3; y++) {
                        System.out.printf("Rad %d", y + 1);
                        for (z = 0; z < 3; z++) {
                            if (passbokning[x][y][z].isEmpty()) {
                                System.out.print("   Ledig  ");
                                available = true;  // Flagga för att kontrollera så att pass finns att boka
                            } else {
                                if (passbokning[x][y][z].equals(this.getPersonNummer())) {   // Kontroll ifall medlemmen redan har en bokad plats på detta pass
                                    System.out.print("   Din    ");
                                    // Spara ner positionen av denna bokning för senare referens ifall användaren vill byta.
                                    ybokad = y;
                                    zbokad = z;
                                    available = true;  // Flagga för att kontrollera så att pass finns att boka, används här för att möjliggöra avbokning ifall alla andra pass är bokade också.
                                } else{
                                    System.out.print("   Bokad  ");
                                }
                            }
                        }
                        System.out.println("");
                    }
                    if (available) { // Det finns pass att boka
                        if (ybokad >= 0) { // Kontroll ifall användaren har ett pass bokat, skriv då texten lite annorlunda.
                            System.out.print("Du är redan inbokad. Skriv 'avboka' om du vill avboka passet, eller skriv in ny rad och plats för att byta (t ex 2b): ");
                        } else {
                            System.out.print("Vilken rad och plats vill du boka i salen (t ex 2b): ");
                        }
                        val = userInput.nextLine();   // Dubbla Userinput pga bug i Java
                        val = userInput.nextLine();
                        // Kontrollera input och splitta upp
                        if (val.length() == 2) {
                            if (val.substring(0, 1).equals("1") || val.substring(0, 1).equals("2") || val.substring(0, 1).equals("3")) {
                                y = Integer.parseInt(val.substring(0, 1)) - 1;
                                switch (val.substring(1, 2).toLowerCase()) {
                                    case "a":
                                        z = 0;
                                        break;
                                    case "b":
                                        z = 1;
                                        break;
                                    case "c":
                                        z = 2;
                                }
                            }
                            // Slutkontroll av alla variabler
                            if (x <= 2 && x >= 0 && y <= 2 && y >= 0 && z <= 2 && z >= 0) {
                                if (ybokad >= 0 && zbokad >= 0) {
                                    // Användaren hade ett bokat pass redan.
                                    if (ybokad == y && zbokad == z) {
                                        // Användaren bokade samma plats denne redan har. Vi behöver inte spara ner något nytt.
                                        success = true;
                                        System.out.printf("Du har kvar din bokade plats på %s på rad %s, plats %s. Välkommen!%n%n", passtyp[x], val.substring(0, 1), val.substring(1, 2).toUpperCase());
                                    } else {
                                        // Användaren bokade en ny plats.
                                        // Vi byter bokningen:
                                        passbokning[x][y][z] = this.getPersonNummer();
                                        passbokning[x][ybokad][zbokad] = "";
                                        success = true;
                                        System.out.printf("Din bokade plats på %s har ändrats till rad %s, plats %s. Välkommen!%n%n", passtyp[x], val.substring(0, 1), val.substring(1, 2).toUpperCase());
                                    }

                                } else if (passbokning[x][y][z].equals("")) {
                                    // Nu är passet ledigt och redo att bokas av användaren
                                    passbokning[x][y][z] = this.getPersonNummer();
                                    success = true;
                                    System.out.printf("Du är nu bokad på %s på rad %s, plats %s. Välkommen!%n%n", passtyp[x], val.substring(0, 1), val.substring(1, 2).toUpperCase());
                                } else {
                                    System.out.println("Tyvärr platsen är bokad. Du måste välja en annan plats.");
                                }
                            } else {
                                System.out.println("Ogiltigt val");
                            }
                        } else if (val.equalsIgnoreCase("avboka") && ybokad >= 0) {
                            // Användaren har ett pass bokat och vill avboka sitt pass.
                            passbokning[x][ybokad][zbokad] = "";
                            success = true;
                            System.out.printf("Du har nu avbokat ditt pass på %s.%n%n", passtyp[x]);
                        }
                    } else {
                        System.out.println("Tyvärr! Det är fullbokat.");
                    }
                }
            }
            while (!success && x >= 0); // Loopa till dess användaren bokat ett pass eller valt att avsluta
        } else {
            System.out.println("Du måste logga in först");
        }
    }

    public static boolean luhnAlgorithm(String persnummer) {
        boolean result; // Result-variabel skapas, denna kommer senare att returneras när metoden avslutas

        //if villkor som ser till att längden på det personnummer användaren har angivit passar, dvs. är 11 bokstäver
        if (persnummer.length() != 11){
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
        sumWithDecimal = (double)sum / 10; //Sum blir dividerat med 10 och lagrat i variabeln (Sum)

        /*
        if-villkor för att kolla så att värdet av sum-variabeln inte är noll, då detta skulle ge result = true,
        Exempelvis skulle användaren kunna ange 000000-0000 som ett äkta personnummer utan nedan if-villkor.
        */
        if (sumWithDecimal == 0){
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


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status, String currentlyLoggedIn) {
        this.status = status;
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    public String getPersonNummer() {
        return this.currentlyLoggedIn;
    }

    /*public void setPersonNummer(String personNummer) {
        this.personNummer = personNummer;
    }


    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountPassword() {
        return accountPassword;
    }*/
}
