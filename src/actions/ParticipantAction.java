package actions;

import bl.mongobus.ParticipantBusiness;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * Created by wangronghua on 14-6-29.
 */
public class ParticipantAction extends BaseTableAction<ParticipantBusiness> {

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/participant";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/participant.js";
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>随访执行</li><li class=\"active\"><a href=\"participant/index.action\">随访对象</a></li></ul>";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("registerNo", "住院/门诊号").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("name", "姓名").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("birthday", "出生年月"));
        init.getAoColumns().add(new TableHeaderVo("dischargeDate", "出院时间"));
        init.getAoColumns().add(new TableHeaderVo("diagnosis", "诊断"));
        init.getAoColumns().add(new TableHeaderVo("studyPlanId", "随访方案"));
        init.getAoColumns().add(new TableHeaderVo("latestFollowUpDate", "最近一次随访时间"));
        return init;
    }
}
