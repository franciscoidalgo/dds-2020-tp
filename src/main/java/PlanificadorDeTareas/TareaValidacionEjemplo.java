package PlanificadorDeTareas;

import Password.ValidatePasswordLength;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TareaValidacionEjemplo extends TimerTask {

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
