package java_extend_turket3D;
import jdk.jfr.StackTrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Turket_3D implements General,Group,Guess1d,Oned, Package,Single,Sum,Tractor,Towd{
    private int[] UserNumber;
    private int[] WinnerNumber;
    Random rand;
    private static Integer change(String input)
    {
        if("*".equals(input)) return -1;
        else return Integer.parseInt(input);
    }
    ArrayList<Integer> getWinnerNumber()
    {
        ArrayList<Integer> output = new ArrayList<>();
        for (int i : WinnerNumber) {
            output.add(i);
        }
        return output;
    }

    Turket_3D(String winnerNumber) {
        //List<String> list=Arrays.stream(winnerNumber.split("")).toList();
        //List<Integer> input= list.stream().map(Turket_3D::change).toList();
        String[] winnerNumbers = winnerNumber.split("");
        ArrayList<Integer> input=new ArrayList<>();
        for(String winner : winnerNumbers)
        {
            input.add(change(winner));
        }
        WinnerNumber=new int[input.size()];
        for (int i = 0; i < input.size(); i++) {
            WinnerNumber[i]=input.get(i);
        }
        rand = new Random();
    }
    public void setWinnerNumber()
    {
        WinnerNumber[0]=rand.nextInt(0,10);
        WinnerNumber[1]=rand.nextInt(0,10);
        WinnerNumber[2]=rand.nextInt(0,10);
    }

    /**
     *
     * @param userNumber
     */
    public void setUserNumber(String userNumber) {
        //List<String> list=Arrays.stream(userNumber.split("")).toList();
        //List<Integer> input= list.stream().map(Turket_3D::change).toList();
        String[] winnerNumbers = userNumber.split("");
        ArrayList<Integer> input=new ArrayList<>();
        for(String winner : winnerNumbers)
        {
            input.add(change(winner));
        }
        UserNumber=new int[input.size()];
        for (int i = 0; i < input.size(); i++) {
            UserNumber[i]=input.get(i);
        }
    }

    /**
     *
     * @param PlayWay
     * @return
     */
    int getValue(String PlayWay)  {
        if(UserNumber!=null) if(UserNumber.length>WinnerNumber.length)  throw new RuntimeException("请输入0-999之间的整数");;
        if(PlayWay.equals("single")) return singleValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("general")) return GeneralValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("tractor")) return TractorValue(WinnerNumber);
        if(PlayWay.equals("sum")) return SumValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("towd")) return TowdValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("group")) return GroupValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("package")) return PacketValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("guess1d")) return Guess1dValue(WinnerNumber,UserNumber);
        if(PlayWay.equals("oned")) return OnedValue(WinnerNumber,UserNumber);
        throw new RuntimeException("您输入的投注方式不存在，请重新输入");
        //return -1;
    }
}
