package actions;

import bl.beans.EntryBean;
import bl.common.BusinessResult;
import bl.mongobus.EntryBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * Created by peter on 14-6-21.
 */
public class EntryAction extends BaseTableAction<EntryBusiness> {

    private EntryBean entry;
    private String documentId;

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/entry";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "实体名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("entryName", "实体变量名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("abbreviation", "实体缩写").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("elementType", "实体类型"));
        init.getAoColumns().add(new TableHeaderVo("createTime", "创建时间"));
        return init;
    }

    @Override
    public String save() throws Exception {
        BusinessResult result = null;
        if (StringUtils.isEmpty(entry.getId())) {
            result = getBusiness().createLeaf(entry);
        } else {
            result = getBusiness().updateLeaf(entry, entry);
        }
        if (result != null && result.getErrors().size() > 0) {
            for (Object error : result.getErrors()) {
                addActionError(error.toString());
            }
            return INPUT;
        }
        if (result != null && result.getMessages().size() > 0) {
            for (Object message : result.getMessages()) {
                addActionMessage(message.toString());
            }
            return SUCCESS;
        }
        return SUCCESS;
    }

    public String edit() {
        entry = (EntryBean) getBusiness().getLeaf(getId()).getResponseData();
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getIds() != null) {
            for (String id : getIds()) {
                getBusiness().deleteLeaf(id);
            }
        }
        return SUCCESS;
    }

    @Override
    public String add() {
        entry = new EntryBean();
        return SUCCESS;
    }
    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>系统模块</li><li class=\"active\">实体</li></ul>";
    }

    public EntryBean getEntry() {
        return entry;
    }

    public void setEntry(EntryBean entry) {
        this.entry = entry;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
