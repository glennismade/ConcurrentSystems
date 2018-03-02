package eTicket;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;

/**
 * Created by glennhealy on 28/02/2018.
 */

public class GetMail implements CSProcess {

    AltingChannelInput button_pressed;
    One2OneChannel[] con_info;
    ChannelOutput dispatch_email;

    // Button channels
    private One2OneChannel next_event;


    public GetMail(AltingChannelInput in, One2OneChannel[] internet, One2OneChannel next_event2, ChannelOutput dispatch_email2) {
        button_pressed = in;
        con_info = internet;
        this.next_event = next_event2;
        this.dispatch_email = dispatch_email2;
    }

    @Override
    public void run() {
        while (true) {
            if (button_pressed.read() == "GetMail") {
                System.out.println("GetMail pressed");
                emailUI uint = new emailUI(con_info, next_event, dispatch_email);
                new Parallel(new CSProcess[] { uint }).run();
            }
        }
    }

}