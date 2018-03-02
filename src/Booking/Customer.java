package Booking;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class Customer implements CSProcess {

    ChannelOutput address; // 'address' of the booking system
    One2OneChannel cus_addr; //customer address
    One2OneChannel socket[];

    public Customer(ChannelOutput addr) {
        address = addr;
        cus_addr = Channel.one2one();
    }

    @Override
    public void run() {


        ChannelOutput send = socket[0].out();
        ChannelInput receive = socket[1].in();

        // Send id for system and await a 'socket'
        address.write(cus_addr);
        AltingChannelInput wait = cus_addr.in();
        socket = (One2OneChannel[]) wait.read();


        System.out.println(this + " connected");


        // Communicate with session
        send.write("book");
        String response = (String) receive.read();
        if (response.contentEquals("booked")) {
            System.out.println(this + " booked");
        }
        send.write("Exit");
    }

}