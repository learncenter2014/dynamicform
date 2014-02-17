<!DOCTYPE html>
<%@ include file="/pages/commonHeader.jsp"%>

<form>
  <table width="100%">
    <tbody>
      <tr>
        <td><label for="x_file">Template Form Lists</label></td>
        <td><select name="x_file" id="x_file">
            <s:iterator value="fileList" var="sup">
              <option value="${sup[1]}">${sup[0]}</option>
            </s:iterator>
        </select></td>
      </tr>
    </tbody>
  </table>
</form>