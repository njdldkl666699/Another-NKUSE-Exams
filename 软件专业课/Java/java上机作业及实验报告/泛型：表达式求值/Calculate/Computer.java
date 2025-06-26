package Calculate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Computer {
    public static ArrayList<Character> findOperator(String input)
    {
        ArrayList<Character> operators = new ArrayList<>();
        String[] output=input.split("[^-+*/%()]");
        Arrays.stream(output).forEach(x-> {
            if(!x.isEmpty())
            {
                for (int i = 0; i < x.length(); i++) {
                    operators.add(x.charAt(i));
                }
            }
        });
        return operators;
    }
    public static LinkedList<Object> findNUm(String input)
    {
        LinkedList<Object> nums = new LinkedList<>();
        String[] output=input.split("[-+*/%()=]");
        Arrays.stream(output).forEach(x->{
            x=x.replaceAll(" ","");
            if(!x.isEmpty()){
            if(x.contains(".")) nums.add(Float.parseFloat(x));
            else nums.add(Integer.parseInt(x));
        }});
        return nums;
    }
    public static void calculate(LinkedList<Object> nums,Character operator)
    {
        Object num1 = nums.removeFirst();
        Object num2 = nums.removeFirst();
        if(num1 instanceof Integer && num2 instanceof Integer)
        {
            nums.addFirst(calculateNum((Integer) num1,(Integer) num2,operator));
        }
        else
        {
            nums.addFirst(calculateNum(Float.parseFloat(num1.toString()),Float.parseFloat(num2.toString()),operator));
        }
    }
    public static Integer calculateNum(Integer num2,Integer num1,Character operator)
    {
        switch(operator){
            case '+':{return num1+num2;}
            case '-':{return num1-num2;}
            case '*':{return num1*num2;}
            case '/':{return num1/num2;}
            case '%':{return num1%num2;}
        }
        return 0;
    }
    public static Float calculateNum(Float num2,Float num1,Character operator)
    {
        switch(operator){
            case '+':{return num1+num2;}
            case '-':{return num1-num2;}
            case '*':{return num1*num2;}
            case '/':{return num1/num2;}
            case '%':{return num1%num2;}
        }
        return Float.valueOf(0);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        ArrayList<Character> operators = findOperator(input);
        LinkedList<Object> Nums = findNUm(input);
        LinkedList<Object> nums = new LinkedList<>();
        LinkedList<Character> Operators=new LinkedList<>();
        int j=0;
        nums.addFirst(Nums.get(j++));
        for (int i = 0; i < operators.size(); i++) {
            if(operators.get(i)=='(') {Operators.addFirst('(');}
            else if(operators.get(i)==')') {
                if(!Operators.isEmpty())
                {
                    while(Operators.getFirst()!='('){
                    calculate(nums,Operators.removeFirst());
                    }
                }
                Operators.removeFirst();
            }
            else if(operators.get(i)=='+' ||operators.get(i)=='-')
            {
                if(!Operators.isEmpty()){
                    while(!Operators.isEmpty()&&Operators.getFirst()!='('){
                        calculate(nums,Operators.removeFirst());
                    }
                }
                nums.addFirst(Nums.get(j++));
                Operators.addFirst(operators.get(i));
            }
            else if(operators.get(i)=='*' ||operators.get(i)=='/'||operators.get(i)=='%')
            {
                if(!Operators.isEmpty()){
                    while(!Operators.isEmpty()&&Operators.getFirst()!='('&&Operators.getFirst()!='+'&&Operators.getFirst()!='-'){
                        calculate(nums,Operators.removeFirst());
                    }
                }
                nums.addFirst(Nums.get(j++));
                Operators.addFirst(operators.get(i));
            }
        }
        while(!Operators.isEmpty()){
            calculate(nums,Operators.removeFirst());
        }
        System.out.println(nums.get(0));
    }
}
