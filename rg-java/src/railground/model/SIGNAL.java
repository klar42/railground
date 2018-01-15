package railground.model;

import java.util.EnumSet;

/**
 * Signal.
 * @author tofische
 */
public class SIGNAL extends ModelElementBase<SIGNAL_repo> {

    /** Configuration property. */
    private static final String PROP_SIGNAL_CTOR = "SIGNAL_CTOR";

    /** Association of Signals to the particular Rail Connector. */
    private final RAIL_CTOR SIGNAL_CTOR;

    /** Available Signal Aspects of the particular Signal. */
    public final EnumSet<SIGNAL_ASPECT_ENUM> SIGNAL_ASPECT_AVAIL = EnumSet.allOf(SIGNAL_ASPECT_ENUM.class);

    /** Default Signal Aspect of all Signals. */
    public static final SIGNAL_ASPECT_ENUM SIGNAL_ASPECT_DEFAULT = SIGNAL_ASPECT_ENUM.SIGNAL_ASPECT_STOP;

    /** Current Signal Aspect of the particular Signal. */
    private SIGNAL_ASPECT_ENUM SIGNAL_ASPECT_CURR = SIGNAL_ASPECT_DEFAULT;

    /**
     * Create the Signal.
     * @param id Signal id.
     * @param repo Repository this model element belongs to.
     * @param config Configuration this model element shall be initialized from.
     */
    SIGNAL(String id, SIGNAL_repo repo, Config config) {
        super(id, repo);

        String prop_signal_ctor = config.getStr(id + "." + PROP_SIGNAL_CTOR);
        SIGNAL_CTOR = getModel().get_RAIL_CTOR_repo().get_RAIL_CTOR(prop_signal_ctor);
    }

    /**
     * Check invariants.
     * @throws RuntimeException if any invariant is violated.
     */
    private void checkInvariants() {
        // Current Signal Aspect is the available one.
        boolean SIGNAL_ASPECT_CURR_value = SIGNAL_ASPECT_AVAIL.contains(SIGNAL_ASPECT_CURR);
        // Non-default signal aspect requires a valid path behind the signal.
        boolean SIGNAL_PATH_depend = true; // ∀ sig
                                           // · sig ∈ SIGNAL
                                           // ∧ SIGNAL_ASPECT_CURR(sig) ≠ SIGNAL_ASPECT_DEFAULT
                                           // ⇒ (∃ path · path ∈ PATH_CURR ∧ path ∉ PATH_REL ∧ SIGNAL_CTOR(sig) = PATH_CTOR_BEG(path))
        if (!SIGNAL_PATH_depend) {
            throw new RuntimeException();
        }
    }

    public String get_SIGNAL_ASPECT_CURR() {
        return SIGNAL_ASPECT_CURR.toString();
    }

    /**
     * Guards of {@link set_SIGNAL_ASPECT_CURR}.
     * @param asp New signal aspect.
     * @return true if enabled, false if disabled.
     */
    private boolean _set_SIGNAL_ASPECT_CURR(SIGNAL_ASPECT_ENUM asp) {
        // New Signal Aspect is available for this Signal.
        boolean asp_avail = SIGNAL_ASPECT_AVAIL.contains(asp);
        // New Signal Aspect is different from the current one.
        boolean asp_diff = asp != SIGNAL_ASPECT_CURR;

        return asp_avail && asp_diff;
    }

    /**
     * Set new current Signal Aspect of the particular Signal.
     * @param asp New signal aspect.
     * @return true if executed, false otherwise.
     */
    private boolean set_SIGNAL_ASPECT_CURR(SIGNAL_ASPECT_ENUM asp) {
        boolean guards = _set_SIGNAL_ASPECT_CURR(asp);
        if (guards) {
            SIGNAL_ASPECT_CURR = asp;
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link set_SIGNAL_ASPECT_PROCEED}.
     * @param asp New signal aspect.
     * @return true if enabled, false if disabled.
     */
    public boolean _set_SIGNAL_ASPECT_PROCEED(SIGNAL_ASPECT_ENUM asp) {
        boolean set_SIGNAL_ASPECT_CURR_guard = _set_SIGNAL_ASPECT_CURR(asp);
        // Aspect is not a default one.
        boolean asp_default = asp != SIGNAL_ASPECT_DEFAULT;
        // There is a current path in rear of the signal.
        boolean path_depend = true; // ∃ path · path ∈ PATH_CURR ∧ path ∉ PATH_REL ∧ SIGNAL_CTOR(sig) = PATH_CTOR_BEG(path)

        return set_SIGNAL_ASPECT_CURR_guard && asp_default && path_depend;
    }

    /**
     * Set Signal to some Proceed signal aspect.
     * @param asp New signal aspect.
     * @return true if executed, false otherwise.
     */
    public boolean set_SIGNAL_ASPECT_PROCEED(SIGNAL_ASPECT_ENUM asp) {
        boolean guards = _set_SIGNAL_ASPECT_PROCEED(asp);
        if (guards) {
            set_SIGNAL_ASPECT_CURR(asp);
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link set_SIGNAL_ASPECT_DEFAULT}.
     * @return true if enabled, false if disabled.
     */
    public boolean _set_SIGNAL_ASPECT_DEFAULT() {
        boolean set_SIGNAL_ASPECT_CURR_guard = _set_SIGNAL_ASPECT_CURR(SIGNAL_ASPECT_DEFAULT);
        // Signal aspect is not already default.
        boolean asp_default = SIGNAL_ASPECT_CURR != SIGNAL_ASPECT_DEFAULT;

        return set_SIGNAL_ASPECT_CURR_guard && asp_default;
    }

    /**
     * Set Signal to Default signal aspect.
     * @return true if executed, false otherwise.
     */
    public boolean set_SIGNAL_ASPECT_DEFAULT() {
        boolean guards = _set_SIGNAL_ASPECT_DEFAULT();
        if (guards) {
            set_SIGNAL_ASPECT_CURR(SIGNAL_ASPECT_DEFAULT);
        }
        checkInvariants();
        return guards;
    }
}
