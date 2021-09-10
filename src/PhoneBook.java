import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {
    public void Start(){
        readText();
    }
    public void readText(){
        Map<String, String> phoneBook = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        boolean continueWorking = true;
        printHelp();
        while(continueWorking){
            String userText = scanner.nextLine();
            switch(readCommand(userText)){
                case ("EXIT"): continueWorking = false; break;
                case ("LIST"): showAll(phoneBook); break;
                case ("HELP"): printHelp(); break;
                default: phoneBook = readMessage(phoneBook, userText);
            }
        }
        scanner.close();
    }

    private void showAll(Map<String, String> phoneBook) {
        for(Map.Entry<String, String> item : phoneBook.entrySet()){
            System.out.printf("phone: %s\tname: %s\n", item.getKey(), item.getValue());
        }
    }

    private Map<String, String> readMessage(Map<String, String> phoneBook, String userText) {
        if(isPhoneNumber(userText)){
            phoneBook = numberProcessing(phoneBook,userText);
        }
        else {
            phoneBook = nameProcessing(phoneBook,userText);
        }
        return phoneBook;
    }

    private Map<String, String> numberProcessing(Map<String, String> phoneBook, String userText){
        String number = getNumber(userText);
        if(phoneBook.containsKey(number)){
            System.out.printf("phone: %s\tname: %s\n", number, phoneBook.get(number));
        }
        else{
            if(saveRequest()) {
                System.out.println("Введете имя");
                String name = addName();
                phoneBook.put(number,name);
            }
        }
        return phoneBook;
    }

    private Map<String, String> nameProcessing(Map<String, String> phoneBook, String userText) {
        if(phoneBook.containsValue(userText)) {
            findNumber(phoneBook,userText);
        }else{
            if(saveRequest()){
                System.out.println("Введете номер");
                String number = addNumber();
                phoneBook.put(number, userText);
            }
        }
        return phoneBook;
    }

    private void findNumber(Map<String, String> phoneBook, String userText) {
        Collection<String> collection = phoneBook.keySet();
        for(String number: collection){
            Object obj = phoneBook.get(number);
            if(userText.equals(obj)){
                System.out.printf("phone: %s\tname: %s\n", number, userText);
            }
        }
    }

    private String addNumber() {
        Scanner scanner = new Scanner(System.in);
        String number = "";
        boolean invalidNumber = true;
        while(invalidNumber){
            number = scanner.nextLine();
            if(isPhoneNumber(number)){
                invalidNumber = false;
            }
        }
        return getNumber(number);
    }

    private String addName() {
        Scanner scanner = new Scanner(System.in);
        String name = "";
        boolean invalidName = true;
        while(invalidName) {
            name = scanner.nextLine();
            if(name.trim() != ""){
                invalidName = false;
            }
        }
        return name;
    }

    private boolean saveRequest() {
        System.out.println("вы хотите сохранить данный контакт?");
        System.out.println("введите Y для сохранения и N для отмены");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine().toLowerCase();
        if(answer.contains("y")){
            return true;
        }
        return false;
    }

    private boolean isPhoneNumber(String text){
        Pattern pattern = Pattern.compile("^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private String getNumber(String text){
        String number = text.replace(" ","");
        number = number.replace("-","");
        number = number.replace("_","");
        number = number.replace(":","");
        number = number.replace("=","");
        number = number.replace("+","");
        number = number.replace("(","");
        number = number.replace(")","");
        return number;
    }

    private String readCommand(String userText) {
        Pattern pattern = Pattern.compile("^[A-Z]+");
        Matcher matcher = pattern.matcher(userText);
        if(matcher.find()) {
            return matcher.group();
        }
        return userText;
    }

    private void printHelp() {
        System.out.println("LIST - вывод на экран телефонной кники");
        System.out.println("EXIT - выход из программы");
        System.out.println("HELP - вывод текущей подсказки");
        System.out.println("введите номер телефона для поиска абанента," +
                " или введите имя абанента для поиска номера телефона");
    }
}
