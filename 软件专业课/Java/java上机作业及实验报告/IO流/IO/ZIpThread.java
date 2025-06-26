package IO;

import java.io.IOException;

public class ZIpThread extends Thread{
    ZipCopyUtil zipCopyUtil;
    ZIpThread(ZipCopyUtil zipCopyUtil){
        this.zipCopyUtil=zipCopyUtil;
        this.setDaemon(true);
    }
    @Override
    public void run() {
        try {
            zipCopyUtil.startToZip(this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
