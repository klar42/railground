package railground.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Rail element.
 * @author tofische
 */
public class RAIL_ELEM extends RAIL_ITEM<RAIL_ELEM_repo> {

    /** Configuration property. */
    private static final String PROP_RAIL_ELEM_POS_CON = "RAIL_ELEM_POS_CON";

    /** Configuration property. */
    private static final String PROP_RAIL_ELEM_POS_DEFAULT = "RAIL_ELEM_POS_DEFAULT";

    protected final Map<String, RAIL_SGMT> RAIL_ELEM_SGMT = new HashMap<>();

    /** Connectivity for individual Element Positions of the particular Rail Element. */
    protected final Map<RAIL_POS_ENUM, Set<RAIL_SGMT>> RAIL_ELEM_POS_CONN = new HashMap<>();

    /** Default Element Position of the particular Rail Element. */
    protected final RAIL_POS_ENUM RAIL_ELEM_POS_DEFAULT;

    // All defined Rail Element Connectivities of the particular Rail Element.
    // RAIL_ELEM_CONN_ALL

    /** Default Rail Element Connectivity of the particular Rail Element. */
    protected final Set<RAIL_SGMT> RAIL_ELEM_CONN_DEFAULT;

    // Rail Element the particular Rail Segment belongs to.
    // RAIL_SGMT_ELEM

    /** Current Rail Element Connectivity of the particular Rail Element. */
    protected Set<RAIL_SGMT> RAIL_ELEM_CONN_CURR;

    /** Current Element Position of the particular Rail Element. */
    protected RAIL_POS_ENUM RAIL_ELEM_POS_CURR;

    // Current Path the Rail Element belongs to.
    // RAIL_ELEM_PATH_CURR

    protected RAIL_ELEM(String id, RAIL_ELEM_repo repo) {
        super(id, repo);

        // TO-DO: Better element type distinguish 
        switch (id.charAt(0)) {
        case 'T':
            RAIL_CTOR rail_ctor_TA = add_RAIL_CTOR(id + "A");
            RAIL_CTOR rail_ctor_TB = add_RAIL_CTOR(id + "B");
            add_RAIL_SGMT(id + "A" + "_" + id + "B", rail_ctor_TA, rail_ctor_TB);
            add_RAIL_SGMT(id + "B" + "_" + id + "A", rail_ctor_TB, rail_ctor_TA);
            break;
        case 'P':
            RAIL_CTOR rail_ctor_PT = add_RAIL_CTOR(id + "T");
            RAIL_CTOR rail_ctor_PL = add_RAIL_CTOR(id + "L");
            RAIL_CTOR rail_ctor_PR = add_RAIL_CTOR(id + "R");
            add_RAIL_SGMT(id + "T" + "_" + id + "L", rail_ctor_PT, rail_ctor_PL);
            add_RAIL_SGMT(id + "L" + "_" + id + "T", rail_ctor_PL, rail_ctor_PT);
            add_RAIL_SGMT(id + "T" + "_" + id + "R", rail_ctor_PT, rail_ctor_PR);
            add_RAIL_SGMT(id + "R" + "_" + id + "T", rail_ctor_PR, rail_ctor_PT);
            break;
        case 'C':
            RAIL_CTOR rail_ctor_CA = add_RAIL_CTOR(id + "A");
            RAIL_CTOR rail_ctor_CB = add_RAIL_CTOR(id + "B");
            RAIL_CTOR rail_ctor_CC = add_RAIL_CTOR(id + "C");
            RAIL_CTOR rail_ctor_CD = add_RAIL_CTOR(id + "D");
            add_RAIL_SGMT(id + "A" + "_" + id + "D", rail_ctor_CA, rail_ctor_CD);
            add_RAIL_SGMT(id + "B" + "_" + id + "A", rail_ctor_CD, rail_ctor_CA);
            add_RAIL_SGMT(id + "C" + "_" + id + "C", rail_ctor_CB, rail_ctor_CC);
            add_RAIL_SGMT(id + "D" + "_" + id + "B", rail_ctor_CC, rail_ctor_CB);
            break;
        }

        for (RAIL_POS_ENUM pos: RAIL_POS_ENUM.values()) {
            String prop_rail_elem_pos_con = repo.getConfig().getStrOpt(id + "." + pos + "." + PROP_RAIL_ELEM_POS_CON);
            if (prop_rail_elem_pos_con != null) {
                Set<RAIL_SGMT> sgmt = new HashSet<>();
                for (String connId : prop_rail_elem_pos_con.split(" ")) {
                    sgmt.add(repo.getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(connId));
                }
                RAIL_ELEM_POS_CONN.put(pos, sgmt);
            }
        }

        String prop_rail_elem_pos_default = repo.getConfig().getStr(id + "." + PROP_RAIL_ELEM_POS_DEFAULT);
        RAIL_ELEM_POS_DEFAULT = RAIL_POS_ENUM.valueOf(prop_rail_elem_pos_default);

        RAIL_ELEM_CONN_DEFAULT = RAIL_ELEM_POS_CONN.get(RAIL_ELEM_POS_DEFAULT);

        RAIL_ELEM_CONN_CURR = RAIL_ELEM_CONN_DEFAULT;

        RAIL_ELEM_POS_CURR = RAIL_ELEM_POS_DEFAULT;
    }

