package Booking;

import eTicket.EmailOut;
import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveClosingFrame;
import org.jcsp.lang.CSProcess;

/**
 * Created by glennhealy on 21/02/2018.
 */
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;
import org.jcsp.util.OverWriteOldestBuffer;

import java.awt.*;

public class Booking extends Frame {

    public Booking(){

        final ActiveClosingFrame activeClosingFrame2 = new ActiveClosingFrame("Booking");
        Frame booking_frame = activeClosingFrame2.getActiveFrame();

        //Button channels
        final One2OneChannel book_event = Channel.one2one();

        //Booking channels
        final  One2OneChannel booking = Channel.one2one(new OverWriteOldestBuffer(10));

        //confirming booking channel
        final One2OneChannel book_confirm = Channel.one2one();


        //buttons
        ActiveButton book_active = new ActiveButton(null, book_event.out(), "Book");



        //Frame
        booking_frame.setLayout(new GridLayout(2,2));
        booking_frame.add(book_active);
        booking_frame.pack();
        booking_frame.setVisible(true);

        //booking process
        Booked book = new Booked(book_event.in(), booking.out(), book_confirm.in());
//        EmailOut sndtickt = new EmailOut();

        One2OneChannel internet = Channel.one2one(new OverWriteOldestBuffer(65645));
        One2OneChannel booking_send = Channel.one2one(new OverWriteOldestBuffer(64645));
        Send send = new Send(booking_send.in());
        Customer customer  = new Customer(internet.out());

        new Parallel(new CSProcess[] {activeClosingFrame2, book, customer, send}).run();

    }



    public static void main(String[] args) {

        Booking bk = new Booking();


    }

}
