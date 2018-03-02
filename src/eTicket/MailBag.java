package eTicket;

/**
 * Created by glennhealy on 28/02/2018.
 */
import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannel;

import java.util.ArrayList;

public class MailBag implements CSProcess {

    private int id = 0;
    private ArrayList<Integer> emails_inbox;
    private AltingChannelInput email_in;
    private AltingChannelInput email_req;
    private ChannelOutput send_inbox;

    public MailBag(AltingChannelInput in, One2OneChannel[] internet) {
        email_req = internet[0].in();
        send_inbox = internet[1].out();
        emails_inbox = new ArrayList<Integer>();
        email_in = in;
    }

    @Override
    public void run() {
        final Guard[] choices = {email_in, email_req };
        final Alternative choice = new Alternative(choices);
        while(true) {

            switch(choice.priSelect()) {
                case 0:
                    email_in.read();
                    emails_inbox.add(id++);
                    System.out.println("Mailbag: There are " + emails_inbox.size() + " emails");
                    break;
                case 1:
                    if(email_req.read() == "get-email") {
                        send_inbox.write(emails_inbox);
                        break;
                    }
            }

        }

    }

}