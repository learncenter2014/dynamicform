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
        <form id="documentForm" class="form-horizontal tasi-form" action="document/save.action" method="post">
            <input name="document.id" type="hidden" value="${document.id}"/>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">模版名称</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="模板名称" name="document.name" class="form-control"
                           required="required" value="${document.name}"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">模板代码</label>
                <div class="col-lg-10">
                    <input name="document.code" type="text" value="${document.code}" class="form-control" required="required" placeholder="请输入模板代码"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">模板缩写</label>
                <div class="col-lg-10">
                    <input name="document.abbreviation" type="text" value="${document.abbreviation}" class="form-control" required="required" placeholder="请输入模板缩写"/>
                </div>
            </div>


            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>

                    <button class="btn btn-info" type="button" onclick="window.location.href='${rootPath}/document/index.action'">取消</button>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>