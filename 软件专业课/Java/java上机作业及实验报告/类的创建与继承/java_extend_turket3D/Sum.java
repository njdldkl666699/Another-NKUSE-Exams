package java_extend_turket3D;

import java.util.ArrayList;
import java.util.Collections;

public interface Sum {
    public default int SumValue(int[] WinnerValue,int[] UserValue)
    {
        //int []winmoney=new int[14];
        ArrayList<Integer> winmoney=new ArrayList<>();
        int tureUserValue=0;
        for(int i=0;i<UserValue.length;i++)
        {
            tureUserValue+=UserValue[i]*(int)Math.pow(10,UserValue.length-i-1);
        }
        Collections.addAll(winmoney,1040,345,172,104,69,49,37,29,23,19,16,15,15,14);

        int sum=0;
        for(int i=0;i<3;i++)
        {
            sum+=WinnerValue[i];
        }
        if(sum==tureUserValue)
        {
            if(sum>=14)return winmoney.get(27-sum);
            else return winmoney.get(sum);
        }
        return 0;
    }
}
