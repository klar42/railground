package railground;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import railground.model.Config;
import railground.model.Model;
import railground.model.ModelImpl;

public class Railground {

    private static final String DEFAULT_PROPERTIES = "Railground.properties";

    public static void main(String[] args) throws Exception {
        InputStream propsStream = Railground.class.getResourceAsStream(DEFAULT_PROPERTIES);
        Properties props = new Properties();
        props.load(propsStream);
        Config config = new Config(props);
        Model model = new ModelImpl(config);

        LineNumberReader lnr = new LineNumberReader(new InputStreamReader(System.in));
        String line;
        while ((line = lnr.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            String[] items = line.split(" ");
            Map<String, String> params = new HashMap<String, String>();
            for (int idx = 1; idx < items.length; idx++) {
                String[] keyValue = items[idx].split("=");
                params.put(keyValue[0], keyValue[1]);
            }
            try {
                boolean res = dispatchEvent(model, items[0], params);
                if (res) {
                    System.out.print("+");
                    showVariables(model);
                } else {
                    System.out.println("! Disabled EVENT: " + items[0] + params);
                }
            } catch (Exception ex) {
                System.out.println("- Error: " + ex.getMessage());
            }
        }
    }

    private static void showVariables(Model model) {
        StringBuilder sb = new StringBuilder();
        sb.append(model.RAIL_ELEM_POS_CURR());
        sb.append(";");
        sb.append(model.PATH_CURR());
        sb.append(";");
        sb.append(model.PATH_REQ());
        sb.append(";");
        sb.append(model.PATH_REL());
        sb.append(";");
        sb.append(model.TVD_STATE_CURR());
        sb.append(";");
        sb.append(model.SIGNAL_ASPECT_CURR());
        System.out.println(sb.toString());
    }

    /**
     * Dispatch Event.
     * @param event Event to be dispatched
     * @param param Event parameters.
     * @return true if the event has been performed, false otherwise.
     */
    private static boolean dispatchEvent(Model model, String event, Map<String, String> params) {
        boolean res;
        switch (event) {
        case "set_RAIL_ELEM_POS_CURR": {
            res = model.set_RAIL_ELEM_POS_CURR(getParameter(params, "elem"), getParameter(params, "pos"));
            break;
        }
        case "set_RAIL_ELEM_PATH": {
            res = model.set_RAIL_ELEM_PATH(getParameter(params, "elem"));
            break;
        }
        case "add_PATH_CURR": {
            res = model.add_PATH_CURR(getParameter(params, "path"));
            break;
        }
        case "rem_PATH_CURR": {
            res = model.rem_PATH_CURR(getParameter(params, "path"));
            break;
        }
        case "add_PATH_REQ": {
            res = model.add_PATH_REQ(getParameter(params, "path"));
            break;
        }
        case "rem_PATH_REQ": {
            res = model.rem_PATH_REQ(getParameter(params, "path"));
            break;
        }
        case "set_PATH_REL": {
            res = model.set_PATH_REL(getParameter(params, "path"));
            break;
        }
        case "set_TVDS_STATE_CURR": {
            res = model.set_TVDS_STATE_CURR(getParameter(params, "sect"), getParameter(params, "stat"));
            break;
        }
        case "set_TVDS_STATE_PATH": {
            res = model.set_TVDS_STATE_PATH(getParameter(params, "sect"), getParameter(params, "stat"));
            break;
        }
        case "set_SIGNAL_ASPECT_PROCEED": {
            res = model.set_SIGNAL_ASPECT_PROCEED(getParameter(params, "sig"), getParameter(params, "asp"));
            break;
        }
        case "set_SIGNAL_ASPECT_DEFAULT": {
            res = model.set_SIGNAL_ASPECT_DEFAULT(getParameter(params, "sig"));
            break;
        }
        default:
            throw new IllegalArgumentException("Unknown EVENT: " + event + params);
        }
        return res;
    }

    private static String getParameter(Map<String, String> params, String name) {
        String param = params.get(  name);
        if (param == null) {
            throw new IllegalArgumentException("Missing parameter: " + name);
        }
        return param;
    }
}
