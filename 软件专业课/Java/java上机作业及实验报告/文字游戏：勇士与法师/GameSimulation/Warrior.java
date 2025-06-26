package GameSimulation;

public class Warrior extends Actor{
    public Warrior(){
        super("Warrior",300,0,60,10,2);
    }
    @Override
    String attackway(Actor a)
    {
        if(getBuff()) return this.name+" be freezed";
        ATK=oriATK+(300-blood)/2;
        if(this.character==a.character)
        {
            attack(a);
            attack(a);
        }
        else
        {
            attack(a);
        }
        return name+" attack "+a.name;
    }
}
