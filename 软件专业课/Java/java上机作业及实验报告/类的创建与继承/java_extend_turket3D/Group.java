package java_extend_turket3D;
import java.util.HashMap;
import java.util.TreeSet;

public interface Group {
    public default int GroupValue(int[] WinnerNumber,int[] UserNumber)
    {
        TreeSet<Integer> Winner=new TreeSet<>();
        boolean three=false;
        int RepeatNumber=-1;
        for (int i = 0; i < 3; i++) {
            if(Winner.add(WinnerNumber[i])==false) {three=true; RepeatNumber=WinnerNumber[i];}
        }
        if(three)
        {
            boolean IfFindRepeat=false;
            for (int i = 0; i < 3; i++) {
                if(Winner.contains(UserNumber[i]))
                {
                    if(UserNumber[i]==RepeatNumber) {
                        if(!IfFindRepeat) IfFindRepeat = true;
                        else Winner.remove(UserNumber[i]);
                    }
                    else Winner.remove(UserNumber[i]);
                }
                else return 0;
            }
            return 346;
        }
        else
        {
            for (int i = 0; i < 3; i++) {
                if(Winner.contains(UserNumber[i])) Winner.remove(UserNumber[i]);
                else return 0;
            }
            return 173;
        }
    }
}
