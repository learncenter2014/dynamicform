<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <style type="text/css">
        table tbody tr.even.row_selected td{
            background-color: #B0BED9 !important;
        }
    </style>
    <!--external css-->
    <title>
        <s:if test="entry.id.length() > 0">
            修改实体
        </s:if>
        <s:else>
            添加实体
        </s:else>
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="entry.id.length() > 0">
            修改实体
        </s:if>
        <s:else>
            添加实体
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="entryForm" class="form-horizontal tasi-form" action="${rootPath}/entry/save.action" method="post">
            <input name="entry.id" type="hidden" value="${entry.id}"/>
            <input name="entry.documentId" type="hidden" value="${entry.documentId}"/>
            <input name="documentId" type="hidden" value="${entry.documentId}"/>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">实体名称</label>
                <div class="col-lg-6">
                    <input type="text" placeholder="实体名称" name="entry.name" class="form-control"
                           required="required" value="${entry.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">实体编码</label>
                <div class="col-lg-6">
                    <input type="text" placeholder="实体编码" name="entry.code" class="form-control"
                           required="required" value="${entry.code}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">实体英文名称</label>
                <div class="col-lg-6">
                    <input name="entry.entryName" type="text" value="${entry.entryName}" class="form-control" required="required" placeholder="请输入实体变量名称"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">实体缩写</label>
                <div class="col-lg-6">
                    <input name="entry.abbreviation" type="text" value="${entry.abbreviation}" class="form-control" required="required" placeholder="请输入实体缩写"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">元素类型</label>
                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="entry.elementType" list="#{0:'定性',1:'定量'}" value="entry.elementType"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">数据类型</label>
                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="entry.dataType" list="#{0:'字符型',1:'数字型',2:'日期型'}" value="entry.dataType"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">元素归类</label>
                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="entry.subElementType" list="#{0:'主元素',1:'子元数'}" value="entry.subElementType"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">表单元素</label>
                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="entry.htmlType" list="#{0:'标签',1:'文本框',2:'文本域',3:'下拉框',4:'复选框',5:'单选框'}" value="entry.htmlType"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">实体缺省值</label>
                <div class="col-lg-6">
                    <input name="entry.defaultValue" type="text" value="${entry.defaultValue}" class="form-control" placeholder="请输入实体缺省值"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">标准实体</label>
                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="entry.standardEntry" list="#{0:'系统缺省',1:'国际标准',2:'用户临时创建'}" value="entry.standardEntry"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">最小值</label>
                <div class="col-lg-6">
                    <input name="entry.minValue" type="text" value="${entry.minValue}" class="form-control" required="true" placeholder="请输入实体最小值"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">最大值</label>
                <div class="col-lg-6">
                    <input name="entry.maxValue" type="text" value="${entry.maxValue}" class="form-control" required="true" placeholder="请输入实体最大值"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">最大长度</label>
                <div class="col-lg-6">
                    <input name="entry.maxLength" type="text" value="${entry.maxLength}" class="form-control" required="true" placeholder="请输入最大长度"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">最大显示长度</label>
                <div class="col-lg-6">
                    <input name="entry.size" type="text" value="${entry.size}" class="form-control" placeholder="请输入最大显示长度"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">正则表达式</label>
                <div class="col-lg-6">
                    <input name="entry.regularExpression" type="text" value="${entry.regularExpression}" class="form-control" placeholder="请输入正则表达式"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-6">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="entry/index.action?documentId=${documentId}">
                      <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>