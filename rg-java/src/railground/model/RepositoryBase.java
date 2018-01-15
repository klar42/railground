package railground.model;

/**
 * Model element repository.
 * @author tofische
 */
abstract class RepositoryBase {

    /** Model this repository belongs to. */
    protected ModelImpl model;

    /** Configuration this repository shall be initialized from. */
    protected Config config;

    /**
     * Create the repository for the given model.
     * @param model Model this repository belongs to.
     * @param config Configuration this repository shall be initialized from.
     */
    protected RepositoryBase(ModelImpl model, Config config) {
        this.model = model;
        this.config = config;
    }

    /**
     * Get the model this repository belongs to.
     * @return Model.
     */
    protected ModelImpl getModel() {
        return model;
    }

    /**
     * Get the configuration this repository shall be initialized from.
     * @return Configuration this repository shall be initialized from.
     */
    protected Config getConfig() {
        return config;
    }
}
