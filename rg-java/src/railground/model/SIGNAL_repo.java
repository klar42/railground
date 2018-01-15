package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Signal repository.
 * @author tofische
 */
public class SIGNAL_repo extends RepositoryBase {

    /** Configuration property. */
    private static final String PROP_SIGNAL = "SIGNAL";

    /** Enumeration of all signals identified by id. */
    private Map<String, SIGNAL> SIGNAL_enum = new HashMap<>();

    /**
     * Create the signal repository.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public SIGNAL_repo(ModelImpl model, Config config) {
        super(model, config);
        for (String signalId: config.getStrArr(PROP_SIGNAL)) {
            SIGNAL signal = new SIGNAL(signalId, this, config);
            SIGNAL_enum.put(signalId, signal);
        }
        checkAxioms();
    }

    /**
     * Check axioms.
     * @throws RuntimeException if any axiom is violated.
     */
    private void checkAxioms() {
    }

    /**
     * Get the signal for the given id.
     * @param id Signal id.
     * @return Signal with the given id.
     */
    public SIGNAL get_SIGNAL(String id) {
        return SIGNAL_enum.get(id);
    }

    public String get_SIGNAL_ASPECT_CURR() {
        String sep = "";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String key : SIGNAL_enum.keySet()) {
            String val = SIGNAL_enum.get(key).get_SIGNAL_ASPECT_CURR();
            sb.append(sep);
            sb.append(key);
            sb.append("=");
            sb.append(val);
            sep = ", ";
        }
        sb.append("]");
        return sb.toString();
    }
}
