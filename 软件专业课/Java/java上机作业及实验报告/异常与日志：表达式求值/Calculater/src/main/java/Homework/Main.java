package Homework;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Slf4j
public class Main {
    @split
    /*
    * 所有带有split注解的方法代表该方法应该被放入一个新的类中，该类的职能为处理输入
    *该方法用来处理所有输入的变量定义及赋值语句
    * */
    public static HashMap<String,Number> handle(ArrayList<String> language) throws VarUndefineException
    {
        HashMap<String,Number> varToNums = new HashMap<>();//用来存放变量和值的对应关系，Number为Integer和Float的父类
        for(String str:language)//遍历传入的字符串数组
        {
            String[] words = str.split("[ =;]");//用等号，空格，分号分割
            /*
            * 接下来的逻辑是，先查看第一个字符串是什么，若是int或者float则说明这是一个变量声明语句。如果是形如int i=1；
            * 这样的语句，会被分割成三个字符串，而形如int i；这样的语句会被分割成两个字符串，
            * 所以只需要判断分割后的字符串数组长度就可知道该语句是否为变量赋值
            * 若该语句为变量赋值，则向Hashmap中添加对应的键值对，若未为变量赋值，
            * 则使用Calculater类中定义的字符串常量ERRORCONSTANT为变量赋初值，注：这里的常量命名并不好，应该命名为UNASSIGNEDVAR（未赋值变量）更好
            * 至于为什么不直接将未赋值的变量值定义为null，下面的逻辑会解释
            * */
            if(words[0].equals("int"))
            {
                if(words.length==3)
                    varToNums.put(words[1],Integer.parseInt(words[2]));
                else
                    varToNums.put(words[1],Integer.parseInt(Calculater.ERRORCONSTANT));
            }
            else if(words[0].equals("float")) {
                if(words.length==3)
                    varToNums.put(words[1], Float.parseFloat(words[2]));
                else varToNums.put(words[1],Float.parseFloat(Calculater.ERRORCONSTANT));
            }
            /*
            *若第一个字符串不为类型修饰符，则为类似i=3；这样的语句，如果在Hashmap中无法查出对应的键，
            * 则会抛出VarUndefineException（变量未定义异常），若可以查出对应的键，则会更新其中的值
            * （更新值的时候需要按照其对应的类型更新，用instanceof可以判断出定义变量的时候是什么类别，这就是不用null值赋给未赋值变量的原因，null无法记录对应的类型信息）
            * */
            else
            {
                if(varToNums.containsKey(words[0]))
                {
                    if(varToNums.get(words[0]) instanceof Float)
                        varToNums.replace(words[0],Float.parseFloat(words[1]));
                    else
                        varToNums.replace(words[0],Integer.parseInt(words[1]));
                }
                else
                    throw new VarUndefineException();
            }
        }
        return varToNums;
    }
    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>();//存放输入的字符串
        Scanner scanner = new Scanner(System.in);
        String str;
        StringBuilder logInput= new StringBuilder();
        str = scanner.nextLine();
        try{
            while(!str.isEmpty())
            {
                logInput.append("\n");//logInput拼接字符串，为了将输入的内容完整输出到log中而作，交oj的时候复制粘贴txt文件中的main函数即可
                logInput.append(str);
                input.add(str);
                str = scanner.nextLine();
            }
        }
        catch(Exception e){}//处理读不到nextline的异常
        //log.info(logInput.toString());
        String expression=input.removeLast();//读到的最后一个字符串是表达式，将该字符串提取出来，则input中剩余的字符串都是变量定义赋值语句
        Calculater calculater = new Calculater();
        HashMap<String,Number> varToNumber;
        try{
            varToNumber=handle(input);//将变量赋值语句交给handle函数，得到变量和值的对应关系
        } catch (VarUndefineException e) {//若抛出变量未定义异常，则记录到错误日志中
            log.error(logInput.toString()+"\n"+e.getMessage());
            return;
        }
        try{
            Number consequence=calculater.calculate(expression,varToNumber);//用consequence接收表达式求值的结果
            if(consequence instanceof Integer) log.info(logInput.toString()+"\n"+"consequence: "+consequence);//如果是integer，直接输出
            else log.info(String.format(logInput.toString()+"\n"+"consequence: %.2f",consequence));//如果是float，输出两位小数
        }
        catch (ErrorExpressionException e) {//分别处理三种异常
            log.error(logInput.toString()+"\n"+e.getMessage());
        } catch (VarUndefineException e) {
            log.error(logInput.toString()+"\n"+e.getMessage());
        } catch (VarUnassignedException e) {
            log.error(logInput.toString()+"\n"+e.getMessage());
        }
    }
}