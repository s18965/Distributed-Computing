package jms1;

import javax.naming.*;
import javax.jms.*;
import java.util.Hashtable;

public class Receiver {

    public static void main(String[] args) {
        Connection con = null;
        try {
            Properties properties = new Properties();

            String admDestName = args[0];
            properties.createSession(admDestName,con);
            Destination dest = (Destination) properties.ctx.lookup(admDestName);
            con = properties.factory.createConnection();
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer receiver = ses.createConsumer(dest);

            con.start();
            System.out.println("Receiver started");
            Message msg = receiver.receive();
            if (msg instanceof TextMessage) {
                TextMessage text = (TextMessage) msg;
                System.out.println("Received: " + text.getText());
            } else if (msg != null) {
                System.out.println("Received non text message");
            }
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