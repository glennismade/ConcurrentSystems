package Booking;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class Send implements CSProcess {

    AltingChannelInput received;

    public Send(AltingChannelInput in) {
        received = in;
    }

    @Override
    public void run() {

        while(true) {
            String temp = (String) received.read();

            System.out.println(this + temp);
        }

    }

}