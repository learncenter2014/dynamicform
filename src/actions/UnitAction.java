package actions;

import bl.beans.UnitBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.UnitBusiness;
import org.apache.commons.lang.StringUtils;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.List;

/**
 * Created by pli on 14-7-19.
 */
public class UnitAction extends BaseTableAction<UnitBusiness> {
    private UnitBean unit;

    public UnitBean getUnit() {
        return unit;
    }

    public void setUnit(UnitBean unit) {
        this.unit = unit;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/unit";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "单位").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "编码"));
        return init;
    }

    @Override
    public String save() throws Exception {
        BusinessResult result = null;
        if (StringUtils.isEmpty(unit.getId())) {
            result = getBusiness().createLeaf(unit);
        } else {
            result = getBusiness().updateLeaf(unit, unit);
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
        unit = (UnitBean) getBusiness().getLeaf(getId()).getResponseData();
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
        unit = new UnitBean();
        return SUCCESS;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>系统管理</li><li class=\"active\"><a href=\"" + getRequest().getContextPath() + "/unit/index.action\">单位管理</a></li></ul>";
    }
}
