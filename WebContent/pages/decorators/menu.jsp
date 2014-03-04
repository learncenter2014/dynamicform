<%@ include file="/pages/commonHeader.jsp"%>
  <section id="container" >

      <!--sidebar start-->
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
                  <li>
                      <a  href="template/dynamicform.action">
                          <i class="fa fa-tasks"></i>
                          <span>模版管理 </span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>

                  <li>
                      <a  href="page/pagelist.action">
                          <i class="fa fa-tasks"></i>
                          <span>页面构建 </span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>

                  <li>
                      <a  href="user/index.action">
                          <i class="fa fa-tasks"></i>
                          <span>用户管理 </span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>
                  <li>
                      <a  href="patient/index.action">
                          <i class="fa fa-tasks"></i>
                          <span>患者管理 </span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>

                  <li>
                      <a  href="datainput/dataPageInput.action?patientId=patient&pageName=patient">
                          <i class="fa fa-th"></i>
                          <span>病例管理</span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>
                  <li>
                      <a  href="datainput/dataPageInput.action?patientId=patient&pageName=followup">
                          <i class="fa fa-th"></i>
                          <span>随访管理</span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>
                  
                  <li>
                      <a  href="">
                          <i class="fa fa-bar-chart-o"></i>
                          <span>数据报表</span>
                          <span class="label label-danger pull-right mail-info"></span>
                      </a>
                  </li>
                  <li>
                      <a  href="pages/menu_loginout/lock_screen.jsp">
                          <i class="fa fa-laptop"></i>
                          <span>锁屏</span>
                      </a>
                  </li>
                  <li>
                      <a  href="logout.action">
                          <i class="fa fa-user"></i>
                          <span>用户退出</span>
                      </a>
                  </li>

                  <!--multi level menu end-->

              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
  </section>