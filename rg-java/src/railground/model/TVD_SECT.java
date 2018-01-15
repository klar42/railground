package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Track Vacancy Detection (TVD) Section.
 * @author tofische
 */
public class TVD_SECT extends ModelElementBase<TVD_SECT_repo> {

    /** Configuration property. */
    private static final String PROP_TVD_SECT_SGMT = "TVD_SECT_SGMT";

    /** Assignment of Rail Segments to TVD Sections. */
    protected Map<String, RAIL_SGMT> TVD_SECT_SGMT = new HashMap<>();

    // Assigment of TVD Section to Rail Segments (derived from TVD_SECT_SGMT).
    // TVD_SGMT_SECT

    /** Current state of the particular TVD Section. */
    protected TVD_STATE_ENUM TVDS_STATE_CURR = TVD_STATE_ENUM.TVD_STATE_VACANT; 

    /**
     * Create the TVD Section.
     * @param id TVD Section id.
     * @param repo Repository this model element belongs to.
     * @param config Configuration this model element shall be initialized from.
     */
    protected TVD_SECT(String id, TVD_SECT_repo repo, Config config) {
        super(id, repo);

        for (String tvdSectSgmtId: config.getStrArr(id + "." + PROP_TVD_SECT_SGMT)) {
            RAIL_SGMT rail_sgmt = getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(tvdSectSgmtId);
            TVD_SECT_SGMT.put(tvdSectSgmtId, rail_sgmt);
        }
    }

    public String get_TVDS_STATE_CURR() {
        return TVDS_STATE_CURR.toString();
    }

    /**
     * Check invariants.
     * @throws RuntimeException if any invariant is violated.
     */
    private void checkInvariants() {
    }

    /**
     * Guards of {@link set_TVDS_STATE_CURR}.
     * @param stat New state.
     * @return true if enabled, false if disabled.
     */
    public boolean _set_TVDS_STATE_CURR(TVD_STATE_ENUM stat) {
        // New TVD State is different from the old one.
        boolean stat_diff = stat != TVDS_STATE_CURR;
        // TVD Section doesn't belong to any current path.
        boolean sgmt_curr = true; // sect ∉ TVD_SGMT_SECT[union(PATH_SGMT_ALL[PATH_CURR])]

        return stat_diff && sgmt_curr;
    }

    /**
     * Set new current state of the particular TVD Section which isn't a part of any current Path.
     * @param stat New state.
     * @return true if executed, false otherwise.
     */
    public boolean set_TVDS_STATE_CURR(TVD_STATE_ENUM stat) {
        boolean guards = _set_TVDS_STATE_CURR(stat);
        if (guards) {
            TVDS_STATE_CURR = stat;
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link set_TVDS_STATE_PATH}.
     * @param stat New state.
     * @return true if enabled, false if disabled.
     */
    public boolean _set_TVDS_STATE_PATH(TVD_STATE_ENUM stat) {
        // New TVD State is different from the old one.
        boolean stat_diff = stat != TVDS_STATE_CURR;
        // TVD Section belongs to some current path.
        boolean sgmt_curr = true; // sect ∈ TVD_SGMT_SECT[union(PATH_SGMT_ALL[PATH_CURR])]

        return stat_diff && sgmt_curr;
    }

    /**
     * Set new current state of the particular TVD Section which is a part of some current Path.
     * @param stat New state.
     * @return true if executed, false otherwise.
     */
    public boolean set_TVDS_STATE_PATH(TVD_STATE_ENUM stat) {
        boolean guards = _set_TVDS_STATE_PATH(stat);
        if (guards) {
            TVDS_STATE_CURR = stat;
        }
        checkInvariants();
        return guards;
    }
}
