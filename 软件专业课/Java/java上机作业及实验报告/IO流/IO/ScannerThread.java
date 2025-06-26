package IO;

import java.util.Scanner;

public class ScannerThread extends Thread{
    ScannerThread()
    {
        this.setDaemon(true);
    }
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(scanner.nextLine().equals("exit")){
                if(scanner.nextLine().equals("y")){break;}
            }
        }
    }
}
