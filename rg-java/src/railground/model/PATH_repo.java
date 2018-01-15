package railground.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Path repository.
 * @author tofische
 */
public class PATH_repo extends RepositoryBase {

    /** Configuration property. */
    private static final String PROP_PATH = "PATH";

    /** Enumeration of all paths identified by id. */
    private Map<String, PATH> PATH_enum = new HashMap<>();

    /** Current Paths. */
    private Set<PATH> PATH_CURR = new HashSet<>();

    /** Requested Paths. */
    private Set<PATH> PATH_REQ = new HashSet<>();

    /** Paths being released. */
    private Set<PATH> PATH_REL = new HashSet<>();

    /**
     * Create the repository for the given model and configuration.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public PATH_repo(ModelImpl model, Config config) {
        super(model, config);
        for (String pathId: config.getStrArr(PROP_PATH)) {
            PATH path = new PATH(pathId, this, config);
            PATH_enum.put(pathId, path);
        }
        checkAxioms();
    }

    public String get_PATH_CURR() {
        return PATH_CURR.toString();
    }

    public String get_PATH_REQ() {
        return PATH_REQ.toString();
    }

    public String get_PATH_REL() {
        return PATH_REL.toString();
    }

    /**
     * Check axioms.
     * @throws RuntimeException if any axiom is violated.
     */
    private void checkAxioms() {
        // TO-DO

        // Path exclusions are symmetric.
        // PATH_EXCL·symm

        // Path exclusions are irreflexive.
        // PATH_EXCL_irreflexive
    }

    /**
     * Get the path for the given id.
     * @param id Path id.
     * @return Path with the given id.
     */
    public PATH get_PATH(String id) {
        return PATH_enum.get(id);
    }

    /**
     * Check invariants.
     * @throws RuntimeException if any invariant is violated.
     */
    private void checkInvariants() {
        // Elements of the current path correspond to the elements of defined paths.
        // RAIL_ELEM_PATH_CURR·val

        // Elements of all current paths are in required position.
        // RAIL_ELEM_PATH_CURR·glue

        // Requested and current paths are disjunct.
        boolean PATH_REQ_curr = Collections.disjoint(PATH_REQ, PATH_CURR);

        // No Rail Element belongs to two Requested Paths at once.
        // PATH_ELEM_POS·disjunct·req

        // No Rail Element belongs to some Requested Path and some Current Path at once.
        // PATH_ELEM_POS·disjunct·curr

        // Path being released is still a current path.
        boolean PATH_REL_curr = PATH_CURR.containsAll(PATH_REL);

        // No Current Path is excluded by any other Current Path.
        // PATH_EXCL·curr

        if (!PATH_REQ_curr || !PATH_REL_curr) {
            throw new RuntimeException();
        }
    }

    /**
     * Guards of {@link add_PATH_CURR}.
     * @param path Path to add
     * @return true if enabled, false if disabled.
     */
    public boolean _add_PATH_CURR(PATH path) {
        boolean path_valid = !PATH_CURR.contains(path); // Path is not a current one.

        // All elements are in required position.
        // path·elem·pos:  ∀ elem · elem ∈ dom(PATH_ELEM_POS(path)) ⇒ PATH_ELEM_POS(path)(elem) = RAIL_ELEM_POS_CURR(elem)

        // Path to add is disjuct with all other current paths.
        // path·elem·disjunct: dom(PATH_ELEM_POS(path)) ∩ dom(RAIL_ELEM_PATH_CURR) = ∅

        // Path is a requested one.
        boolean path_req = PATH_REQ.contains(path);

        // Path is not excluded.
        // path·excl:  PATH_EXCL[{path}] ∩ PATH_CURR = ∅

        return path_valid && path_req;
    }

