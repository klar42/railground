package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail terminator repository.
 * @author tofische
 */
public class RAIL_TERM_repo extends RepositoryBase {

    /** Configuration property. */
    private static final String PROP_RAIL_TERM = "RAIL_TERM";

    /** Enumeration of all rail terminators identified by id. */
    private Map<String, RAIL_TERM> RAIL_TERM_enum = new HashMap<>();

    /**
     * Create the repository for the given model and configuration.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public RAIL_TERM_repo(ModelImpl model, Config config) {
        super(model, config);
        for (String railTermId: config.getStrArr(PROP_RAIL_TERM)) {
            RAIL_TERM rail_term = new RAIL_TERM(railTermId, this);
            RAIL_TERM_enum.put(railTermId, rail_term);
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
     * Get the rail terminator for the given id.
     * @param id Rail terminator id.
     * @return Rail terminator with the given id.
     */
    public RAIL_TERM get_RAIL_TERM(String id) {
        return RAIL_TERM_enum.get(id);
    }
}
