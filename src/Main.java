import menu.Menu;
import menu.TerminalMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Menu menu = new TerminalMenu(new Scanner(System.in));
        menu.run();
    }
}
