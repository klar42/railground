package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail segment repository.
 * @author tofische
 */
public class RAIL_SGMT_repo extends RepositoryBase {

    /** Enumeration of all rail segments identified by id. */
    private Map<String, RAIL_SGMT> RAIL_SGMT_enum = new HashMap<>();

    /**
     * Create the repository for the given model and configuration.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public RAIL_SGMT_repo(ModelImpl model, Config config) {
        super(model, config);
    }

    /**
     * Get the rail segment for the given id.
     * @param id Rail segment id.
     * @return Rail segment with the given id.
     */
    public RAIL_SGMT get_RAIL_SGMT(String id) {
        return RAIL_SGMT_enum.get(id);
    }

    /**
     * Create and register the rail segment with the given id and begin as well as end connector.
     * @param id Rail segment id.
     * @param begCtor Begin connector.
     * @param endCtor End connector.
     * @return Created rail segment.
     */
    public RAIL_SGMT create_RAIL_SGMT(String id, RAIL_CTOR begCtor, RAIL_CTOR endCtor) {
        RAIL_SGMT rail_sgmt = new RAIL_SGMT(id, begCtor, endCtor, this);
        RAIL_SGMT_enum.put(id, rail_sgmt);
        return rail_sgmt;
    }
}
