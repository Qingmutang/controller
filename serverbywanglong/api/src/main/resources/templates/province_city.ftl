<#if provinceCities??>
    {
        <#if provinceCities?keys??>
            <#list provinceCities?keys as key>
                "${key}":<#if provinceCities[key]??>
                [
                    <#list provinceCities[key] as city>
                        {
                            "id": <#if city.id??>${city.id?c}</#if>,
                            "name": "<#if city.name??>${city.name}</#if>",
                            "areaCode": "<#if city.areaCode??>${city.areaCode}</#if>"
                        }<#if city_has_next>
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
