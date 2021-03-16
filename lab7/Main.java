ackage com.company;

import java.util.Scanner;

public class Main {
    /**
     * "Facade" method, receiving data to call Crawler.java main() method**/
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите URL и глубину поиска");
        String[] arg = in.nextLine().split(" ");
        Crawler.main(arg);
    }
}
