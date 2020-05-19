/**
 * @author Stachurski Filip S18965
 */

package S_PASSTIME_SERVER1;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ClientTask extends FutureTask<String> {
    static String log="";
    static String resp="";

    public ClientTask(Callable<String> CallableString) {
        super(CallableString);
    }

    public static ClientTask create(Client c, List<String> reqs, boolean showSendRes) {
        return new ClientTask(new Callable<String>() {
            @Override
            public String call() throws Exception {
                c.connect();
                c.send("login " + c.id);
                reqs.forEach((string) -> {
                    resp = c.send(string);
                    if (showSendRes)
                        System.out.println(resp);
                });

                log+=c.send("bye and log transfer");
                return log;
            }
        });
    }
}