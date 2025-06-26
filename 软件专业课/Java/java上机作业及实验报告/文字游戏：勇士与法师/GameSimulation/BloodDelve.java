package GameSimulation;

public class BloodDelve extends Actor{
    boolean IfGetBlood=false;
    BloodDelve(){
        super("BloodDelve",400,0,50,40,3);
    }
    @Override
    String attackway(Actor a) {
        if(getBuff()) return this.name+" be freezed";
        if(this.character!=a.character) {a.buff=Buff.LOSEBLOOD;a.LoseBloodRound++;}
        attack(a);
        if(blood<200&&!IfGetBlood) {
            blood+=ATK;
            a.blood-=ATK;
            IfGetBlood=true;
            return name+"GetBlood";
        }
        return this.name+" attack "+a.name;
    }
}
