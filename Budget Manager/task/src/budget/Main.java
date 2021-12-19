package budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here

        Kontroler kontroler = new Kontroler();
        kontroler.mainMenu();

        //System.out.println();
        System.out.println("Bye!");

        /*
        ********************************************
        **************** stage 1 *******************
        ********************************************

        Scanner scanner = new Scanner(System.in);
        List<String> lista = new ArrayList<>();
        String line ="";

        while( scanner.hasNextLine() )
        {
            line = new String( scanner.nextLine() );
            lista.add(line);
        }
        scanner.close();

        float sum = 0;

        for(String s:lista){
            System.out.println(s);
            String x = s.substring(s.lastIndexOf("$") + 1);
            //System.out.println(x);
            sum= sum+ Float.parseFloat(x);
        }

        System.out.println();
        System.out.println("Total: $"+sum);
    */

    }
}
