package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail connector repository.
 * @author tofische
 */
public class RAIL_CTOR_repo extends RepositoryBase {

    /** Enumeration of all rail connectors identified by id. */
    private Map<String, RAIL_CTOR> RAIL_CTOR_enum = new HashMap<>();

    /**
     * Create the repository for the given model and configuration.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public RAIL_CTOR_repo(ModelImpl model, Config config) {
        super(model, config);
    }

    /**
     * Get the rail connector for the given id.
     * @param id Rail connector id.
     * @return Rail connector with the given id.
     */
    public RAIL_CTOR get_RAIL_CTOR(String id) {
        return RAIL_CTOR_enum.get(id);
    }

    /**
     * Create and register the rail connector with the given id.
     * @param id Rail connector id.
     * @return Created rail connector.
     */
    public RAIL_CTOR create_RAIL_CTOR(String id) {
        RAIL_CTOR rail_ctor = new RAIL_CTOR(id, this);
        RAIL_CTOR_enum.put(id, rail_ctor);
        return rail_ctor;
    }
}
