package Carpark;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.print.Book;

import Booking.Booking;
import org.jcsp.awt.ActiveButton;
import org.jcsp.awt.ActiveClosingFrame;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.Parallel;
import org.jcsp.util.OverWriteOldestBuffer;

/**
 * Created by glennhealy on 28/02/2018.
 */
public class CarPark extends Frame {

    CarPark(){

        final ActiveClosingFrame activeClosingFrame = new ActiveClosingFrame("Carpark");
        Frame frame = activeClosingFrame.getActiveFrame();

        // Button channels
        final One2OneChannel arrive_event = Channel.one2one();
        final One2OneChannel depart_event = Channel.one2one();


        // Arrive/Depart channels
        final One2OneChannel arrival = Channel.one2one(new OverWriteOldestBuffer(10));
        final One2OneChannel departure = Channel.one2one(new OverWriteOldestBuffer(10));


        // Arrive/Depart confirm channels
        final One2OneChannel arrival_confirm = Channel.one2one();
        final One2OneChannel departure_confirm = Channel.one2one();

        // Buttons
        ActiveButton arrive_active = new ActiveButton(null, arrive_event.out(), "Arrive");
        ActiveButton depart_active = new ActiveButton(null, depart_event.out(), "Depart");

        // Frame Layout
        frame.setLayout(new GridLayout(1, 2));
        frame.add(arrive_active);
        frame.add(depart_active);
       // frame.add(book_active);
        frame.pack();
        frame.setVisible(true);

        // Processes
        Arrive arrive = new Arrive(arrive_event.in(), arrival.out(), arrival_confirm.in());
        Controller control = new Controller(arrival.in(), departure.in(), arrival_confirm.out(), departure_confirm.out());
        Depart depart = new Depart(depart_event.in(), departure.out(), departure_confirm.in());
      //  Book book = new Book(book_event.in(), booking.out(), book_confirm.in());

        new Parallel(new CSProcess[] { activeClosingFrame, arrive_active, depart_active, arrive, control, depart }).run();
    }

    public static void main(String argv[]) {
        CarPark cp = new CarPark();
        Booking bk = new Booking();

    }

}





