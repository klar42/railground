package railground.model;

/**
 * RailGround model conforming to the corresponding Event-B model.
 * @author tofische
 */
public interface Model {

    // **** Variables ****

    /** Current Element Position of the particular Rail Element. */
    public String RAIL_ELEM_POS_CURR();

    /** Current Paths. */
    public String PATH_CURR();

    /** Requested Paths. */
    public String PATH_REQ();

    /** Paths being released. */
    public String PATH_REL();

    /** Current state of the particular TVD Section. */
    public String TVD_STATE_CURR();

    /** Current Signal Aspect of the particular Signal. */
    public String SIGNAL_ASPECT_CURR();


    // **** Events ****

    /**
     * Set new current Element Position of the particular Rail Element.
     */
    public boolean set_RAIL_ELEM_POS_CURR(String elem, String pos);

    /**
     * Set the proper Element Position of the particular Rail Element which is a part of a Path.
     */
    public boolean set_RAIL_ELEM_PATH(String elem);

    /**
     * Add a new Path.
     */
    public boolean add_PATH_CURR(String path);

    /**
     * Remove an existing Path..
     */
    public boolean rem_PATH_CURR(String path);

    /**
     * Add a requested Path.
     */
    public boolean add_PATH_REQ(String path);

    /**
     * Remove a requested Path.
     */
    public boolean rem_PATH_REQ(String path);

    /**
     * Set a path as being released.
     */
    public boolean set_PATH_REL(String path);

    /**
     * Set new current state of the particular TVD Section which isn't a part of any current Path.
     */
    public boolean set_TVDS_STATE_CURR(String sect, String state);

    /**
     * Set new current state of the particular TVD Section which is a part of some current Path.
     */
    public boolean set_TVDS_STATE_PATH(String sect, String state);
    
    /**
     * Set Signal to some Proceed signal aspect.
     */
    public boolean set_SIGNAL_ASPECT_PROCEED(String sig, String asp);

    /**
     * Set Signal to Default signal aspect.
     */
    public boolean set_SIGNAL_ASPECT_DEFAULT(String sig);
}
