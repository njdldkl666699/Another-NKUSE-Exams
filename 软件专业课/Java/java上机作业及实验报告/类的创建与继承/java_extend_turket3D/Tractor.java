package java_extend_turket3D;
public interface Tractor {
    public default int TractorValue(int[] WinnerNumber)
    {
        boolean up=false;
        boolean down=false;
        for (int i = 0; i < 2; i++) {
            if (WinnerNumber[i] - WinnerNumber[i + 1]==1) up = true;
            else if (WinnerNumber[i] - WinnerNumber[i + 1]==-1) down = true;
            else return 0;
        }
        if(up==down) return 0;
        return 65;
    }
}
