package railground.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Path.
 * @author tofische
 */
public class PATH extends ModelElementBase<PATH_repo> {

    /** Configuration property. */
    private static final String PROP_PATH_ELEM_POS = "PATH_ELEM_POS";

    /** Configuration property. */
    private static final String PROP_PATH_SGMT_ALL = "PATH_SGMT_ALL";

    /** Configuration property. */
    private static final String PROP_PATH_CTOR_BEG = "PATH_CTOR_BEG";

    /** Configuration property. */
    private static final String PROP_PATH_CTOR_END = "PATH_CTOR_END";

    /** Configuration property. */
    private static final String PROP_PATH_SGMT_FST = "PATH_SGMT_FST";

    /** Configuration property. */
    private static final String PROP_PATH_SGMT_LST = "PATH_SGMT_LST";

    /** Configuration property. */
    private static final String PROP_PATH_SGMT_NXT = "PATH_SGMT_NXT";

    /** Configuration property. */
    private static final String PROP_PATH_EXCL = "PATH_EXCL";

    /** Rail Element Positions for the particular Path. */
    private final Map<RAIL_ELEM, RAIL_POS_ENUM> PATH_ELEM_POS = new HashMap<>();

    /** Rail Segments of the particular Path. */
    private Set<RAIL_SGMT> PATH_SGMT_ALL = new HashSet<>();

    /** Begin Rail Connector of the particular Path. */
    private RAIL_CTOR PATH_CTOR_BEG;

    /** End Rail Connector of the particular Path. */
    private RAIL_CTOR PATH_CTOR_END;

    /** First Rail Segment of the particular Path. */
    private RAIL_SGMT PATH_SGMT_FST;

    /** Last Rail Segment of the particular Path. */
    private RAIL_SGMT PATH_SGMT_LST;

    /** Next Rail Segment of the particular Path. */
    private Map<RAIL_SGMT, RAIL_SGMT> PATH_SGMT_NXT = new HashMap<>();

    /** Path exclusions. */
    private final Set<PATH> PATH_EXCL = new HashSet<>();

    /**
     * Create the Path.
     * @param id Path id.
     * @param repo Repository this model element belongs to.
     * @param config Configuration this model element shall be initialized from.
     */
    PATH(String id, PATH_repo repo, Config config) {
        super(id, repo);

        for (String[] path_elem_pos: config.getStrSeqArr(id + "." + PROP_PATH_ELEM_POS)) {
            RAIL_ELEM elem = getModel().get_RAIL_ELEM_repo().get_RAIL_ELEM(path_elem_pos[0]);
            RAIL_POS_ENUM pos = RAIL_POS_ENUM.valueOf(path_elem_pos[1]);
            PATH_ELEM_POS.put(elem, pos);
        }

        for (String path_sgmt_all: config.getStrArr(id + "." + PROP_PATH_SGMT_ALL)) {
            PATH_SGMT_ALL.add(getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(path_sgmt_all));
        }

        String path_ctor_beg = config.getStr(id + "." + PROP_PATH_CTOR_BEG);
        PATH_CTOR_BEG = getModel().get_RAIL_CTOR_repo().get_RAIL_CTOR(path_ctor_beg);

        String path_ctor_end = config.getStr(id + "." + PROP_PATH_CTOR_END);
        PATH_CTOR_END = getModel().get_RAIL_CTOR_repo().get_RAIL_CTOR(path_ctor_end);

        String path_sgmt_fst = config.getStr(id + "." + PROP_PATH_SGMT_FST);
        PATH_SGMT_FST = getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(path_sgmt_fst);

        String path_sgmt_lst = config.getStr(id + "." + PROP_PATH_SGMT_LST);
        PATH_SGMT_LST = getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(path_sgmt_lst);

        for (String[] path_sgmt_next: config.getStrSeqArr(id + "." + PROP_PATH_SGMT_NXT)) {
            RAIL_SGMT sgmt0 = getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(path_sgmt_next[0]);
            RAIL_SGMT sgmt1 = getModel().get_RAIL_SGMT_repo().get_RAIL_SGMT(path_sgmt_next[1]);
            PATH_SGMT_NXT.put(sgmt0, sgmt1);
        }

        for (String path_excl: config.getStrArrOpt(id + "." + PROP_PATH_EXCL)) {
            PATH_EXCL.add(repo.get_PATH(path_excl));
        }
    }
}
