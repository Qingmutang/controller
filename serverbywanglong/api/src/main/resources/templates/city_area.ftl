<#if cityAreas??>
    {
        <#if cityAreas?keys??>
            <#list cityAreas?keys as key>
                "${key}":<#if cityAreas[key]??>
                [
                    <#list cityAreas[key] as area>
                        {
                            "id": <#if area.id??>${area.id?c}</#if>,
                            "name": "<#if area.name??>${area.name}</#if>",
                            "areaCode": "<#if area.areaCode??>${area.areaCode}</#if>"
                        }<#if area_has_next>
                            ,
                        </#if>
                    </#list>
                ]<#if key_has_next>
                    ,
                </#if>
                </#if>
            </#list>
        </#if>
    }
</#if>
