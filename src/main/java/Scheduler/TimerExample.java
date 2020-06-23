package Scheduler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {

    public static void main (String[] args) {

        Timer timer = new Timer();

        MyTimerTask task = new MyTimerTask();

      //  timer.schedule(task, 0, 5000);


    }

}