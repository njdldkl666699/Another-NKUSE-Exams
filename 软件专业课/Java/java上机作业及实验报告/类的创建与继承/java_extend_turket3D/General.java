package java_extend_turket3D;
public interface General {
    public default int GeneralValue(int[] WinnerNumber,int[] UserNumber)
    {
        int Repeat=0;
        for(int i=0;i<3;i++)
        {
            if(WinnerNumber[i]==UserNumber[i]) Repeat++;
        }
        if(Repeat==3) return 470;
        if(Repeat==2) return 21;
        return 0;

    }
}
