package Scheduler;

import java.util.Date;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    @Override
    public void run() {
        ejecutarAlgo();
    }

    public void ejecutarAlgo () {
        System.out.println("MyTimerTask se ejecutó a las " + new Date());


    }
}
