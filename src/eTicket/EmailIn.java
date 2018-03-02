package eTicket;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class EmailIn implements CSProcess{

    private AltingChannelInput button_pressed;
    private ChannelOutput place_in_mailqueue;

    public EmailIn(AltingChannelInput in, ChannelOutput out) {
        button_pressed = in;
        place_in_mailqueue = out;
    }

    //@Override
    public void run() {

    }

}

