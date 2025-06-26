package GameSimulation;

import java.util.Random;

public abstract class Actor implements CanPlay {
    String name;
    int blood;
    int state;
    int ATK;
    int DEF;
    int character;
    int oriATK;
    int oriDef;
    enum Buff{NONE,LOSEBLOOD,FREEZE};
    Buff buff;
    int LoseBloodRound=0;
    static int RoolNum=0;
    Random rand = new Random();
    public Actor(String name, int blood, int state, int ATK, int DEF,int chara) {
        this.name = name+RoolNum;
        this.blood = blood;
        this.state = state;
        this.ATK = ATK;
        this.DEF = DEF;
        this.character = chara;
        oriATK=ATK;
        oriDef=DEF;
        buff=Buff.NONE;
        RoolNum++;
    }

    public Actor() {

    }

    @Override
    public void attack(Actor a) {
        state=1;
        int LoseBlood = ATK-a.DEF;
        if(rand.nextBoolean()) LoseBlood*=2;
        if(LoseBlood>0) a.blood-= LoseBlood;
    }

    @Override
    public String defense() {
        state=0;
        DEF*=2;
        return name+" defensed";
    }
    @Override
    public String toString()
    {
        return name+" :Blood: "+blood+" ATK: "+ATK+" DEF: "+DEF+" oriATK: "+oriATK+" oriDef: "+oriDef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getState() {
        return state;
    }

    public void setState(String state) {
        if(state.equals("Attack")) this.state=1;
        else if(state.equals("Defense")) this.state=0;
    }
    public void setState(int state) {
        this.state = state;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }
    abstract String attackway(Actor a);
    public void setDEF(int DEF) {
        this.DEF = DEF;
    }
    String action(Actor a) {
        if(ifdead()) return a.name+" dead";
        if(state==1) {
            return attackway(a);
        }
        else return defense();
    }
    public boolean getBuff()
    {
        switch(buff)
        {
            case NONE:break;
            case LOSEBLOOD:blood-=20*LoseBloodRound;
                System.out.println(name+" lose blood:"+20*LoseBloodRound);break;
            case FREEZE:buff=Buff.NONE;return true;
        }
        return false;
    }
    void reset()
    {
        ATK=oriATK;
        DEF=oriDef;
    }
    boolean ifdead()
    {
        return blood<=0;
    }
}
