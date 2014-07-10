package actions;

import bl.beans.EntryBean;
import bl.common.BusinessResult;
import bl.mongobus.EntryBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;
import vo.table.TableQueryVo;

/**
 * Created by peter on 14-6-21.
 */
public class EntryAction extends BaseTableAction<EntryBusiness> {

    private EntryBean entry;
    private String documentId;


    @Override
    public String getCustomJsp() {
        return "/pages/entry/entryPost.jsp";
    };

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/entry";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("sequence", "序号").disableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "实体名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "实体编码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("englishName", "实体英文名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("subElementType", "元素归类").addSearchOptions(new String[][] { { "0", "1", "2", "3"}, { "主元素", "子元数", "伪参考值主元素", "伪检查值主元素"} }).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("elementType", "实体类型").addSearchOptions(new String[][] { { "0", "1"}, { "定性", "定量"} }).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("standardEntry", "标准分类").addSearchOptions(new String[][] { { "0", "1","2"}, { "CDISC", "机构标准","非标准"} }).enableSearch());

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
        entry.setDocumentId(this.documentId);
        return SUCCESS;
    }
    @Override
    public String getTableTitle() {
        String prefixPath = getRequest().getContextPath()+"/";
        return "<ul class=\"breadcrumb\"><li>随访设计</li><li><a href=\""+prefixPath+"document/index.action\">系统模块</a></li><li class=\"active\"><a href=\""+prefixPath+"entry/index.action?documentId="+this.documentId+"\">实体</a></li></ul>";
    }
    @Override
    public TableQueryVo getModel() {
        TableQueryVo model = super.getModel();
        model.getFilter().put("documentId", this.documentId + "");
        model.getSort().put("sequence","asc");
        return model;
    }

    @Override
    public String getAddButtonParameter(){
        return "subElementType=0&documentId="+this.documentId;
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
