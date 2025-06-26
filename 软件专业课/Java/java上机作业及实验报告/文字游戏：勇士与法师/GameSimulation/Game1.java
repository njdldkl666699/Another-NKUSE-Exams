package GameSimulation;

import java.util.Scanner;

public class Game1 extends Game {
    static Master master;
    static Warrior warrior;

    public void play() {
        master = new Master();
        warrior = new Warrior();
        Scanner sc = new Scanner(System.in);
        int player = 0;
        int whodead = 0;
        while (master.blood > 0 && warrior.blood > 0) {
            String Character = sc.next();
            String State = sc.next();
            if (Character.equals("Master")) {
                player = 1;
                master.setState(State);
                warrior.setState(RandState.nextInt() % 2);
            } else if (Character.equals("Warrior")) {
                player = 2;
                warrior.setState(State);
                master.setState(RandState.nextInt() % 2);
            }
            int whonext = RandState.nextInt() % 2;
            if (whonext == 1) {
                System.out.println(master.action(warrior));
                if (warrior.blood <= 0) {
                    whodead = 2;
                    break;
                }
                System.out.println(warrior.action(master));
                if (master.blood <= 0) {
                    whodead = 1;
                    break;
                }
            } else {
                System.out.println(warrior.action(master));
                if (master.blood <= 0) {
                    whodead = 1;
                    break;
                }
                System.out.println(master.action(warrior));
                if (warrior.blood <= 0) {
                    whodead = 2;
                    break;
                }
            }
            System.out.println(master);
            System.out.println(warrior);
            warrior.reset();
            master.reset();
        }
        if (whodead == player) System.out.println("Drew!");
        else System.out.println("You are the winner!");
        System.out.println(warrior);
        System.out.println(master);
    }
}
