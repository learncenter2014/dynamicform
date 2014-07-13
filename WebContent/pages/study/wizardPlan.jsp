<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
    <script src="${rootPath}/js/inlineedittable.js"></script>
</head>
<body>

<!--main content start-->
<section class="panel">
    <div class="panel-body">
        <%@include file="studyNavigation.jsp" %>
        <form id="wizardPlan" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardPlan.action"
              method="post" onsubmit="postForm()">
            <input name="study.id" type="hidden" value="${study.id}"/>
            <input name="activeWizard" type="hidden" value="${activeWizard}"/>

            <div id="hiddenForm"></div>
            <!-- page start-->
            <section class="panel">
                <header class="panel-heading">
                    <h3>随访复诊规则</h3>
                </header>
                <div class="panel-body">
                    <div class="adv-table editable-table ">
                        <div class="form-group has-success">
                            <label class="col-lg-2 control-label">随访基准时间</label>

                            <div class="col-lg-6">
                                <s:radio name="subsequentPlan.startPoint" list="#{0:'出院时间',1:'门诊时间',2:'住院时间'}"
                                         value="subsequentPlan.startPoint"/>
                            </div>
                        </div>
                        <div class="form-group has-success">
                            <label class="col-lg-2 control-label">随访计划列表</label>
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button id="subsequent_new" class="btn green">
                                        添加<i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-lg-10">
                                <table class="table table-striped table-hover table-bordered" id="subsequent">
                                </table>
                            </div>
                        </div>
                        <div class="form-group has-success">
                            <label class="col-lg-2 control-label">顺延设置</label>

                            <div class="col-lg-3">
                                <s:checkbox name="subsequentPlan.skipWeek"
                                            fieldValue="%{subsequentPlan.skipWeek}" label="周六周日"/>周六周日
                            </div>
                            <div class="col-lg-3">
                                <s:checkbox name="subsequentPlan.skipVocation"
                                            fieldValue="%{subsequentPlan.skipVocation}" label="法定节假日"/>法定节假日
                            </div>
                            <div class="col-lg-3">
                                <s:checkbox name="subsequentPlan.matchDeptReservation"
                                            fieldValue="%{subsequentPlan.matchDeptReservation}" label="科室预约"/>科室预约
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- page end-->
            <!-- END JAVASCRIPTS -->
            <script>
                jQuery(document).ready(function () {
                    subsequent = jQuery.editTable.init({tableId: 'subsequent', headerTypes: [
                        {type: 'text', mData: 'sequence', sTitle: "序列"},
                        {type: 'text', mData: 'jiange', sTitle: "时间间隔"},
                        {type: 'select', mData: 'unit', sTitle: "间隔单位", sourceList: [
                            ['月', 'month']
                        ], mRender: function (data, type, full) {
                            if (data != null && data == 'month') {
                                return "月";
                            } else {
                                return "";
                            }
                        }},
                        {type: 'select', mData: 'view', sTitle: "缺省页面", sourceList: [
                            ['页面0', 'value'],
                            ['页面1', 'value']
                        ], mRender: function (data, type, full) {
                            if (data != null) {
                                return "随访页面0";
                            } else {
                                return "";
                            }
                        }}
                    ]});
                    subsequent.fnAddData([
                        {sequence: '11', jiange: '1', unit: 'month', view: '随访页面0'}
                    ]);
                });
            </script>

            <!-- page start-->
            <section class="panel">
                <header class="panel-heading">
                    <h3>随访电话规则</h3>
                </header>
                <div class="panel-body">
                    <div class="adv-table editable-table ">
                        <div class="form-group has-success">
                            <label class="col-lg-2 control-label">随访基准时间</label>

                            <div class="col-lg-6">
                                <s:radio name="telphonePlan.startPoint" list="#{0:'出院时间',1:'门诊时间',2:'住院时间'}"
                                         value="telphonePlan.startPoint"/>
                            </div>
                        </div>
                        <div class="form-group has-success">
                            <label class="col-lg-2 control-label">随访计划列表</label>
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button id="telphone_new" class="btn green">
                                        添加<i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-lg-10">
                                <table class="table table-striped table-hover table-bordered" id="telphone">
                                </table>
                            </div>
                        </div>
                        <div class="form-group has-success">
                            <label class="col-lg-2 control-label">顺延设置</label>

                            <div class="col-lg-3">
                                <s:checkbox name="telphonePlan.skipWeek"
                                            fieldValue="%{telphonePlan.skipWeek}" label="周六周日"/>周六周日
                            </div>
                            <div class="col-lg-3">
                                <s:checkbox name="telphonePlan.skipVocation"
                                            fieldValue="%{telphonePlan.skipVocation}" label="法定节假日"/>法定节假日
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- page end-->
            <!-- END JAVASCRIPTS -->
            <script>
                jQuery(document).ready(function () {
                    telphone = jQuery.editTable.init({tableId: 'telphone', headerTypes: [
                        {type: 'text', mData: 'sequence', sTitle: "序列"},
                        {type: 'text', mData: 'jiange', sTitle: "时间间隔"},
                        {type: 'select', mData: 'unit', sTitle: "间隔单位", sourceList: [
                            ['月', 'month']
                        ], mRender: function (data, type, full) {
                            if (data != null && data == 'month') {
                                return "月";
                            } else {
                                return "";
                            }
                        }},
                        {type: 'select', mData: 'view', sTitle: "缺省页面", sourceList: [
                            ['页面0', 'value'],
                            ['页面1', 'value']
                        ], mRender: function (data, type, full) {
                            if (data != null) {
                                return "随访页面0";
                            } else {
                                return "";
                            }
                        }}
                    ]});
                    telphone.fnAddData([
                        {sequence: '11', jiange: '1', unit: 'month', view: '112'}
                    ]);
                });

                //wrap strut2.0 dynamic data arrays within post form.
                function postForm() {
                    var form = $("#hiddenForm");
                    //removed all hidden element in this exportForm.
                    form.empty();
                    var subsequentData = subsequent.fnGetData();
                    for (var i = 0; i < subsequentData.length; i++) {
                        for (var j in subsequentData[i]) {
                            var hiddenElement = $("<input>");
                            hiddenElement.attr("type", "hidden");
                            hiddenElement.attr("name", "subsequent[" + i + "]." + j);
                            hiddenElement.attr("value", subsequentData[i][j]);
                            form.append(hiddenElement);
                        }
                    }

                    var telphone = telphone.fnGetData();
                    for (var i = 0; i < telphone.length; i++) {
                        for (var j in telphone[i]) {
                            var hiddenElement = $("<input>");
                            hiddenElement.attr("type", "hidden");
                            hiddenElement.attr("name", "telphone[" + i + "]." + j);
                            hiddenElement.attr("value", telphone[i][j]);
                            form.append(hiddenElement);
                        }
                    }
                }
            </script>
            <%@ include file="/pages/study/studyAction.jsp" %>
        </form>
    </div>
</section>
</body>
</html>