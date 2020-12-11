// Import av bibliotek som klassen använder för user inputs.
import java.util.Scanner;

public class Passbokning {
    // Deklaration av en array och initiering av samtliga värden i denna array som kommer att hålla personnummer på den medlem som har bokat ett visst pass på en viss rad och plats.
    private String[][][] passbokning = {{{"", "", ""}, {"", "", ""}, {"", "", ""}}, {{"", "", ""}, {"", "", ""}, {"", "", ""}}, {{"", "", ""}, {"", "", ""}, {"", "", ""}}};

    // Metod för att hantera bokning av gympass.
    public void bokaPass() {
        if (AccountDB.getStatus() == true) {  // Kontroll om användaren är inloggad, annars kan man inte boka pass!
            // Deklaration av olika variabler vi behöver
            Scanner userInput = new Scanner(System.in);
            int x, y, z = 0;  // x-y-z kommer att hantera olika aktiviteter, rader och platser i vår passbokningsarray som sparar vem som bokat vilket pass och vart.
            int ybokad, zbokad; // Extra y och z- variabler för de fall där användaren redan har en plats bokad och avser ändra bokningen
            boolean success = false;  // Flaggor att hantera om bokningen gått bra
            boolean available = false; // Flagga som hanterar fallet om alla platser på en viss aktivitet är uppbokad
            String val;
            String[] passtyp = new String[]{"Spinning", "Aerobics", "Yoga"};  // De pass som går att boka
            // Loopa till dess användaren lyckats boka ett pass eller valt att avsluta
            do {
                ybokad = -1;  // Initialiserar variabler för de fall där användaren redan har en plats bokad och avser ändra bokningen. Sätts till -1 för att samtidigt kunna använda som flagga om > 0 för ifall medlem har bokning eller ej. Måste sättas varje körning.
                zbokad = -1;  // Initialiserar variabler för de fall där användaren redan har en plats bokad och avser ändra bokningen. Sätts till -1 för att samtidigt kunna använda som flagga om > 0 för ifall medlem har bokning eller ej. Måste sättas varje körning.
                // For-loop som visar vilka pass man kan välja att boka
                for (int counter = 0; counter <= 2; counter++) {
                    System.out.printf("%d. %s%n", counter + 1, passtyp[counter]);
                }
                System.out.print("Vilket pass vill du boka eller välj 0 för att avsluta? ");
                x = userInput.nextInt() - 1;  // De val användaren gör måste vi ta -1 eftersom index i array börjar på 0
                if (x >= 0 && x < 3) {    // Kontroll att användaren gjort ett giltigt val
                    // Här nedan ritar vi upp vilka positioner i salen som finns och ifall de är lediga eller bokade. Detta med hjälp av en for-loop som kontrollerar mot passbokning-array
                    System.out.println();
                    System.out.println("        Plats A   Plats B   Plats C");
                    // Nestad for-loop som går igenom arrayen passbokning
                    for (y = 0; y < 3; y++) {
                        System.out.printf("Rad %d", y + 1);
                        for (z = 0; z < 3; z++) {
                            if (passbokning[x][y][z].isEmpty()) {
                                System.out.print("   Ledig  ");
                                available = true;  // Flagga för att kontrollera så att pass finns att boka
                            } else {
                                if (passbokning[x][y][z].equals(AccountDB.getCurrentlyLoggedIn())) {   // Kontroll ifall medlemmen redan har en bokad plats på detta pass
                                    System.out.print("   Din    ");
                                    // Spara ner positionen av denna bokning för senare referens ifall användaren vill byta.
                                    ybokad = y;
                                    zbokad = z;
                                    available = true;  // Flagga för att kontrollera så att pass finns att boka, används här för att möjliggöra avbokning ifall alla andra pass är bokade också.
                                } else {
                                    // Denna position var bokad så vi skriver ut det.
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
                        // Kontrollera input av vald position och splitta upp för att omvandla till indexnummer i vår array passbokning
                        if (val.length() == 2) {
                            if (val.substring(0, 1).equals("1") || val.substring(0, 1).equals("2") || val.substring(0, 1).equals("3")) {
                                y = Integer.parseInt(val.substring(0, 1)) - 1;
                                // Vi omvandlar plats A, B eller C till indexnummer i passbokning-array
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
                                        passbokning[x][y][z] = AccountDB.getCurrentlyLoggedIn();
                                        passbokning[x][ybokad][zbokad] = "";
                                        success = true;
                                        System.out.printf("Din bokade plats på %s har ändrats till rad %s, plats %s. Välkommen!%n%n", passtyp[x], val.substring(0, 1), val.substring(1, 2).toUpperCase());
                                    }
                                } else if (passbokning[x][y][z].equals("")) {
                                    // Nu är passet ledigt och redo att bokas av användaren
                                    passbokning[x][y][z] = AccountDB.getCurrentlyLoggedIn();
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
}
