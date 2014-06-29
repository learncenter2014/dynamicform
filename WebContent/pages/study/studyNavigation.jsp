<div class="panel-body">
    <div class="stepy-tab">
        <ul id="default-titles" class="stepy-titles clearfix">
            <s:iterator value="wizardAction" var="var">
                <s:if test="%{activeWizard==#var[0]}">
                    <li class="current-step">
                        <s:a href="%{'study/'+#var[0]+'.action?activeWizard='+#var[0] +'&id='+id}">
                            <s:param name="activeWizard" value="#var[0]"/>
                            <div><s:property value="%{#var[1]}"/></div>
                        </s:a>
                    </li>
                </s:if>
                <s:else>
                    <li id="default-title-0">
                        <s:a href="%{'study/'+#var[0]+'.action?activeWizard='+#var[0] +'&id='+id}">
                            <div><s:property value="%{#var[1]}"/>
                            </div>
                        </s:a>
                    </li>
                </s:else>
            </s:iterator>
        </ul>
    </div>
</div>
