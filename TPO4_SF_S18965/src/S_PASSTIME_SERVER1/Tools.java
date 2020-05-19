/**
 *
 *  @author Stachurski Filip S18965
 *
 */

package S_PASSTIME_SERVER1;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {

    public static Options createOptionsFromYaml(String fileName) throws Exception {

        Yaml yaml = new Yaml();
        InputStream input = new FileInputStream(fileName);
        Object settingsTmp = yaml.load(input);
        Map<String, Object> settings = (Map<String, Object>) settingsTmp;
        Options options = new Options((String)settings.get("host"),(int)settings.get("port"),(boolean)settings.get("concurMode"),
                (boolean)settings.get("showSendRes"),(HashMap<String,List<String>>)settings.get("clientsMap"));

        return options;

    }
}
