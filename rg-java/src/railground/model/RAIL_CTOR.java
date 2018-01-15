package railground.model;

/**
 * Rail connector.
 * @author tofische
 */
public class RAIL_CTOR extends ModelElementBase<RAIL_CTOR_repo> {

    /**
     * Create the rail connector with the given id.
     * Only rail connector repository is allowed to create the rail connectors.
     * @param id Rail connector id.
     * @param repo Corresponding rail connector repository.
     */
    protected RAIL_CTOR(String id, RAIL_CTOR_repo repo) {
        super(id, repo);
    }
}
