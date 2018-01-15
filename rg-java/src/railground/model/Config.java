package railground.model;

import java.util.Properties;

public class Config {

    private Properties props;

    public Config(Properties props) {
        this.props = props;
    }

    /** Get string optional. */
    public String getStrOpt(String propName) {
        String val = props.getProperty(propName);
        if (val != null) {
            val = val.trim();
        }
        return val;
    }

    /** Get string. */
    public String getStr(String propName) {
        String val = getStrOpt(propName);
        if (val == null) {
            throw new RuntimeException("Missing config " + propName);
        } else {
            return val;
        }
    }

    /** Get string array optional. */
    public String[] getStrArrOpt(String propName) {
        String strOpt = getStrOpt(propName);
        String[] strArrOpt = (strOpt == null) ? new String[0] : strOpt.split(" ");
        return strArrOpt;
    }

    /** Get string array. An array consists of multiple space separated values. */
    public String[] getStrArr(String propName) {
        String str = getStr(propName);
        String[] strArr = str.isEmpty() ? new String[0] : str.split(" ");
        return strArr;
    }

    /** Get string sequence array. A sequence consists of multiple slash separated values. */
    public String[][] getStrSeqArr(String propName) {
        String[] strArr = getStrArr(propName);
        String[][] strSeqArr = new String[strArr.length][];
        for (int idx = 0; idx < strSeqArr.length; idx++) {
            strSeqArr[idx] = strArr[idx].split("/");
        }
        return strSeqArr;
    }
}
