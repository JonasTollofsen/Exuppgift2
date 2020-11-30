import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("test.txt"));

        List<String> test = new ArrayList<String>();
        while (input.hasNextLine()){
            test.add(input.nextLine());
            System.out.println();
        }
        for (int i = 0; i < test.size(); i++) {
            System.out.println("name: " + test.get(i));
        }
        }
    }