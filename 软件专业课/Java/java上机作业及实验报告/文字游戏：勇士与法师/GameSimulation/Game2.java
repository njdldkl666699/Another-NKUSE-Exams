package GameSimulation;

public class Game2 extends Game{
    Actor player1;
    Actor player2;
    public Game2(String player1,String player2){
        switch(player1){
            case"AssAssIn": this.player1=new AssAssIn();break;
            case"Master":this.player1=new Master();break;
            case"Warrior":this.player1=new Warrior();break;
            case"BloodDelve":this.player1=new BloodDelve();break;
        }
        switch(player2){
            case"AssAssIn": this.player2=new AssAssIn();break;
            case"Master":this.player2=new Master();break;
            case"Warrior":this.player2=new Warrior();break;
            case"BloodDelve":this.player2=new BloodDelve();break;
        }
    }
    public void play()
    {
        Play("Attack","Attack");
    }
    public void RandPlay()
    {
        int WhoPiror= RandState.nextInt()%2;
        player1.setState(RandState.nextInt()%2);
        player2.setState(RandState.nextInt()%2);
        if(WhoPiror==1)
        {
            System.out.println(player1.action(player2));
            if(player2.ifdead()) return;
            System.out.println(player2.action(player1));
            if(player1.ifdead()) return;
        }
        else {
            System.out.println(player2.action(player1));
            if(player1.ifdead()) return;
            System.out.println(player1.action(player2));
            if(player2.ifdead()) return;
        }
        System.out.println(player1);
        System.out.println(player2);
        player1.reset();
        player2.reset();
    }
    public void Play(String play1,String play2)
    {
        int WhoPiror= RandState.nextInt()%2;
        player1.setState(play1);
        player2.setState(play2);
        if(player1.getState()==0) WhoPiror=1;
        if(player2.getState()==0) WhoPiror=0;
        if(WhoPiror==1)
        {
            System.out.println(player1.action(player2));
            if(player2.ifdead()) return;
            System.out.println(player2.action(player1));
            if(player1.ifdead()) return;
        }
        else {
            System.out.println(player2.action(player1));
            if(player1.ifdead()) return;
            System.out.println(player1.action(player2));
            if(player2.ifdead()) return;
        }
        System.out.println(player1);
        System.out.println(player2);
        player1.reset();
        player2.reset();
    }
}
