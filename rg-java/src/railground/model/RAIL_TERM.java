package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail terminator.
 * @author tofische
 */
public class RAIL_TERM extends RAIL_ITEM<RAIL_TERM_repo> {

    protected Map<String, RAIL_CTOR> RAIL_ITEM_CTOR = new HashMap<>();

    protected RAIL_TERM(String id, RAIL_TERM_repo repo) {
        super(id, repo);
        add_RAIL_CTOR(id + "E");
    }
}
