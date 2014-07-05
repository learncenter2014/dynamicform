package bl.beans;

/**
 * Created by wangronghua on 14-7-5.
 */
public class DynamicDataBean {

    private String dataRecordId;
    private String tableName;
    private ViewBean view;

    public String getDataRecordId() {
        return dataRecordId;
    }

    public void setDataRecordId(String dataRecordId) {
        this.dataRecordId = dataRecordId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ViewBean getView() {
        return view;
    }

    public void setView(ViewBean view) {
        this.view = view;
    }

}
