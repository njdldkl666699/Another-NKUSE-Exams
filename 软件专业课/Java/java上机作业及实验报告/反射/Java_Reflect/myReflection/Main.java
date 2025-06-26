package Java_Reflect.myReflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public  HashMap<String,Student> Students;
    public  Class<Student> studentClass;
    Main()
    {
        studentClass=Student.class;
        Students=new HashMap<>();
    }
    public  void invoke(String method) throws WrongVarException, WrongMethodException, WrongStatementException ,WrongConstructorException,WrongVarException{
        if(method.contains("="))
        {
            String[] explainMethod=method.split("[= ]");
            if(method.contains("Student")) throw new WrongTypeException();
            Constructor(explainMethod);
        }
        else if(method.contains("."))
        {
            String[] explainMethod=method.split("\\.");
            if(!Students.containsKey(explainMethod[0])) throw new WrongVarException();
            invokeStudentMethod(explainMethod[1],Students.get(explainMethod[0]));
        }
        else throw new WrongStatementException();
    }

    private  void invokeStudentMethod(String method,Student student) throws WrongVarException, WrongMethodException, WrongConstructorException, WrongVarException{
        String[] explainMethod=method.split("[(),;]");
        ArrayList<Class> parametersType=new ArrayList<>();
        ArrayList<Object> parameters=new ArrayList<>();
        String methodName=explainMethod[0];
        explainMethod[0]="";
        transParameter(explainMethod,parametersType,parameters);
        Method method1=null;
        Class[] parameterTypes=parametersType.toArray(new Class[parametersType.size()]);
        Object[] parameter=parameters.toArray();
        try{
            method1=studentClass.getMethod(methodName,parameterTypes );
            method1.invoke(student,parameter);
        } catch (Exception e) {
            throw new WrongMethodException();
        }


    }
    public  void transParameter(String[] explainMethod,ArrayList<Class> parametersType,ArrayList<Object> parameters){
        if(explainMethod==null) return;
        for (String s : explainMethod) {
            if(s.isEmpty()) continue;
            if(s.contains("\""))
            {
                parameters.add(s.replace("\"",""));
                parametersType.add(String.class);
            }
            else
            {
                try{
                    parameters.add(Integer.parseInt(s));
                    parametersType.add(Integer.class);
                }
                catch(NumberFormatException e){
                    if(!Students.containsKey(s))
                    {
                        throw new WrongVarException();
                    }
                    else
                    {
                        parameters.add(Students.get(s));
                        parametersType.add(studentClass);
                    }
                }
            }
        }
    }
    public  void Constructor(String[] method) throws  WrongConstructorException, WrongStatementException{
        String[] explainMethod=null;
        for (String s : method) {
            if(s.contains("("))
            {
                explainMethod=s.split("[(),;]");
                break;
            }
        }
        ArrayList<Object> parameters=new ArrayList<>();
        ArrayList<Class> parametersType=new ArrayList<>();
        explainMethod[0]="";
        try{
            transParameter(explainMethod,parametersType,parameters);
        }
        catch(WrongVarException e){
            throw new WrongConstructorException();
        }
        Class[] parameterTypes=parametersType.toArray(new Class[parametersType.size()]);
        Object[] parameter=parameters.toArray();
        Student student=null;
        try {
            student =(Student) studentClass.getConstructor(parameterTypes).newInstance(parameter);
        } catch (Exception e) {
            throw new WrongConstructorException();
        }
        if(student!=null)
        {
            Students.put(method[1],student);
        }
    }
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        Main main=new Main();
        while(sc.hasNext()) {
            String method = sc.nextLine();
            try{
                main.invoke(method);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
