package PlanificadorDeTareas;

import Password.*;

import java.util.Date;
import java.util.TimerTask;

public class TareaValidacion extends TimerTask {

    @Override
    public void run() {

        ValidatePasswordLength validatePasswordLength = new ValidatePasswordLength();

        System.out.println("MyTimerTask se ejecutó a las " + new Date());

        if (validatePasswordLength.validatePassword("Password"))
            System.out.println("Password special character... OK");
        else
            System.out.println("Password special character... NOT OK");


    }
}
