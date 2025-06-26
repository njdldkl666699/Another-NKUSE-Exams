package GameSimulation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

       /* Game1 game1 = new Game1();
        game1.play();*/
        Scanner input = new Scanner(System.in);
        System.out.println("请输入两个玩家的身份");
        String player1 = input.next();
        String player2 = input.next();
        System.out.println("请输入玩法");
        String playway= input.next();
        Game2 game = new Game2(player1, player2);
        while(!game.player1.ifdead()&&!game.player2.ifdead()) {

            if(playway.equals("Random")) game.RandPlay();
            if(playway.equals("test")) game.play();
            if(playway.equals("Play")) {
                player1= input.next();
                player2= input.next();
                game.Play(player1,player2);
            }
        }
        if(game.player1.ifdead())
        {
            System.out.println(game.player2.name+" win");
        }
        else
        {
            System.out.println(game.player1.name+" win");
        }
        System.out.println(game.player1);
        System.out.println(game.player2);
    }
}