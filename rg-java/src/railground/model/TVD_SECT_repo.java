package railground.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Track vacancy detector section repository.
 * @author tofische
 */
public class TVD_SECT_repo extends RepositoryBase {

    /** Configuration property. */
    private static final String PROP_TVD_SECT = "TVD_SECT";

    /** Enumeration of all TVD sections identified by id. */
    private Map<String, TVD_SECT> TVD_SECT_enum = new HashMap<>();

    /**
     * Create the TVD repository.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    public TVD_SECT_repo(ModelImpl model, Config config) {
        super(model, config);
        for (String railElemId: config.getStrArr(PROP_TVD_SECT)) {
            TVD_SECT tvd_sect = new TVD_SECT(railElemId, this, config);
            TVD_SECT_enum.put(railElemId, tvd_sect);
        }
        checkAxioms();
    }

    /**
     * Check axioms.
     * @throws RuntimeException if any axiom is violated.
     */
    private void checkAxioms() {
        // Rail Segments of different TVD Sections are disjunct.
        boolean TVD_SECT_SGMT_disjunct = true; // ∀ sect1, sect2
                                               // · sect1 ∈ TVD_SECT
                                               // ∧ sect2 ∈ TVD_SECT
                                               // ∧ sect1 ≠ sect2
                                               // ⇒ TVD_SECT_SGMT(sect1) ∩ TVD_SECT_SGMT(sect2) = ∅
        // Each Rail Segment belongs to some TVD Section.
        boolean TVD_SECT_SGMT_all = true; // union(ran(TVD_SECT_SGMT)) = RAIL_SGMT
        // Mapping of TVD Segmens to Rail Sections.
        boolean TVD_SGMT_SECT_value = true; // TVD_SGMT_SECT = {sgmt, sect · sgmt ∈ RAIL_SGMT ∧ sect ∈ TVD_SECT ∧ sgmt ∈ TVD_SECT_SGMT(sect) ∣ sgmt ↦ sect}

        if (!TVD_SECT_SGMT_disjunct || !TVD_SECT_SGMT_all || !TVD_SGMT_SECT_value) {
            throw new RuntimeException();
        }
    }

    /**
     * Get the TVD Section for the given id.
     * @param id TVD Section id.
     * @return TVD section with the given id.
     */
    public TVD_SECT get_TVD_SECT(String id) {
        return TVD_SECT_enum.get(id);
    }

    public String get_TVD_STATE_CURR() {
        String sep = "";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String key : TVD_SECT_enum.keySet()) {
            String val = TVD_SECT_enum.get(key).get_TVDS_STATE_CURR();
            sb.append(sep);
            sb.append(key);
            sb.append("=");
            sb.append(val);
            sep = ", ";
        }
        sb.append("]");
        return sb.toString();

    }
}
