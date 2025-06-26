package java_extend_turket3D;
import java.util.TreeSet;

public interface Package extends Group,Single{
    public default int PacketValue(int[] WinnerNumber,int[]UserNumber) {

        int ifsingle=singleValue(WinnerNumber,UserNumber);
        int group=GroupValue(WinnerNumber,UserNumber);
        if(ifsingle!=0)
        {
            if(group==346) return 693;
            if(group==173) return 606;
        }
        else
        {
            if(group==346) return 173;
            else if(group==173) return 86;
        }
        return 0;
    }

}
