package railground.model;

/**
 * Rail segment.
 * @author tofische
 */
public class RAIL_SGMT extends ModelElementBase<RAIL_SGMT_repo> {

    /** Rail connector begin connector. */
    private final RAIL_CTOR begCtor;

    /** Rail connector end connector. */
    private final RAIL_CTOR endCtor;

    /**
     * Create the rail segment with the given id and begin as well as end connector.
     * Only rail segment repository is allowed to create the rail segments.
     * @param id Rail segment id.
     * @param begCtor Begin connector.
     * @param endCtor End connector.
     * @param repo Corresponding rail segment repository.
     */
    protected RAIL_SGMT(String id, RAIL_CTOR begCtor, RAIL_CTOR endCtor, RAIL_SGMT_repo repo) {
        super(id, repo);
        this.begCtor = begCtor;
        this.endCtor = endCtor;
    }

    /**
     * Get the begin connector.
     * @return Begin connector.
     */
    public RAIL_CTOR getBegCtor() {
        return begCtor;
    }

    /**
     * Get the end connector.
     * @return End connector.
     */
    public RAIL_CTOR getEndCtor() {
        return endCtor;
    }
}
