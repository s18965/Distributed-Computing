/**
 *
 *  @author Stachurski Filip S18965
 *
 */

package S_PASSTIME_SERVER1;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class Server {
    String host,serverLog="";
    int port;
    private ServerSocketChannel serverChannel = null;
    private Selector selector = null;
    boolean serverIsRunning=true;
    private Map<String,StringBuilder> clientLog;
    private Map<SocketChannel,String> clients;
    private SocketChannel socket;
    private Charset charset;
    private Thread thread;

    public Server(String host, int port) {
        this.host=host;
        this.port=port;
        this.clientLog=new HashMap<>();
        clients=new HashMap<>();
        charset=Charset.forName("UTF-8");
    }

    public void startServer() {
        thread = new Thread(() -> {
                    try {
                        serverChannel = ServerSocketChannel.open();
                        serverChannel.configureBlocking(false);
                        serverChannel.socket().bind(new InetSocketAddress(host, port));
                        selector = Selector.open();
                        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
                        serverIsRunning = true;

                        while(serverIsRunning){
                            try {
                                selector.select();
                                Set keys = selector.selectedKeys();
                                Iterator iter = keys.iterator();
                                while(iter.hasNext()) {
                                    SelectionKey key = (SelectionKey) iter.next();
                                    iter.remove();
                                    if (key.isAcceptable()) {
                                        socket = serverChannel.accept();
                                        socket.configureBlocking(false);
                                        socket.register(selector, SelectionKey.OP_READ);
                                        continue;
                                    }
                                    if (key.isReadable()) {
                                        SocketChannel cc = (SocketChannel) key.channel();
                                        serviceRequest(cc);
                                        continue;
                                    }
                                }
                            } catch(Exception exc) {
                                exc.printStackTrace();
                                continue;
                            }
                        }
                        serverIsRunning = false;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            });
        thread.start();
    }

    public void stopServer() {
                    serverIsRunning = false;
                    thread.interrupt();
        try {
            serverChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serviceRequest(SocketChannel socketChannel) {
        ByteBuffer bbuf=ByteBuffer.allocate(1024);
        StringBuilder reqString = new StringBuilder();

        if (!socketChannel.isOpen()) return;
        reqString.setLength(0);
        bbuf.clear();
        try {
            while (true) {
                int n = socketChannel.read(bbuf);
                if (n > 0) {
                    bbuf.flip();
                    CharBuffer cbuf = charset.decode(bbuf);
                    reqString.append(cbuf);
                    break;
                }
            }

            if (reqString.toString().contains("login")) {
                String client= reqString.toString().replace("login ","");
                clients.put(socketChannel,client);
                serverLog+=clients.get(socketChannel)+ " logged in at "+ LocalTime.now()+'\n';
                writeResp(socketChannel, "logged in");
                clientLog.put(client,new StringBuilder());
                clientLog.get(client).append("=== " + client+ " log start ===\nlogged in\n");
            }
            else if (reqString.toString().equals("bye")) {
                serverLog+=clients.get(socketChannel)+ " logged out at "+ LocalTime.now()+'\n';
                clientLog.get(clients.get(socketChannel)).append("logged out\n=== " +clients.get(socketChannel) + " log end ===\n");
                writeResp(socketChannel,"logged out");
            }
            else if (reqString.toString().contains("bye and log transfer")) {
                serverLog+=clients.get(socketChannel)+ " logged out at "+ LocalTime.now()+'\n';
                clientLog.get(clients.get(socketChannel)).append("logged out\n=== " +clients.get(socketChannel) + " log end ===");
                writeResp(socketChannel,clientLog.get(clients.get(socketChannel)).toString());
            }
            else { String[] dates= reqString.toString().split(" ");
                String Result = Time.passed(dates[0],dates[1]);
                serverLog+=clients.get(socketChannel) + " request at " + LocalTime.now() +": \""+ reqString+"\"\n";
                writeResp(socketChannel, "Request: "+reqString+"\nResult:\n"+Result);
                clientLog.get(clients.get(socketChannel)).append("Request: "+reqString+"\nResult:\n"+Result+"\n");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            clientLog.clear();
            clients.clear();
            try { socketChannel.close();
                socketChannel.socket().close();
            } catch (Exception e) {}
        }
    }
    String getServerLog(){
        return serverLog;
    }
    
    private void writeResp(SocketChannel socketChannel, String addMsg)
            throws IOException {
        ByteBuffer buf = charset.encode(CharBuffer.wrap(addMsg));
        socketChannel.write(buf);
    }
}
