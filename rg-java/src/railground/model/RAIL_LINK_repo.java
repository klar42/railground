package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail link repository.
 * @author tofische
 */
public class RAIL_LINK_repo extends RepositoryBase {

    /** Configuration property. */
    private static final String PROP_RAIL_LINK = "RAIL_LINK";

    /** Rail links - mapping from the begin to the end connector. */
    private Map<RAIL_CTOR, RAIL_CTOR> RAIL_LINK_enum = new HashMap<>();

    /**
     * Create the repository for the given model and configuration.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public RAIL_LINK_repo(ModelImpl model, Config config) {
        super(model, config);
        for (String[] rail_link: config.getStrSeqArr(PROP_RAIL_LINK)) {
            RAIL_CTOR ralLinkBeg = model.get_RAIL_CTOR_repo().get_RAIL_CTOR(rail_link[0]);
            RAIL_CTOR ralLinkEnd = model.get_RAIL_CTOR_repo().get_RAIL_CTOR(rail_link[1]);
            RAIL_LINK_enum.put(ralLinkBeg, ralLinkEnd);
        }
    }

    /**
     * Get the end connector connected via the rail link for the given begin connector.
     * @param beg Begin connector.
     * @return End connector.
     */
    public RAIL_CTOR get_RAIL_LINK(RAIL_CTOR beg) {
        return RAIL_LINK_enum.get(beg);
    }
}
