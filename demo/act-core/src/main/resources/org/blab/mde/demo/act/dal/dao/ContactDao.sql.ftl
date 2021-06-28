import org.blab.mde.demo.act.dal.dao.Common.common
import org.blab.mde.demo.act.dal.dao.Common.commonValues

insert ::= <<
    insert into contact
        (
            <common>
            <#if name ? exists> ,name </#if>
            <#if qq ? exists> ,qq </#if>
            <#if mobile ? exists> ,mobile </#if>
            <#if adress ? exists> ,adress </#if>

        )
    values
        (
          <commonValues>
          <insertValues>
        )
>>


insertValues ::= <<
          <#if name ? exists> ,:name </#if>
          <#if qq ? exists> ,:qq </#if>
          <#if mobile ? exists> ,:mobile </#if>
          <#if adress ? exists> ,:adress </#if>

>>

selectById ::= <<
   select * from contact where id = :id
>>
