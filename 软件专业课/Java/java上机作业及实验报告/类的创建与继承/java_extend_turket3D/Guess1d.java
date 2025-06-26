package java_extend_turket3D;
public interface Guess1d {
    public default int Guess1dValue(int[] WinnerNumber,int[] GuessNumber) {
        int GuessRepeat=0;
        for (int i = 0; i < 3; i++) {
            if(WinnerNumber[i]==GuessNumber[0]) GuessRepeat++;
        }
        switch (GuessRepeat) {
            case 0:return 0;
            case 1:return 2;
            case 2:return 12;
            case 3:return 230;
        }
        return 0;
    }
}
