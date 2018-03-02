package Booking;

import org.jcsp.lang.*;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class Session implements CSProcess {

    private One2OneChannel con;
    private ChannelOutput booking_send;

    public Session(One2OneChannel temp, ChannelOutput send_booking) {
        con = temp;
        booking_send = send_booking;
    }

    @Override
    public void run() {
        System.out.println(this + " started");

        ChannelOutput out = con.out();
        One2OneChannel socket[] = { Channel.one2one(), Channel.one2one() };
        out.write(socket);

        ChannelInput rec = socket[0].in();
        ChannelOutput send = socket[1].out();

        String command = "";

        while (!command.contentEquals("quit")) {
            command = (String) rec.read();

            if (command.contentEquals("book")) {
                // make a booking
                System.out.println(this + " booking");
                send.write("booked");
            }
        }
        System.out.println(this + " sending booking");
        booking_send.write("email");
        System.out.println(this + " exiting");
    }

}