    /**
     * Add a new Path.
     * @param path Path to add.
     * @return true if executed, false otherwise.
     */
    public boolean add_PATH_CURR(PATH path) {
        boolean guards = _add_PATH_CURR(path);
        if (guards) {
            PATH_CURR.add(path);
            // RAIL_ELEM_PATH_CURR·value:  RAIL_ELEM_PATH_CURR ≔ RAIL_ELEM_PATH_CURR  (dom(PATH_ELEM_POS(path)) × {path})
            PATH_REQ.remove(path);
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link rem_PATH_CURR}.
     * @param path Path to remove.
     * @return true if enabled, false if disabled.
     */
    public boolean _rem_PATH_CURR(PATH path) {
        // Path is a current one.
        boolean path_valid = PATH_CURR.contains(path);
        // Only path being released may be removed.
        boolean path_rel = PATH_REL.contains(path);
        // path·depend:  ∀ sig · sig ∈ SIGNAL ∧ SIGNAL_ASPECT_CURR(sig) ≠ SIGNAL_ASPECT_DEFAULT ⇒ SIGNAL_CTOR(sig) ≠ PATH_CTOR_BEG(path)
        return path_valid && path_rel;
    }

    /**
     * Remove an existing Path.
     * @param path Path to remove.
     * @return true if executed, false otherwise.
     */
    public boolean rem_PATH_CURR(PATH path) {
        boolean guards = _rem_PATH_CURR(path);
        if (guards) {
            PATH_CURR.remove(path);
            // RAIL_ELEM_PATH_CURR·value:  RAIL_ELEM_PATH_CURR ≔ RAIL_ELEM_PATH_CURR ⩥ {path} ›
            PATH_REL.remove(path);
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link add_PATH_REQ}.
     * @param path Path to add.
     * @return true if enabled, false if disabled.
     */
    public boolean _add_PATH_REQ(PATH path) {
        // Path is not a current or requested one.
        boolean path_valid_curr = !PATH_CURR.contains(path) && !PATH_REQ.contains(path);
        // Requested path is disjunct with all other current paths.
        // path·elem·disjunct·curr:  dom(PATH_ELEM_POS(path)) ∩ dom(RAIL_ELEM_PATH_CURR) = ∅
        // Requested path is disjunct with all other requested paths.
        // path·elem·disjunct·req: dom(PATH_ELEM_POS(path)) ∩ dom(union(PATH_ELEM_POS[PATH_REQ])) = ∅

        return path_valid_curr;
    }

    /**
     * Add a requested Path.
     * @param path Path to remove.
     * @return true if executed, false otherwise.
     */
    public boolean add_PATH_REQ(PATH path) {
        boolean guards = _add_PATH_REQ(path);
        if (guards) {
            PATH_REQ.add(path);
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link rem_PATH_REQ}.
     * @param path Path to remove.
     * @return true if enabled, false if disabled.
     */
    public boolean _rem_PATH_REQ(PATH path) {
        // Path is a requested one.
        boolean path_valid = PATH_REQ.contains(path);

        return path_valid;
    }

    /**
     * Remove a requested Path.
     * @param path Path to remove.
     * @return true if executed, false otherwise.
     */
    public boolean rem_PATH_REQ(PATH path) {
        boolean guards = _rem_PATH_REQ(path);
        if (guards) {
            PATH_REQ.remove(path);
        }
        checkInvariants();
        return guards;
    }

    /**
     * Guards of {@link set_PATH_REL}.
     * @param elem Element to remove.
     * @return true if enabled, false if disabled.
     */
    public boolean _set_PATH_REL(PATH path) {
        // Path is a current one.
        boolean path_curr = PATH_CURR.contains(path);
        // Path is not being already released.
        boolean path_rel = !PATH_REL.contains(path);
        // There is no signal in proceed aspect in front of this path.
        boolean path_depend = true; // ∀ sig
                                    // · sig ∈ SIGNAL
                                    // ∧ SIGNAL_ASPECT_CURR(sig) ≠ SIGNAL_ASPECT_DEFAULT
                                    // ⇒ SIGNAL_CTOR(sig) ≠ PATH_CTOR_BEG(path)
        return path_curr && path_rel && path_depend;
    }

    /**
     * Set a path as being released.
     * @param path Path to release.
     * @return true if executed, false otherwise.
     */
    public boolean set_PATH_REL(PATH path) {
        boolean guards = _set_PATH_REL(path);
        if (guards) {
            PATH_REL.add(path);
        }
        checkInvariants();
        return guards;
    }
}
