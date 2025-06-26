package java_extend_turket3D;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String WinnerNumber=sc.next();
        int num;
        num=sc.nextInt();
        int max=0;
        Turket_3D player=new Turket_3D(WinnerNumber);
        for (int i = 0; i < num; i++) {
            String play;
            play=sc.next();
            if(!play.equals("tractor")) {
                String GuessNumber = sc.next();
                player.setUserNumber(GuessNumber);
            }
            System.out.println(player.getValue(play));
            max=Math.max(max,player.getValue(play));
        }
        System.out.println(max);
    }
}