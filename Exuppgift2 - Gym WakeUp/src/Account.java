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
                System.out.println("Please enter your Personnummer in the following format YYMMDD-XXXX: ");
                personnummerAttempt = userInput.nextLine();
                personnummerControl = luhnAlgorithm(personnummerAttempt);
                if (personnummerControl == false) {
                    System.out.println("Incorrect personnummer, please try again");
                } else {
                    this.setPersonNummer(personnummerAttempt);
                }
            }
            while (personnummerControl == false);
            System.out.println("Please choose a password: ");
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

            System.out.println("Please login");
            System.out.println("Personnummer: ");
            personnummerAttempt = userInput.next();
            System.out.println("Password: ");
            passwordAttempt = userInput.next();
            if (personnummerAttempt.equals(this.getPersonNummer()) && (passwordAttempt.equals(this.getAccountPassword()))) {
                this.setStatus(true);
                System.out.println("Welcome!");
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
            // persnummertoChar.getNumericValue(i);
        }
        int tmp, n = 0;
        int sum = 0;
        //for-loop to multiply every other value of the intArray with 2 and every other value with 1
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
        sum = sum / 10; //Sum gets divided by 10 and stored in the variable

        /*if-statment to double check that the value is not zero, as this would make result being true,
        the user could for example enter 000000-0000 and get a true result without below if-statment*/
        if (sum == 0){
            result = false;
            return result;
        }
        // Nu dags att kolla ifall allt stämmer och att personnumret har rätt summa och är korrekt
        if (sum == Math.floor(sum)) {
            result = true;
        } else result = false;
        return result;
    }

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
