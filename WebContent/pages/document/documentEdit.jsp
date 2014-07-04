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
        <s:if test="document.id.length() > 0">
            修改模块
        </s:if>
        <s:else>
            添加模块
        </s:else>
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="document.id.length() > 0">
            修改模块
        </s:if>
        <s:else>
            添加模块
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="documentForm" class="form-horizontal tasi-form" action="${rootPath}/document/save.action" method="post">
            <input name="document.id" type="hidden" value="${document.id}"/>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">模块名称</label>
                <div class="col-lg-6">
                    <input type="text" placeholder="模块名称" name="document.name" class="form-control"
                           required="required" value="${document.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">英文名称</label>
                <div class="col-lg-6">
                    <input type="text" placeholder="英文名称" name="document.englishName" class="form-control"
                           required="required" value="${document.englishName}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">模块代码</label>
                <div class="col-lg-6">
                    <input name="document.code" type="text" value="${document.code}" class="form-control"
                           required="required" placeholder="模块代码"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">模块缩写</label>
                <div class="col-lg-6">
                    <input name="document.abbreviation" type="text" value="${document.abbreviation}" class="form-control"
                           required="required" placeholder="模块缩写"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">模块类型</label>
                <div class="col-lg-6">
                    <%--<input name="document.columnCount" type="text" value="${document.columnCount}" --%>
                    <%--class="form-control" required="required" placeholder="请输入显示列数"/>--%>
                    <s:select list="#{0:'无子元素',1:'有子元素'}" cssClass="form-control m-bot15"
                              name="document.type" value="document.type"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">显示列数</label>
                <div class="col-lg-6">
                    <s:select list="#{1:'一列',2:'二列',3:'三列',4:'四列'}" cssClass="form-control m-bot15"
                              name="document.columnCount" value="document.columnCount"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">显示名称</label>
                <div class="col-lg-6">
                    <%--<input name="document.columnCount" type="text" value="${document.columnCount}" --%>
                    <%--class="form-control" required="required" placeholder="请输入显示列数"/>--%>
                    <s:select list="#{true:'是',false:'否'}" cssClass="form-control m-bot15"
                              name="document.displayNameOrNot" value="document.displayNameOrNot"/>

                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">标准分类</label>
                <div class="col-lg-6">
                    <%--<input name="document.columnCount" type="text" value="${document.columnCount}" --%>
                    <%--class="form-control" required="required" placeholder="请输入显示列数"/>--%>
                    <s:select list="#{0:'CDISC',1:'机构标准',2:'非标准'}" cssClass="form-control m-bot15"
                              name="document.standardCategory" value="document.standardCategory"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">备注</label>
                <div class="col-lg-6">
                    <%--<input name="document.columnCount" type="text" value="${document.columnCount}" --%>
                    <%--class="form-control" required="required" placeholder="请输入显示列数"/>--%>
                    <textarea name="document.description" rows="5" cols="60" class="form-control">${document.description}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-6">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="document/index.action">
                      <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>