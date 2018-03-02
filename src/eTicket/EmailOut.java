package eTicket;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import sun.security.acl.WorldGroupImpl;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class EmailOut implements CSProcess {

    private ChannelInput send_Email;

    public EmailOut(AltingChannelInput ACI) {
        send_Email = ACI;

    }

    @Override
    public void run() {
        while (true) {
            send_Email.read();
            System.out.print(this + "send email");
        }
    }
}
