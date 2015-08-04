package common;

/**
 * The Class PaginationData.
 */
public class PaginationData {

    /** The size. */
    private int size;

    /** The page. */
    private int page;

    /**
     * Instantiates a new pagination data.
     */
    public PaginationData() {

    }

    /**
     * Instantiates a new pagination data.
     *
     * @param size the size
     * @param page the page
     */
    public PaginationData(final int sizeValue, final int pageValue) {
        this.size = sizeValue;
        this.page = pageValue;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size
     *            the new size
     */
    public void setSize(final int sizeValue) {
        this.size = sizeValue;
    }

    /**
     * Gets the page.
     *
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the page.
     *
     * @param page
     *            the new page
     */
    public void setPage(final int pageValue) {
        this.page = pageValue;
    }
}
