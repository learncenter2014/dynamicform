<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <style type="text/css">
        table tbody tr.even.row_selected td{
            background-color: #B0BED9 !important;
        }
    </style>

    <script type="text/javascript">
        function handleCancel() {
            window.location.href = "department/index.action"
        }
    </script>
    <!--external css-->
    <title>
    	<s:if test="department.id.length() > 0">
			修改部门信息
		</s:if>
		<s:else>
			添加部门信息
		</s:else></title>
</head>
<body>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
       <s:if test="department.id.length() > 0">
			修改部门信息
		</s:if>
		<s:else>
			添加部门信息
		</s:else>
    </header>
    <div class="panel-body">
    <s:actionerror/><s:actionmessage/>
    <form id="departmentForm" class="form-horizontal tasi-form" action="${rootPath}/department/save.action">

        <input name="department.id" type="hidden" value="${department.id}"/>

        <div class="form-group has-success">
            <label class="col-lg-2 control-label">科室代码</label>
            <div class="col-lg-6">
                <input type="text" placeholder="科室代码"  name="department.uniqueDepartmentId" class="form-control" required="required" value="${department.uniqueDepartmentId}">
            </div>
        </div>
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">科室名称</label>
            <div class="col-lg-6">
                <input type="text" placeholder="科室名称"  name="department.deptName" class="form-control" required="required" value="${department.deptName}">
            </div>
        </div>
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">随访角色</label>
            <div class="col-lg-6">
                <select class="form-control m-bot15" name="department.fuRole">
                    <option value="0"
                    <s:if test="department.fuRole==0">
                        selected="selected"
                    </s:if>
                    >未分配</option>
                    <option value="1"
                    <s:if test="department.fuRole==1">
                        selected="selected"
                    </s:if>
                    >管理</option>
                    <option value="2"
                    <s:if test="department.fuRole==2">
                        selected="selected"
                    </s:if>
                    >执行</option>
                </select>
            </div>
        </div>
		<div class="form-group has-success">
            <label class="col-lg-2 control-label">外线前缀</label>
            <div class="col-lg-6">
                <input type="text" placeholder="外线前缀" name="department.outsideThePrefix" class="form-control"  value="${department.outsideThePrefix}">
            </div>
        </div>
		<div class="form-group has-success">
            <label class="col-lg-2 control-label">拨打长途加号码</label>
            <div class="col-lg-6">
                <input type="text" placeholder="拨打长途加号码" name="department.longDistanceThePrefix" class="form-control"  value="${department.longDistanceThePrefix}">
            </div>
        </div>
        
        <div class="form-group has-success">
            <label class="col-lg-2 control-label">是否有长途</label>
            <div class="col-lg-6">
                <select class="form-control m-bot15" name="department.hasLongDistance">
                    <option value="0"
                    <s:if test="department.hasLongDistance==0">
                        selected="selected"
                    </s:if>
                    >有长途</option>
                    <option value="1"
                    <s:if test="department.hasLongDistance==1">
                        selected="selected"
                    </s:if>
                    >无长途</option>
                </select>
            </div>
        </div>
        <div class="form-group">
                <div class="col-lg-offset-2 col-lg-6">
                    <button class="btn btn-info" type="submit">保存</button>

                    <a href="department/index.action">
                      <div class="btn btn-info">取消</div>
                    </a>
                </div>
            </div>
    </form>
    </div>
</section>
<!--script for this page-->
<script type="text/javascript" src="<%=request.getContextPath() %>/jslib/flatlab/js/jquery.validate.min.js"></script>
<script src="<%=request.getContextPath() %>/jslib/flatlab/js/form-validation-script.js"></script>
</body>
</html>