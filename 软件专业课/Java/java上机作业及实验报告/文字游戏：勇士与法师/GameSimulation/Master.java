package GameSimulation;

public class Master extends Actor{
    public int FreezeValue=0;
    public Master(){
        super("Master",250,0,80,20,1);
    }
    @Override
    String attackway(Actor a){
        if(getBuff()){return this.name+" be freezed";}
        if(this.character==a.character){
            attack(a);
        }
        else {
            attack(a);
            attack(a);
        }
        FreezeValue++;
        if(FreezeValue==2){FreezeValue=0;a.buff=Buff.FREEZE;}
        return this.name+" attack "+a.name;
    }

}
