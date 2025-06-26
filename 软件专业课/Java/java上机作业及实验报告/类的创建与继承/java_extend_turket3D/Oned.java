package java_extend_turket3D;
public interface Oned {
    public default int OnedValue(int[] WinnerNumber,int[]UserNumber) {
        for(int i=0;i<3;i++)
        {
            if(UserNumber[i]!=-1 && UserNumber[i]==WinnerNumber[i]) return 10;
        }
        return 0;
    }
}
