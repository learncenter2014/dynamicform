<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <title>
        <s:if test="view.id.length() > 0">
            修改页面
        </s:if>
        <s:else>
            添加页面
        </s:else>
    </title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="view.id.length() > 0">
            修改页面
        </s:if>
        <s:else>
            添加页面
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="viewForm" class="form-horizontal tasi-form" action="${rootPath}/view/save.action" method="post">
            <input name="view.id" type="hidden" value="${view.id}"/>
            <input name="view.studyId" type="hidden" value="${view.studyId}"/>
            <input name="studyId" type="hidden" value="${view.studyId}"/>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">序号</label>
                <div class="col-lg-6">
                    <input type="text" placeholder="序号" name="view.sequence" class="form-control"
                           required="required" value="${view.sequence}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">页面名称</label>
                <div class="col-lg-6">
                    <input type="text" placeholder="页面名称" name="view.name" class="form-control"
                           required="required" value="${view.name}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">页面描述</label>
                <div class="col-lg-6">
                    <textarea name="view.description" type="text" value="${view.description}" class="form-control" required="required" placeholder="请输入页面描述"></textarea>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">页面类型</label>
                <div class="col-lg-6">
                    <s:select cssClass="form-control" name="view.type" list="#{0:'随访基线',1:'随访录入'}" value="view.type"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-6">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="${rootPath}/view/index.action?studyId=${studyId}">
                      <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>

        </form>
    </div>
</section>
</body>
</html>