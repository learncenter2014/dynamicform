package bl.common;

public interface BusinessInterface<F extends BeanContext, L extends BeanContext> {

    /**
     * Construct a Leaf bean of the appropriate type.
     */
    public L constructLeafBean();

    /**
     * Business method to create a new Leaf.
     * 
     * @param ctx
     *            The request context.
     * @return The result of the business operation.
     */
    public BusinessResult createLeaf(L genLeafBean);

    /**
     * Business method to get a Leaf with string UID type.
     * 
     * @param ctx
     *            The request context.
     * @return The result of the business operation.
     */
    public BusinessResult getLeaf(String objectId);

  
    /**
     * Business method to get a Leaf with string UID type.
     * 
     * @param ctx
     *            The request context.
     * @return The result of the business operation.
     */
    public BusinessResult getLeafByName(String name);
    
    /**
     * Business method to delete a Leaf.
     * 
     * @param ctx
     *            The request context.
     * @return The result of the business operation.
     */
    public BusinessResult deleteLeaf(String objectId);


    /**
     * Business method to update a Leaf.
     * 
     * @param ctx
     *            The request context.
     * @return The result of the business operation.
     */
    public BusinessResult updateLeaf(L origBean, L newBean);

    /**
     * Business method to get all of the Leaves in the system.
     * 
     * @param ctx
     *            The request context.
     * @return The result of the business operation.
     */
    public BusinessResult getAllLeaves();
    
}
