package java_extend_turket3D;


import java.util.Scanner;
public class test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String WinnerNumber=sc.next();
        Turket_3D player=new Turket_3D(WinnerNumber);
        while(true)
        {
            System.out.println("请输入投注方式及其金额");
            try{
                int value=0;
                String PlayWay=sc.next();
                if(PlayWay.equals("quit")) break;
                player.setWinnerNumber();
                System.out.println("中奖号为："+player.getWinnerNumber());
                if(!PlayWay.equals("tractor")) {String PlayerNum=sc.next();
                player.setUserNumber(PlayerNum);}
                value=player.getValue(PlayWay);
                System.out.println("您获得的奖金为"+value);
            }
            catch(RuntimeException e)
            {
                System.out.println(e.getMessage());
            }

        }
    }
}
