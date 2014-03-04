<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp"%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="img/favicon.png">

    <style type="text/css">
		 table tbody tr.even.row_selected td{
			background-color: #B0BED9 !important;
		 }
    </style>
    <!--external css-->
    <title>User Edit</title>
  </head>
<body>

<!--main content start-->
  <section class="panel">
    <header class="panel-heading">
        User Edit
    </header>
     <form role="form" class="form-horizontal tasi-form" action="<%=request.getContextPath() %>/user/save.action">
         <div class="form-group has-success">
             <label class="col-lg-2 control-label">Username</label>
             <div class="col-lg-10">
                 <input name="user.id" type="hidden" value="${user.id}"/>
                 <input type="text" placeholder="" id="f-name" name="user.name" class="form-control" required="required" value="${user.name}">
                 <p class="help-block">Please input username</p>
             </div>
         </div>
         <div class="form-group has-success">
             <label for="lock" class="control-label col-lg-2 col-sm-3">Lock</label>
             <div class="col-lg-10 col-sm-9">
                 <input  type="checkbox" style="width: 20px" class="checkbox form-control" id="lock" name="user.lock" value="1"
                    <s:if test="user.lock==1">
	                  checked="checked"
	                 </s:if>
                 />
             </div>
         </div>
         <div class="form-group has-error">
             <label class="col-lg-2 control-label">Sex</label>
             <div class="col-lg-10">
                 <select class="form-control m-bot15" name="user.sex">
	                 <option value="1"
	                  <s:if test="user.sex==1">
	                  selected="selected"
	                 </s:if>
	                 >Male</option>
	                 <option value="2"
	                 <s:if test="user.sex==2">
	                  selected="selected"
	                 </s:if>
	                 >Female</option>
	               </select>
                 <p class="help-block">Please select sex</p>
             </div>
         </div>

         <div class="form-group has-error">
             <label for="password" class="control-label col-lg-2">Password</label>
           <div class="col-lg-10">
               <input class="form-control " id="password" name="user.password" type="password" />
           </div>
         </div>

         <div class="form-group has-error">
            <label for="confirm_password" class="control-label col-lg-2">Confirm Password</label>
           <div class="col-lg-10">
               <input class="form-control " id="confirm_password" name="confirm_password" type="password" />
           </div>
         </div>

         <div class="form-group has-warning">
             <label class="col-lg-2 control-label">Email</label>
             <div class="col-lg-10">
                 <input type="email" placeholder="" name="user.email"  id="email2" class="form-control" value="${user.email}">
                 <p class="help-block">Please input email</p>
             </div>
         </div>

        <div class="form-group has-warning">
             <label class="col-lg-2 control-label">CellPhone</label>
             <div class="col-lg-10">
                 <input  placeholder="" name="user.cellPhone"  class="form-control" value="${user.cellPhone}">
                 <p class="help-block">Please input cellphone</p>
             </div>
         </div>

         <div class="form-group">
             <div class="col-lg-offset-2 col-lg-10">
                 <button class="btn btn-danger" type="submit">保存</button>
                 <button class="btn btn-danger" type="button" onclick="history.go(-1);">取消</button>
             </div>
         </div>
     </form>
  </section>
  <!--script for this page-->
  <script type="text/javascript" src="<%=request.getContextPath() %>/jslib/flatlab/js/jquery.validate.min.js"></script>
    <script src="<%=request.getContextPath() %>/jslib/flatlab/js/form-validation-script.js"></script>
</body>
</html>