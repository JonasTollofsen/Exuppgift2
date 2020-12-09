import java.util.Scanner;

public class Account {
    private String accountPassword;
    private String personNummer;
    private boolean status;
    private int membershipPrice;
    private int membershipLenght;


    public void bliMedlem() {
        Scanner userInput = new Scanner(System.in);
        String personnummerAttempt;
        boolean personnummerControl;

        if (this.getStatus()) {
            System.out.println("Du är redan medlem");
        } else {
            do {
                System.out.println("Vänligen ange personnummer in följande format ÅÅMMDD-XXXX: ");
                personnummerAttempt = userInput.nextLine();
                personnummerControl = luhnAlgorithm(personnummerAttempt);
                if (personnummerControl == false) {
                    System.out.println("Felaktigt personnummer, vänligen försök igen");
                } else {
                    this.setPersonNummer(personnummerAttempt);
                }
            }
            while (personnummerControl == false);
            System.out.println("Vänligen ange lösenord: ");
            this.setAccountPassword(userInput.next());

            System.out.println("");
            System.out.println("PRISLISTA:");
            System.out.println("Medlemskap - 100 SEK");
            System.out.println("1-2 månader – 400 SEK/månad");
            System.out.println("3-6 månader – 350 SEK/månad");
            System.out.println("7-12 månader – 300 SEK/månad");
            System.out.println("Längre än 12 månader – 250 SEK/månad");

            System.out.println("");

            System.out.println("Hur många månader vill du vara medlem: ");
            this.membershipLenght = userInput.nextInt();
            this.membershipPrice = calcMembership(this.membershipLenght);
            System.out.print("Totalt att betala: ");  // FIXA!
            System.out.print(this.membershipPrice);
            System.out.println(" SEK");
            System.out.println("Välkommen som medlem!");
        }

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
            if (personnummerAttempt.equals(this.getPersonNummer()) && (passwordAttempt.equals(this.getAccountPassword()))) {
                this.setStatus(true);
                System.out.println("Välkommen!");
            } else {
                System.out.println("Fel inloggning!");
            }
        }
    }

    public void bokaPass() {
        if (this.getStatus()) {
            System.out.println("Boka pass här..");


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
        sum = sum / 10; //Sum blir dividerat med 10 och lagrat i variabeln (Sum)

        /*
        if-villkor för att kolla så att värdet av sum-variabeln inte är noll, då detta skulle ge result = true,
        Exempelvis skulle användaren kunna ange 000000-0000 som ett äkta personnummer utan nedan if-villkor.
        */
        if (sum == 0){
            result = false;
            return result;
        }

        /*
        if-villkor för att se till att värdet i sum-variabeln är lika stor som om man skulle skicka in variabeln i metoden
        "floor" i klassen "Math" (Math.floor). Sammafattat kollar detta efter att sum = ett heltal.
        */

        if (sum == Math.floor(sum)) {
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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPersonNummer() {
        return personNummer;
    }

    public void setPersonNummer(String personNummer) {
        this.personNummer = personNummer;
    }


    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountPassword() {
        return accountPassword;
    }
}
