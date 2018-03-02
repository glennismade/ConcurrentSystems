package Booking;

import Carpark.Arrive;
import eTicket.eTicket;
import eTicket.EmailOut;
import org.jcsp.lang.*;
import org.jcsp.util.OverWriteOldestBuffer;

/**
 * Created by glennhealy on 01/03/2018.
 */
public class Booked implements CSProcess {


    private AltingChannelInput btn_pressed;
    private ChannelOutput sndTo_Contol;
    private AltingChannelInput confirm_booked;
    private int maxBook;

    public Booked(AltingChannelInput button_press, ChannelOutput send, AltingChannelInput confirm_booked) {
        btn_pressed = button_press;
        sndTo_Contol = send;
        this.confirm_booked = confirm_booked;
        maxBook = 0;
    }

    public Booked() {

        One2OneChannel internet = Channel.one2one(new OverWriteOldestBuffer(65645));
        One2OneChannel booking_send = Channel.one2one(new OverWriteOldestBuffer(64645));

        Server server = new Server(internet.in(), booking_send.out());
        Send send = new Send(booking_send.in());


        Customer customer  = new Customer(internet.out());
        Customer customer2 = new Customer(internet.out());
        Customer customer3 = new Customer(internet.out());
        Customer customer4 = new Customer(internet.out());
        Customer customer5 = new Customer(internet.out());

        //starting the process
        new Parallel(new CSProcess[] {server, send, customer, customer2, customer3, customer4, customer5}).run();

    }


    @Override
    public void run() {
        final Guard[] confirm_booking = {btn_pressed, confirm_booked};
        eTicket ticket = new eTicket();
      //  EmailOut message = new EmailOut();


            while (true) {
                if (btn_pressed.read() == "Book") {
                    sndTo_Contol.write(1);
    //                eTicket.EmailOut(sen)
                    maxBook++;
                new Parallel(new CSProcess[] {ticket}).run();
                System.out.println("Booking confirmed, you are customer number:" + maxBook);
            }
            //make max booking match max in carpark.
            if(maxBook > 10){
                maxBook = 0;
                System.out.println("please try again in a minute");
                break;
            }
        }
    }

}

