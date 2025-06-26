package java_extend_turket3D;
public interface Single {
    public default int singleValue(int[] WinnerNumber,int[] UserNumber) {
        for (int i = 0; i < 3; i++) {
            if(WinnerNumber[i] != UserNumber[i]) {return 0;}
        }
        return 1040;
    }
}
