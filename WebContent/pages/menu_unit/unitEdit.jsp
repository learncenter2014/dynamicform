<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
    <style type="text/css">
        table tbody tr.even.row_selected td {
            background-color: #B0BED9 !important;
        }
    </style>
    <!--external css-->
    <title>
        <s:if test="entry.id.length() > 0">
            修改单位
        </s:if>
        <s:else>
            添加单位
        </s:else>
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="entry.id.length() > 0">
            修改单位
        </s:if>
        <s:else>
            添加单位
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="unitForm" class="form-horizontal tasi-form" action="${rootPath}/unit/save.action" method="post">
            <input name="unit.id" type="hidden" value="${unit.id}"/>

            <div class="form-group has-error">
                <label class="col-lg-2 control-label">名称</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="名称" name="unit.name" class="form-control"
                           required="required" value="${unit.name}"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">编码</label>

                <div class="col-lg-10">
                    <input name="unit.code" type="text" value="${unit.code}" class="form-control"
                           required="required" placeholder="请输入缩写"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="${rootPath}/unit/index.action">
                        <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>