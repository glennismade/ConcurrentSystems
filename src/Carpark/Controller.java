package Carpark;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.Guard;

/**
 * Created by glennhealy on 21/02/2018.
 */
public class Controller implements CSProcess {


    private AltingChannelInput arrived;
    private AltingChannelInput departed;
    private ChannelOutput arrival_confirm;
    private ChannelOutput departure_confirm;
    private int spaces;
    private static final int MAX_SPACES = 10;

    public Controller(AltingChannelInput ar, AltingChannelInput dep, ChannelOutput arrive_conf, ChannelOutput depart_conf) {
        arrived = ar;
        departed = dep;
        arrival_confirm = arrive_conf;
        departure_confirm = depart_conf;
        spaces = MAX_SPACES; // max spaces
    }
    @Override
    public void run() {
        boolean full = false;
        final Guard[] choices = {departed, arrived};
        final Alternative choice = new Alternative(choices);

        while(true) {
            switch(choice.priSelect()) {
                case 0:
                    // Depart
                    departed.read();
                    if ( spaces < MAX_SPACES) {
                        departure_confirm.write(++spaces);
                    } else {
                        departure_confirm.write(-1);
                    }
                    break;
                case 1:
                    //Arrive
                    if ( spaces > 0) {
                        full = false;
                        arrived.read();
                        arrival_confirm.write(--spaces);
                    } else {
                        arrival_confirm.write(-1);
                        full = true;
                    }
                    break;
            }
        }

    }

}