    /**
     * Create and register a rail segment belonging to this rail element.
     * @param id Rail segment id.
     * @param begCtor Begin connector.
     * @param endCtor End connector.
     * @return Created rail segment.
     * @return
     */
    protected RAIL_SGMT add_RAIL_SGMT(String railSgmtId, RAIL_CTOR railCtorBeg, RAIL_CTOR railCtorEnd) {
        RAIL_SGMT rail_sgmt = getModel().get_RAIL_SGMT_repo().create_RAIL_SGMT(railSgmtId, railCtorBeg, railCtorEnd);
        RAIL_ELEM_SGMT.put(railSgmtId, rail_sgmt);
        return rail_sgmt;
    }

    /**
     * Check invariants.
     * @throws RuntimeException if any invariant is violated.
     */
    private void checkInvariants() {
        // Current connectivity is defined.
        // RAIL_ELEM_CONN·valid
        // Currect position is defined.
        boolean RAIL_ELEM_POS_CURR_valid = RAIL_ELEM_POS_CONN.containsKey(RAIL_ELEM_POS_CURR);
        // Connectivity is consistent with the position.
        // RAIL_ELEM_CONN_CURR·glue
        if (!RAIL_ELEM_POS_CURR_valid) {
//            throw new RuntimeException();
        }
    }

    /**
     * Guards of {@link set_RAIL_ELEM_CONN_CURR}.
     * @param conn New connectivity.
     * @return true if enabled, false if disabled.
     */
    private boolean _set_RAIL_ELEM_CONN_CURR(Set<RAIL_SGMT> conn) {
        // New connectivity is defined for this element.
        // conn·valid: conn ∈ RAIL_ELEM_CONN_ALL(elem)
        return true;
    }

    /**
     * Set new current Rail Element Connectivity of the particular Rail Element.
     * @param conn New connectivity.
     * @return true if executed, false otherwise.
     */
    private boolean set_RAIL_ELEM_CONN_CURR(Set<RAIL_SGMT> conn) {
        boolean guards = _set_RAIL_ELEM_CONN_CURR(conn);
        if (guards) {
            RAIL_ELEM_CONN_CURR = conn;
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link set_RAIL_ELEM_POS_CURR}.
     * @param pos New position.
     * @return true if enabled, false if disabled.
     */
    protected boolean _set_RAIL_ELEM_POS_CURR(RAIL_POS_ENUM pos) {
        Set<RAIL_SGMT> conn = RAIL_ELEM_POS_CONN.get(pos);

        // New current position is defined.
        boolean pos_valid = true; // pos ∈ dom(RAIL_ELEM_POS_CONN(elem))

        // New position is different from the old one.
        boolean pos_diff = pos != RAIL_ELEM_POS_CURR;

        // The element doesn't belong to any current path.
        boolean elem_path_curr = true; // elem ∉ dom(RAIL_ELEM_PATH_CURR)

        // The element doesn't belong to any requested path.
        boolean elem_path_req = true; // elem ∉ dom(union(PATH_ELEM_POS[PATH_REQ]))

        return
            _set_RAIL_ELEM_CONN_CURR(conn) &&
            pos_valid && pos_diff && elem_path_curr && elem_path_req;
    }

    public String get_RAIL_ELEM_POS_CURR() {
        return RAIL_ELEM_POS_CURR.toString();
    }

    /**
     * Set new current Element Position of the particular Rail Element which isn't a part of a Path.
     * @param pos New position.
     * @return true if executed, false otherwise.
     */
    public boolean set_RAIL_ELEM_POS_CURR(RAIL_POS_ENUM pos) {
        boolean guards = _set_RAIL_ELEM_POS_CURR(pos);
        if (guards) {
            Set<RAIL_SGMT> conn = RAIL_ELEM_POS_CONN.get(pos);
            // assert conn ∈ RAIL_ELEM_CONN_ALL(elem) // New connectivity is valid.
            set_RAIL_ELEM_CONN_CURR(conn);
            RAIL_ELEM_POS_CURR = pos;
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link set_RAIL_ELEM_PATH}.
     * @return true if enabled, false if disabled.
     */
    protected boolean _set_RAIL_ELEM_PATH() {
        // The element belongs to some requested path.
        boolean elem_path_req = true; // elem ∈ dom(union(PATH_ELEM_POS[PATH_REQ]))
        // The element doesn't belong to any current path.
        boolean elem_path_curr = true; // elem ∉ dom(RAIL_ELEM_PATH_CURR)
        // Requested position is different from the current one.
        boolean pos_diff = true; // union(PATH_ELEM_POS[PATH_REQ])(elem) ≠ RAIL_ELEM_POS_CURR(elem)
        return elem_path_req && elem_path_curr && pos_diff;
    }

    /**
     * Set the proper Element Position of the particular Rail Element which is a part of a Path.
     * @return true if executed, false otherwise.
     */
    public boolean set_RAIL_ELEM_PATH() {
        boolean guards = _set_RAIL_ELEM_PATH();
        if (guards) {
            RAIL_POS_ENUM pos = RAIL_POS_ENUM.RAIL_POS_X; // TO-DO
            Set<RAIL_SGMT> conn = RAIL_ELEM_POS_CONN.get(pos);
            RAIL_ELEM_POS_CURR = pos;
            RAIL_ELEM_CONN_CURR = conn;
        }
        checkInvariants();
        return guards;
    }
}
