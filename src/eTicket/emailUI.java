package eTicket;

import java.util.ArrayList;
import org.jcsp.lang.Alternative;
import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannel;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class emailUI implements CSProcess{

    //event counter channel
    private One2OneChannel next_event;

    //email channels - declares emails
    private int Index;
    private ArrayList<Integer> buffer;
    private ChannelOutput send_Email;
    private ChannelOutput Check_email;
    private AltingChannelInput get_Email;



    //constructor
    public emailUI(One2OneChannel[] connection_inf, One2OneChannel next_event2, ChannelOutput dispatch_email2) {
        Check_email = connection_inf[0].out();
        get_Email = connection_inf[1].in();

        next_event = next_event2;

        this.send_Email = dispatch_email2;
        buffer = new ArrayList<Integer>();
        Index = 0;

    }

    @Override //run method for emailUI
    public void run() {

        final Guard[] options = { next_event.in()};
        final Alternative option = new Alternative(options);
        update_Inbox();
        while (true) {
            switch (option.priSelect()) {
                case 0:
                    next_event.in().read();
                    if (Index < buffer.size() - 1) {
                        Index++;
                        System.out.println("Next email" + Index);
                    }
                    break;
                case 1:
                    if (Index > 0) {
                        Index--;
                        System.out.println("Prev email" + Index);
                    }
                    break;
                case 2:
                    if (buffer.size() > 0) {
                        buffer.remove(Index);
                        System.out.println("Deleted email" + Index);
                    }
                    if (Index > buffer.size() - 1) {
                        Index--;
                    }
                    break;
                case 3:
                    // send email
                    send_Email.write("email");
            }
            update_Inbox();
            print_Inbox();

        }

    }

    public void print_Inbox() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < buffer.size(); i++) {
            if (Index == i)
            {
                output.append(buffer.get(i));
            } else {
                output.append(buffer.get(i));
            }
        }

        System.out.println(output);
    }

    public void update_Inbox() {
        Check_email.write("get-email");
        buffer = (ArrayList<Integer>) get_Email.read();
        System.out.println("Got" + buffer.size() + "emails");
    }



}