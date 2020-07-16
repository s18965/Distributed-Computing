package jms1;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import javax.naming.*;
import javax.jms.*;
import java.util.Hashtable;

public class Properties {

    Hashtable properties;
    Context ctx;
    ConnectionFactory factory;

    public Properties() {
        properties = new Hashtable<>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.exolab.jms.jndi.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");
        ctx = null;
        try {
            ctx = new InitialContext(properties);
            factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void createSession(String admDestName,Connection con){
        try {
            Destination dest = (Destination) ctx.lookup(admDestName);
            con = factory.createConnection();
            Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
