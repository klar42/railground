package railground.model;

/**
 * Model element.
 * @param <REPO> Model element repository type.
 * @author tofische
 */
abstract class ModelElementBase<REPO extends RepositoryBase> {

    /** Model element ID. */
    protected final String id;

    /** Repository this model element belongs to. */
    protected final REPO repo;

    /**
     * Create the model element belonging to the given repository.
     * @param id Model element ID
     * @param repo Repository.
     */
    protected ModelElementBase(String id, REPO repo) {
        this.id = id;
        this.repo = repo;
    }

    /**
     * Get the model element ID.
     * @return Model element ID.
     */
    public String getId() {
        return id;
    }
    /**
     * Get the repository this model element belongs to.
     * @return Repository.
     */
    protected REPO getRepository() {
        return repo;
    }

    /**
     * Get the model this model element belongs to.
     * @return Model.
     */
    protected ModelImpl getModel() {
        return repo.getModel();
    }

    @Override
    public String toString() {
        return id;
    }
}
