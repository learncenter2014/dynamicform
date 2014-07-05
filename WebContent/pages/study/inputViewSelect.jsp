<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <title>inputViewSelect.jsp</title>
</head>
<body>
<section>
    <form action="${rootPath}/dataInput/inputViewSelectSubmit.action" class="form-horizontal">
        <s:hidden name="studyId" />
        <s:hidden name="participantId" />

        <!-- page start-->
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <header class="panel-heading">
                        请选择需要添加的页面
                    </header>
                    <div class="panel-body">
                        <s:iterator value="viewBeanList" var="view">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="viewId" value="${view.id}"> ${view.name}
                                </label>
                            </div>
                        </s:iterator>
                    </div>
                </section>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <div class="panel-body">
                        <button type="submit" class="btn btn-info">确认</button>
                    </div>
                </section>
            </div>
        </div>
    </form>
    <!-- page end-->
</section>
</body>
</html>
