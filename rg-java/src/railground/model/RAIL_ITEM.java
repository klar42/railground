package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Rail item (common term for rail terminator and rail element).
 * @param <REPO> Model element repository type.
 * @author tofische
 */
public abstract class RAIL_ITEM<REPO extends RepositoryBase> extends ModelElementBase<REPO> {

    /** Rail connectors of this rail item. */
    protected Map<String, RAIL_CTOR> RAIL_ITEM_CTOR = new HashMap<>();

    /**
     * @param id
     * @param repo
     */
    protected RAIL_ITEM(String id, REPO repo) {
        super(id, repo);
    }

    /**
     * Create and register a rail connector belonging to this rail item.
     * @param railCtorId Rail connector id.
     * @return Created rail connector.
     */
    protected RAIL_CTOR add_RAIL_CTOR(String railCtorId) {
        RAIL_CTOR rail_ctor = getModel().get_RAIL_CTOR_repo().create_RAIL_CTOR(railCtorId);
        RAIL_ITEM_CTOR.put(railCtorId, rail_ctor);
        return rail_ctor;
    }
}
