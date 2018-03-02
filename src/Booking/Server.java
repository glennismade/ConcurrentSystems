package Booking;

import org.jcsp.lang.*;

/**
 * Created by glennhealy on 28/02/2018.
 */

public class Server implements CSProcess {

    ChannelInput port;
    ChannelOutput send_booking;

    public Server(ChannelInput number, ChannelOutput send) {
        port = number;
        send_booking = send;
    }

    @Override
    public void run() {

        while(true) {
            // listen on 'port' for incoming
            One2OneChannel temp = (One2OneChannel) port.read();
            new ProcessManager(new Session(temp, send_booking)).start();
        }
    }
}