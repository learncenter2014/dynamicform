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
        <form id="entryForm" class="form-horizontal tasi-form" action="entry/save.action" method="post">
            <input name="entry.id" type="hidden" value="${entry.id}"/>
            <input name="entry.documentId" type="hidden" value="${entry.documentId}"/>
            <input name="documentId" type="hidden" value="${entry.documentId}"/>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">实体名称</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="实体名称" name="entry.name" class="form-control"
                           required="required" value="${entry.name}"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">entryName</label>
                <div class="col-lg-10">
                    <input name="entry.entryName" type="text" value="${entry.entryName}" class="form-control" required="required" placeholder="请输入实体变量名称"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">实体缩写</label>
                <div class="col-lg-10">
                    <input name="entry.abbreviation" type="text" value="${entry.abbreviation}" class="form-control" required="required" placeholder="请输入实体缩写"/>
                </div>
            </div>


            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="entry/index.action">
                      <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>