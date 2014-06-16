package bl.common;

/**
 * Created by Administrator on 14-3-14.
 */
public class SpecPaginationContext {

    /**
     * The size to use in the limit clause.
     */
    private int limitSize;

    /**
     * The offset to use in the limit clause.
     */
    private int limitOffset;

    public int getLimitSize() {
        return limitSize;
    }

    public void setLimitSize(int limitSize) {
        this.limitSize = limitSize;
    }

    public int getLimitOffset() {
        return limitOffset;
    }

    public void setLimitOffset(int limitOffset) {
        this.limitOffset = limitOffset;
    }
}
