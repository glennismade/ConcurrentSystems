package eTicket;

/**
 * Created by glennhealy on 28/02/2018.
 */
import java.awt.Frame;
import java.awt.GridLayout;

import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveClosingFrame;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;
import org.jcsp.util.OverWriteOldestBuffer;

public class eTicket implements CSProcess{

    public static void main(String[] args) {
        final ActiveClosingFrame activeClosingFrame = new ActiveClosingFrame("eTicket");
        Frame frame = activeClosingFrame.getActiveFrame();

        // Button channels
        final One2OneChannel arrive_event = Channel.one2one(new OverWriteOldestBuffer(10));
        final One2OneChannel getmail_event = Channel.one2one();
        final One2OneChannel send_event = Channel.one2one();

        // Arrive/Depart channels
        final One2OneChannel arrival = Channel.one2one();
        final One2OneChannel internet[] = { Channel.one2one(), Channel.one2one() };
        final One2OneChannel dispatch_email = Channel.one2one();

        // Buttons
        ActiveButton btn_arrive = new ActiveButton(null, arrive_event.out(), "Arrive");
        ActiveButton btn_getmail = new ActiveButton(null, getmail_event.out(), "GetMail");
        final ActiveButton btn_send = new ActiveButton(null, send_event.out(), "Send");

        // Frame Layout
        frame.setLayout(new GridLayout(2, 4));
        frame.add(btn_arrive);
        frame.add(btn_getmail);
        frame.add(btn_send);
        frame.pack();
        frame.setVisible(true);

        // Processes
        EmailIn arrive = new EmailIn(arrive_event.in(), arrival.out());
        MailBag mailbag = new MailBag(arrival.in(), internet);
        GetMail get = new GetMail(getmail_event.in(), internet, send_event, dispatch_email.out());
        EmailOut dispatch = new EmailOut(dispatch_email.in());

        new Parallel(new CSProcess[] { activeClosingFrame, btn_arrive, btn_getmail, btn_send, arrive, mailbag, get, dispatch }).run();

    }

    public eTicket(){
        System.out.println("confirmed");

    }

    public void run(){

    }

}