<!DOCTYPE html>
<%@ include file="commonHeader.jsp"%>

<form>
  <table width="100%">
    <tbody>
      <tr>
        <td><label for="x_file">Template Form Lists</label></td>
        <td><select name="x_file" id="x_file" class="fCmb">
            <s:iterator value="fileList" var="sup">
              <option value="${sup}">${sup}</option>
            </s:iterator>
        </select></td>
      </tr>
    </tbody>
  </table>
</form>