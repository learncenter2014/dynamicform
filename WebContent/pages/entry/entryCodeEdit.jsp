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
            修改编码
        </s:if>
        <s:else>
            添加编码
        </s:else>
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="entry.id.length() > 0">
            修改编码
        </s:if>
        <s:else>
            添加编码
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="entryCodeForm" class="form-horizontal tasi-form" action="entryCode/save.action" method="post">
            <input name="entryCode.id" type="hidden" value="${entryCode.id}"/>
            <input name="entryCode.entryId" type="hidden" value="${entryCode.entryId}"/>
            <input name="entryId" type="hidden" value="${entryCode.entryId}"/>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">名称</label>
                <div class="col-lg-10">
                    <input type="text" placeholder="名称" name="entryCode.name" class="form-control"
                           required="required" value="${entryCode.name}"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">缩写</label>
                <div class="col-lg-10">
                    <input name="entryCode.abbreviation" type="text" value="${entryCode.abbreviation}" class="form-control"
                           required="required" placeholder="请输入缩写"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">标识</label>
                <div class="col-lg-10">
                    <input name="entryCode.key" type="text" value="${entryCode.key}" class="form-control"
                           required="required" placeholder="请输入缩写"/>
                </div>
            </div>
            <div class="form-group has-error">
                <label class="col-lg-2 control-label">值</label>
                <div class="col-lg-10">
                    <input name="entryCode.value" type="text" value="${entryCode.value}" class="form-control"
                           required="required" placeholder="请输入缩写"/>
                </div>
            </div>


            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="entryCode/index.action?entryId=${entryId}">
                        <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>