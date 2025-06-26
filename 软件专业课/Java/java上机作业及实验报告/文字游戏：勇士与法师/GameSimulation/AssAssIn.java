package GameSimulation;

public class AssAssIn extends Actor {
    AssAssIn()
    {
        super("AssAssIn",350,0,60,40,4);
    }
    @Override
    String attackway(Actor a) {
        if(getBuff()){return this.name+" be freezed";}
        attack(a);
        if(rand.nextBoolean()) {
            ATK*=2;
            attack(a);
        }
        return name+" attack "+a.name;
    }
}
