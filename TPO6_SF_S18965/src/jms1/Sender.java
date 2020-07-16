package jms1;

import javax.naming.*;
import javax.jms.*;
import java.util.Hashtable;

public class Sender {

    public static void main(String[] args) {

        Connection con = null;
        try {
            Properties properties = new Properties();

            String admDestName = args[0];

            Destination dest = (Destination) properties.ctx.lookup(admDestName);
            con = properties.factory.createConnection();
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer sender = ses.createProducer(dest);
            con.start();
            TextMessage msg = ses.createTextMessage();
            msg.setText(args[1]);
            sender.send(msg);
            System.out.println("Sender sent msg: " + args[1]);
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (JMSException exc) {
                    System.err.println(exc);
                }
            }
        }
        System.exit(0);
    }
}