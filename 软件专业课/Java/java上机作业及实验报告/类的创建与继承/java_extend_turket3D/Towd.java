package java_extend_turket3D;
public interface Towd {
    public default int TowdValue(int[] WinnerNumber,int[] UserNumber)
    {
        int TowdValue = 0;
        for (int i = 0; i < 3; i++) {
            if(UserNumber[i] != -1 &&WinnerNumber[i] == UserNumber[i]) TowdValue ++;
        }
        if(TowdValue==2) return 104;
        return 0;
    }
}
