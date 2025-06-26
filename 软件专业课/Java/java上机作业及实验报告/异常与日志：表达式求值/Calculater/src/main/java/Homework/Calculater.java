package Homework;

import java.util.*;
/*
* 注：本类的设计在一定程度上违背了单一职责原则，所有带有split注解的方法都应该抽出到一个新类中
* 本类可以声明为工具类，即将类中所有的方法全部声明为static方法，因为本类中主要定义计算表达式的方法和处理输入的方法，并没有专门描述某个该类的实例化对象的内容
* */
public class Calculater {
    public static final String ERRORCONSTANT="88888888";//Main类中已经说明了。该变量名并不合适
    private static final Float ERRORFLOAT=Float.parseFloat(ERRORCONSTANT);//由于精度问题，转为float后再tostring的值和原本不一样，所以只能单独存一份float的

   /*
   * 该方法寻找表达式中所有的运算符，方法形参名变为expression更合适
   * */
    @split
    public ArrayList<Character> findOperator(String input)
    {
        ArrayList<Character> operators = new ArrayList<>();//用数组存运算符
        String[] output=input.split("[^-+*/%()]");//去除所有非运算符
        Arrays.stream(output).forEach(x-> {
            if(!x.isEmpty())
            {
                for (int i = 0; i < x.length(); i++) {//分割后的字符串可能存在+（这样的情况，所以要对字符串中的每个字符遍历
                    operators.add(x.charAt(i));
                }
            }
        });
        return operators;
    }
    /**
     * 该方法用于将表达式中的数字提取出来，当然，还会负责将变量转化为对应的值的工作
     * */
    @split
    public ArrayList<Object> findNum(String input, HashMap<String,Number> Nums) throws VarUndefineException, VarUnassignedException {
        ArrayList<Object> nums = new ArrayList<>();//注：这个地方的泛型用Number更合适，但由于上次用的是Object，在这里没改
        String[] output=input.split("[-+*/%()=?]");//将表达式中所有的运算符等去除
        for(String x:output)
        {
            if(x.isEmpty()) continue;
            if(x.contains(".")) {nums.add(Float.parseFloat(x));continue;}//如果某个字符串中存在小数点，则说明该字符串是个小数，因为变量中不允许存在小数点
            try{
                nums.add(Integer.parseInt(x));//如果没有小数点，就尝试将字符串变为整数
            }
            catch(NumberFormatException e){//若出现异常，则说明当前字符串是一个变量名
            if(Nums.containsKey(x))//如果变量名可以在变量和值的对应关系中找到，则说明变量已定义
            {
                //若变量的值是ERRORCONSTANT，则说明该变量未赋值，抛出异常
                if(ERRORCONSTANT.equals(Nums.get(x).toString())||ERRORFLOAT.toString().equals(Nums.get(x).toString())) throw new VarUnassignedException();
                nums.add(Nums.get(x));
            }
            //若找不到，说明变量未定义，抛出异常
            else throw new VarUndefineException();}
        }
        return nums;
    }
    /*
    * 该函数计算表达式中的单步操作，该操作会提取两个操作数和一个操作符
    * */
    public void calculate(LinkedList<Object> nums, Character operator) throws ErrorExpressionException
    {
        //如果操作数不够，抛出异常
        if(nums.size()<2) throw new ErrorExpressionException();
        Object num1 = nums.removeFirst();
        Object num2 = nums.removeFirst();
        //如果两个数都是Integer，用Integer的方式计算，否则用Float的方式计算
        if(num1 instanceof Integer && num2 instanceof Integer)
        {
            nums.addFirst(calculateNum((Integer) num1,(Integer) num2,operator));
        }
        else
        {
            nums.addFirst(calculateNum(Float.parseFloat(num1.toString()),Float.parseFloat(num2.toString()),operator));
        }
    }
    public Integer calculateNum(Integer num2,Integer num1,Character operator) throws ErrorExpressionException
    {
        switch(operator){
            case '+':{return num1+num2;}
            case '-':{return num1-num2;}
            case '*':{return num1*num2;}
            case '/':{return num1/num2;}
            case '%':{return num1%num2;}
            default: {throw new ErrorExpressionException();}
        }
    }
    public Float calculateNum(Float num2,Float num1,Character operator) throws ErrorExpressionException
    {
        switch(operator){
            case '+':{return num1+num2;}
            case '-':{return num1-num2;}
            case '*':{return num1*num2;}
            case '/':{return num1/num2;}
            case '%':{return num1%num2;}
            default: {throw new ErrorExpressionException();}
        }

    }
    //表达式求值的主体函数
    public Number calculate(String expression,HashMap<String,Number> varToNumber) throws ErrorExpressionException, VarUndefineException, VarUnassignedException {
        ArrayList<Object>Nums=findNum(expression,varToNumber);//将所有操作数提取出来
        ArrayList<Character> operators = findOperator(expression);//将所有操作符提取出来
        LinkedList<Object> nums = new LinkedList<>();//初始化存放数字和运算符的栈，注意这里的Object最好换成Number，此处使用Object依然是屎山遗留问题
        LinkedList<Character> Operators=new LinkedList<>();
        int j=0;
        nums.addFirst(Nums.get(j++));

        /*下面的是用栈完成表达式求值的过程，不多加赘述，需要注意的是，如果出现了数组超限异常（IndexOutOfBoundsException）则说明运算的时候操作数不足或者找不到左括号等情况
        * 这样的情况一定是因为表达式错误，故捕获到数组超限异常就向上抛出表达式错误异常
        */
        try{
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
        }catch(IndexOutOfBoundsException e){
            throw new ErrorExpressionException();
        }
        //将栈中剩余的操作符提取出来进行运算，如果捕获到异常说明表达式有误
        try{
            while(!Operators.isEmpty())
            {
                calculate(nums,Operators.removeFirst());
            }
        }catch(Exception e){
            throw new ErrorExpressionException();
        }
        if(nums.size()!=1) throw new ErrorExpressionException();
        return (Number)nums.get(0);
    }
}
