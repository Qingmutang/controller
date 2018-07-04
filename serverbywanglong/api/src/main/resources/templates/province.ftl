[
<#if provinces??>
    <#list provinces as p>
        {
        "id": <#if p.id??>${p.id?c}</#if>,
        "name": "<#if p.name??>${p.name}</#if>",
        "areaCode": "<#if p.areaCode??>${p.areaCode}</#if>"
        }<#if p_has_next>,
        </#if>
    </#list>
</#if>
]