package Java_Reflect.myReflection;

import java.util.Random;

public class Student {
    public String stdname;
    public Integer stdcode;
    public Student(String stdname,Integer stdcode) {
        this.stdname=stdname;
        this.stdcode=stdcode;
    }
    public Student(String stdname) {
        this.stdname=stdname;
        this.stdcode=new Random().nextInt();
    }
    public void study(String lesson) {
        System.out.println(stdname+" learning "+lesson);
    }
    public void setName(String stdname) {
        this.stdname=stdname;
    }
    public void doSomeHelp(Student s) {
        System.out.println(stdname+" help "+s.stdname);
    }
}
