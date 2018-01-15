package railground.model;

public class ModelImpl implements Model {

    private RAIL_CTOR_repo RAIL_CTOR_repo;
    private RAIL_SGMT_repo RAIL_SGMT_repo;
    private RAIL_TERM_repo RAIL_TERM_repo; // Depends on RAIL_CTOR_repo
    private RAIL_ELEM_repo RAIL_ELEM_repo; // Depends on RAIL_CTOR_repo, RAIL_SGMT_repo
    private RAIL_LINK_repo RAIL_LINK_repo; // Depends on RAIL_CTOR_repo
    private TVD_SECT_repo TVD_SECT_repo;   // Depends on RAIL_SGMT_repo
    private SIGNAL_repo SIGNAL_repo;
    private PATH_repo PATH_repo;           // Depends on RAIL_ELEM_repo

    public ModelImpl(Config config) {
        // Repositories which are initialized during the initialization of other repositories must be created first
        RAIL_CTOR_repo = new RAIL_CTOR_repo(this, config);
        RAIL_SGMT_repo = new RAIL_SGMT_repo(this, config);

        RAIL_TERM_repo = new RAIL_TERM_repo(this, config);
        RAIL_ELEM_repo = new RAIL_ELEM_repo(this, config);
        RAIL_LINK_repo = new RAIL_LINK_repo(this, config);
        TVD_SECT_repo = new TVD_SECT_repo(this, config);
        SIGNAL_repo = new SIGNAL_repo(this, config);
        PATH_repo = new PATH_repo(this, config);
    }

    protected RAIL_TERM_repo get_RAIL_TERM_repo() {
        return RAIL_TERM_repo;
    }

    protected RAIL_ELEM_repo get_RAIL_ELEM_repo() {
        return RAIL_ELEM_repo;
    }

    protected RAIL_CTOR_repo get_RAIL_CTOR_repo() {
        return RAIL_CTOR_repo;
    }

    protected RAIL_SGMT_repo get_RAIL_SGMT_repo() {
        return RAIL_SGMT_repo;
    }

    protected RAIL_LINK_repo get_RAIL_LINK_repo() {
        return RAIL_LINK_repo;
    }

    protected TVD_SECT_repo get_TVD_SECT_repo() {
        return TVD_SECT_repo;
    }

    protected SIGNAL_repo get_SIGNAL_repo() {
        return SIGNAL_repo;
    }

    protected PATH_repo get_PATH_repo() {
        return PATH_repo;
    }


    // Variables

    @Override
    public String RAIL_ELEM_POS_CURR() {
        return "RAIL_ELEM_POS_CURR:" + RAIL_ELEM_repo.get_RAIL_ELEM_POS_CURR();
    }

    @Override
    public String PATH_CURR() {
        return "PATH_CURR:" + PATH_repo.get_PATH_CURR();
    }

    @Override
    public String PATH_REQ() {
        return "PATH_REQ:" + PATH_repo.get_PATH_REQ();
    }

    @Override
    public String PATH_REL() {
        return "PATH_REL:" + PATH_repo.get_PATH_REL();
    }

    @Override
    public String TVD_STATE_CURR() {
        return "TVD_STATE_CURR:" + TVD_SECT_repo.get_TVD_STATE_CURR();
    }

    @Override
    public String SIGNAL_ASPECT_CURR() {
        return "SIGNAL_ASPECT_CURR:" + SIGNAL_repo.get_SIGNAL_ASPECT_CURR();
    }

    @Override
    public boolean set_RAIL_ELEM_POS_CURR(String elem, String pos) {
        RAIL_ELEM elemObj = RAIL_ELEM_repo.get_RAIL_ELEM(elem);
        RAIL_POS_ENUM posObj = RAIL_POS_ENUM.valueOf(pos);
        return elemObj.set_RAIL_ELEM_POS_CURR(posObj);
    }

    @Override
    public boolean set_RAIL_ELEM_PATH(String elem) {
        RAIL_ELEM elemObj = RAIL_ELEM_repo.get_RAIL_ELEM(elem);
        return elemObj.set_RAIL_ELEM_PATH();
    }

    @Override
    public boolean add_PATH_CURR(String path) {
        PATH pathObj = PATH_repo.get_PATH(path);
        return PATH_repo.add_PATH_CURR(pathObj);
    }

    @Override
    public boolean rem_PATH_CURR(String path) {
        PATH pathObj = PATH_repo.get_PATH(path);
        return PATH_repo.rem_PATH_CURR(pathObj);
    }

    @Override
    public boolean add_PATH_REQ(String path) {
        PATH pathObj = PATH_repo.get_PATH(path);
        return PATH_repo.add_PATH_REQ(pathObj);
    }

    @Override
    public boolean rem_PATH_REQ(String path) {
        PATH pathObj = PATH_repo.get_PATH(path);
        return PATH_repo.rem_PATH_REQ(pathObj);
    }

    @Override
    public boolean set_PATH_REL(String path) {
        PATH pathObj = PATH_repo.get_PATH(path);
        return PATH_repo.set_PATH_REL(pathObj);
    }

    @Override
    public boolean set_TVDS_STATE_CURR(String sect, String stat) {
        TVD_SECT sectObj = TVD_SECT_repo.get_TVD_SECT(sect);
        TVD_STATE_ENUM statObj = TVD_STATE_ENUM.valueOf(stat);
        return sectObj.set_TVDS_STATE_CURR(statObj);
    }

    @Override
    public boolean set_TVDS_STATE_PATH(String sect, String stat) {
        TVD_SECT sectObj = TVD_SECT_repo.get_TVD_SECT(sect);
        TVD_STATE_ENUM statObj = TVD_STATE_ENUM.valueOf(stat);
        return sectObj.set_TVDS_STATE_PATH(statObj);
    }

    @Override
    public boolean set_SIGNAL_ASPECT_PROCEED(String sig, String asp) {
        SIGNAL sigObj = SIGNAL_repo.get_SIGNAL(sig);
        SIGNAL_ASPECT_ENUM aspObj =SIGNAL_ASPECT_ENUM.valueOf(asp);
        return sigObj.set_SIGNAL_ASPECT_PROCEED(aspObj);
    }

    @Override
    public boolean set_SIGNAL_ASPECT_DEFAULT(String sig) {
        SIGNAL sigObj = SIGNAL_repo.get_SIGNAL(sig);
        return sigObj.set_SIGNAL_ASPECT_DEFAULT();
    }
}
