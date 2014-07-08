package actions;

import bl.beans.DocumentBean;
import bl.common.BusinessResult;
import bl.mongobus.DocumentBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.List;

/**
 * Created by wangronghua on 14-6-19.
 */
public class DocumentAction extends BaseTableAction<DocumentBusiness> {

    private DocumentBean document;

    @Override
    public String getCustomJsp() {
        return "/pages/document/documentPost.jsp";
    };

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/document";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("code", "模块代码").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "模块名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("abbreviation", "模块缩写").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("type", "类型").addSearchOptions(new String[][] { { "0", "1"}, { "无子元素", "有子元素"} }).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("standardCategory", "标准分类").addSearchOptions(new String[][] { { "0", "1","2"}, { "CDISC", "机构标准","非标准"} }).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("columnCount", "显示列数"));
        init.getAoColumns().add(new TableHeaderVo("createTime", "创建时间"));
        return init;
    }

    @Override
    public String save() throws Exception {
        BusinessResult result = null;
        if (StringUtils.isEmpty(document.getId())) {
            result = getBusiness().createLeaf(document);
        } else {
            DocumentBean origBean = (DocumentBean)getBusiness().getLeaf(document.getId()).getResponseData();
            if(null == origBean) {
              addActionError("未找到对应的模块！");
              return INPUT;
            }
            result = getBusiness().updateLeaf(origBean, document);
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
        document = (DocumentBean) getBusiness().getLeaf(getId()).getResponseData();
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
        document = new DocumentBean();
        return SUCCESS;
    }
    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>随访设计</li><li class=\"active\"><a href=\"document/index.action\">系统模块</a></li></ul>";
    }

    public DocumentBean getDocument() {
        return document;
    }

    public void setDocument(DocumentBean document) {
        this.document = document;
    }

}
