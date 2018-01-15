package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail element repository.
 * @author tofische
 */
public class RAIL_ELEM_repo extends RepositoryBase {

    /** Configuration property. */
    private static final String PROP_RAIL_ELEM = "RAIL_ELEM";

    /** Enumeration of all rail elements identified by id. */
    private Map<String, RAIL_ELEM> RAIL_ELEM_enum = new HashMap<>();

    /**
     * Create the repository for the given model and configuration.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public RAIL_ELEM_repo(ModelImpl model, Config config) {
        super(model, config);
        for (String railElemId: config.getStrArr(PROP_RAIL_ELEM)) {
            RAIL_ELEM rail_elem = new RAIL_ELEM(railElemId, this);
            RAIL_ELEM_enum.put(railElemId, rail_elem);
        }
        checkAxioms();
    }

    /**
     * Check axioms.
     * @throws RuntimeException if any axiom is violated.
     */
    private void checkAxioms() {
        // TO-DO
    }

    /**
     * Get the rail element for the given id.
     * @param id Rail element id.
     * @return Rail element with the given id.
     */
    public RAIL_ELEM get_RAIL_ELEM(String id) {
        return RAIL_ELEM_enum.get(id);
    }

    public String get_RAIL_ELEM_POS_CURR() {
        String sep = "";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String key : RAIL_ELEM_enum.keySet()) {
            String val = RAIL_ELEM_enum.get(key).get_RAIL_ELEM_POS_CURR();
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
