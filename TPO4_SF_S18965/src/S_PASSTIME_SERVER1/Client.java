/**
 *
 *  @author Stachurski Filip S18965
 *
 */

package S_PASSTIME_SERVER1;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class Client {
    private String host;
    private int port;
    String id;
    private SocketChannel socket;
    private StringBuilder log;
    private StringBuffer respBuffer;
    private  Charset charset;
    private ByteBuffer inBuffer;

    public Client(String host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
        log= new StringBuilder();
        respBuffer = new StringBuffer();
        charset = StandardCharsets.UTF_8;
        inBuffer = ByteBuffer.allocate(1024);
    }

    public String send(String req) {
        String clientResponse = null;
        respBuffer.setLength(0);
        CharBuffer cbuf = CharBuffer.wrap(req);
        ByteBuffer outBuf = charset.encode(cbuf);
        try {
            socket.write(outBuf);
            while (true) {
                inBuffer.clear();
                int readBytes = socket.read(inBuffer);
                if (readBytes > 0) {
                    inBuffer.flip();
                    cbuf = charset.decode(inBuffer);
                    respBuffer.append(cbuf);
                    respBuffer.append("\n");
                    break;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        clientResponse= respBuffer.toString();
        return clientResponse;
    }
    public void connect() {
        try {
            socket = SocketChannel.open();
            socket.configureBlocking(false);
            socket.connect(new InetSocketAddress(host, port));
            while (!socket.finishConnect()) {
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
