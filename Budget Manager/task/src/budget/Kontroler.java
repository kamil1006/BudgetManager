package budget;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Kontroler {

    Scanner scanner;
    String line;

    float income;
    float balance;
    List<Zakup> listaZakupow = new ArrayList<>();
    DecimalFormat df ;

    private static final String filepath="purchases.txt";
    private static final String filepath2="statusy.txt";

    //---------------------------------------------------
    public Kontroler() {
        income = 0;
        balance = 0;
        listaZakupow = new ArrayList<>();
        scanner = new Scanner(System.in);
        line ="";
        df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
    }
    //---------------------------------------------------
    public void mainMenu(){


        String line ="";

        while (!line.equals("0")){
            System.out.println("Choose your action:");
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("7) Analyze (Sort)");
            System.out.println("0) Exit");
            line = scanner.nextLine();
            System.out.println();
            switch (line){
                case "1":
                    addIncome();

                break;
                case "2":
                    chooseCategoryPurchase();
                    //addPurchase();

                    break;
                case "3":
                    if(!listaZakupow.isEmpty())
                        showCategoryPurchases();
                    else{
                        System.out.println("The purchase list is empty");
                        System.out.println();
                    }

                      //  showPuchases();

                    break;
                case "4":
                showBalance();
                break;
                case "5":
                    saveList();
                    saveStatusy();
                    break;
                case "6":
                    loadList();
                    loadStatusy();
                    break;
                case "7":
                    analyzeMenu();
                    break;
            }


        }



    }
    //---------------------------------------------------
    private void analyzeMenu() {
        String line ="";

        while (!line.equals("4")){
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");
            line = scanner.nextLine();
            System.out.println();
            switch (line){
                case "1":
                    if(!listaZakupow.isEmpty())
                        sortByMostExpensive();
                    else{
                        System.out.println("The purchase list is empty");
                        System.out.println();
                    }

                    break;
                case "2":
                        sortByType();

                    break;
                case "3":
                    sortByChoosenType();


                   break;

            }


        }
    }
    //---------------------------------------------------
    private void sortByChoosenType() {
        String line ="";
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        line = scanner.nextLine();
        System.out.println();
        List<Zakup> lista;
        switch (line){
            case "1":
                lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Food")).collect(Collectors.toList());
                lista = lista.stream().sorted((y,x)->Float.compare(x.getCena(),y.getCena())).collect(Collectors.toList());
                showPuchasesList(lista);
                break;
            case "2":
                lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Clothes")).collect(Collectors.toList());
                lista = lista.stream().sorted((y,x)->Float.compare(x.getCena(),y.getCena())).collect(Collectors.toList());
                showPuchasesList(lista);
                break;
            case "3":
                lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Entertainment")).collect(Collectors.toList());
                lista = lista.stream().sorted((y,x)->Float.compare(x.getCena(),y.getCena())).collect(Collectors.toList());
                showPuchasesList(lista);
                break;
            case "4":
                lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Other")).collect(Collectors.toList());
                showPuchasesList(lista);
                break;
        }


    }

    //---------------------------------------------------
    private void sortByType() {

        Map<String,Double> mapa = new HashMap<>();

        List<Zakup> lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Food")).collect(Collectors.toList());
        double sum = lista.stream().mapToDouble(x -> x.getCena()).sum();
        mapa.put("Food",sum);

        lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Entertainment")).collect(Collectors.toList());
        sum = lista.stream().mapToDouble(x -> x.getCena()).sum();
        mapa.put("Entertainment",sum);

        lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Clothes")).collect(Collectors.toList());
        sum = lista.stream().mapToDouble(x -> x.getCena()).sum();
        mapa.put("Clothes",sum);

        lista = listaZakupow.stream().filter(x -> x.kategoria.equals("Other")).collect(Collectors.toList());
        sum = lista.stream().mapToDouble(x -> x.getCena()).sum();
        mapa.put("Other",sum);

        Stream<Map.Entry<String, Double>> sorted = mapa.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        List<Map.Entry<String, Double>> collect = sorted.collect(Collectors.toList());

        System.out.println();
        System.out.println("Types:");

        if(!listaZakupow.isEmpty()){
            for( Map.Entry<String, Double> k:collect){
                System.out.println(k.getKey()+" - $"+df.format(k.getValue()));
            }
        }else {

        System.out.println("Food - $"+df.format(sum));
        System.out.println("Entertainment - $"+df.format(sum));
        System.out.println("Clothes - $"+df.format(sum));
        System.out.println("Other - $"+df.format(sum));

        }




        lista = listaZakupow;
        sum = lista.stream().mapToDouble(x -> x.getCena()).sum();
        System.out.println("Total sum: $"+df.format(sum));
        System.out.println();
    }

    //---------------------------------------------------
    private void sortByMostExpensive() {
        List<Zakup> temp = new ArrayList<>();
        //listaZakupow.stream().sorted().collect(Collectors.toList());
        temp = listaZakupow.stream().sorted((y,x)->Float.compare(x.getCena(),y.getCena())).collect(Collectors.toList());

        showPuchasesList(temp);
    }

    //---------------------------------------------------
    public class AllKomparator implements Comparator<Zakup>{

        @Override
        public int compare(Zakup zakup, Zakup t1) {
            return Float.compare(zakup.getCena(),t1.getCena());
        }
    }
    //---------------------------------------------------
    private void loadStatusy() {
        Statusy temp = new Statusy();
        try {
            FileInputStream fis = new FileInputStream(filepath2);
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (Statusy) ois.readObject();
            // Collections.reverse(lista);
            ois.close();
            balance = temp.balance;
            income = temp.income;

        }
        catch (IOException e) {
            // System.out.println("***catch ERROR***");
            // e.printStackTrace();

        }
        catch (ClassNotFoundException e) {
            System.out.println("***catch ERROR***");
            //  e.printStackTrace();
        }
        // End of loadDatabase
    }
    //---------------------------------------------------
    private void loadList() {
        List<Zakup> temp = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (List<Zakup>) ois.readObject();
            // Collections.reverse(lista);
            ois.close();
            listaZakupow = temp;
            System.out.println("Purchases were loaded!");
            System.out.println();

        }
        catch (IOException e) {
            // System.out.println("***catch ERROR***");
            // e.printStackTrace();

        }
        catch (ClassNotFoundException e) {
            System.out.println("***catch ERROR***");
            //  e.printStackTrace();
        }
     // End of loadDatabase
}
    //---------------------------------------------------
    private void saveStatusy() {
        try {
            Statusy temp = new Statusy();
            temp.balance = balance;
            temp.income = income;
            FileOutputStream fos = new FileOutputStream(filepath2);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(temp);
            oos.close();
            //boolean databaseIsSaved = true;


        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    //---------------------------------------------------
    private void saveList() {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listaZakupow);
            oos.close();
            //boolean databaseIsSaved = true;
            System.out.println("Purchases were saved!");
            System.out.println();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    //---------------------------------------------------
    private void showBalance() {


        System.out.println("Balance: $"+df.format(balance));

       // System.out.println("Balance: $"+balance);
        System.out.println();

    }
    //---------------------------------------------------
    private void showCategoryPurchases(){

        boolean czyExit = false;
        while (!czyExit){
            Zakup zakup = new Zakup();
            System.out.println("Choose the type of purchases");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) All");
            System.out.println("6) Back");
            String kategoria = scanner.nextLine();
            switch (kategoria){
                case "1":
                    showPuchasesKategory("Food");

                    break;
                case "2":

                    showPuchasesKategory("Clothes");

                    break;
                case "3":

                    showPuchasesKategory("Entertainment");

                    break;
                case "4":

                    showPuchasesKategory("Other");

                    break;
                case "5":

                    showPuchases();

                    break;


                case "6":
                    czyExit = true;
                    System.out.println();
                    break;
            }




        }



    }
    //---------------------------------------------------
    private void chooseCategoryPurchase(){
        boolean czyExit = false;
        while (!czyExit){
            Zakup zakup = new Zakup();
            System.out.println("Choose the type of purchase");
            System.out.println("1) Food");
            System.out.println("2) Clothes");
            System.out.println("3) Entertainment");
            System.out.println("4) Other");
            System.out.println("5) Back");
            String kategoria = scanner.nextLine();
            switch (kategoria){
                case "1":
                    zakup.kategoria = "Food";
                    addPurchase(zakup);
                    break;
                case "2":
                    zakup.kategoria = "Clothes";
                    addPurchase(zakup);
                    break;
                case "3":
                    zakup.kategoria = "Entertainment";
                    addPurchase(zakup);
                    break;
                case "4":
                    zakup.kategoria = "Other";
                    addPurchase(zakup);
                    break;
                case "5":
                    czyExit = true;
                    System.out.println();
                    break;
            }




        }
    }

    //---------------------------------------------------
    private void addPurchase(Zakup zakup) {
        //Zakup zakup = new Zakup();
        System.out.println();
        System.out.println("Enter purchase name:");
        zakup.nazwa = scanner.nextLine();
        System.out.println("Enter its price:");
        zakup.cena = Float.parseFloat(scanner.nextLine());
        System.out.println("Purchase was added!");
        System.out.println();
        balance = balance- zakup.cena;
        if(balance <0) balance = 0;
        listaZakupow.add(zakup);

    }
    //---------------------------------------------------
    private void showPuchasesKategory(String kategoria) {
        System.out.println();
        List<Zakup> collect = listaZakupow.stream().filter(x -> x.kategoria.equals(kategoria)).collect(Collectors.toList());
        if(collect.isEmpty()){
            System.out.println("The purchase list is empty");
            System.out.println();
        }
        else {
            float sum = 0;
            for(Zakup s:collect){
                System.out.println(s.nazwa+" $"+df.format(s.cena));
                //String x = s.nazwa.substring(s.nazwa.lastIndexOf("$") + 1);
                //System.out.println(x);
                // sum= sum+ Float.parseFloat(x);
                sum = sum+s.cena;
            }
            System.out.println("Total sum: $"+df.format(sum));
            System.out.println();
        }

    }
    //---------------------------------------------------


    //---------------------------------------------------
    private void showPuchases() {
        System.out.println();
        if(listaZakupow.isEmpty()){
            System.out.println("The purchase list is empty");
            System.out.println();
        }
        else {
            float sum = 0;
            for(Zakup s:listaZakupow){
                System.out.println(s.nazwa+" $"+df.format(s.cena));
                //String x = s.nazwa.substring(s.nazwa.lastIndexOf("$") + 1);
                //System.out.println(x);
               // sum= sum+ Float.parseFloat(x);
                sum = sum+s.cena;
            }
            System.out.println("Total sum: $"+df.format(sum));
            System.out.println();
        }


    }
    //---------------------------------------------------
    private void addIncome() {
        System.out.println("Enter income:");
        income = Float.parseFloat(scanner.nextLine());
        System.out.println("Income was added!");
        System.out.println();
        balance = balance+income;
    }
    //---------------------------------------------------
    private void showPuchasesList(List<Zakup> lista) {
        System.out.println();
        if(lista.isEmpty()){
            System.out.println("The purchase list is empty");
            System.out.println();
        }
        else {
            float sum = 0;
            for(Zakup s:lista){
                System.out.println(s.nazwa+" $"+df.format(s.cena));

                sum = sum+s.cena;
            }
            System.out.println("Total sum: $"+df.format(sum));
            System.out.println();
        }


    }

}
