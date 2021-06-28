grammar FT;

options {
	language=Java;
}

@header {
import org.blab.mde.ee.dal.ftl.FTAgg;
import org.blab.mde.ee.dal.ftl.utils.MiscUtil;
}

@members {
public FTAgg ftAgg;

}

interpreter [FTAgg ftAgg]
    : SUB_FT*?
    ;

SUB_FT
		:	'<' ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|'_')* '>'
	;


BIGSTRING_NO_NL
	:	'<%' ( . )*? '%>'
        // %\> is the escape to avoid end of string
	;


COMMENT
    :   '/*' ( . )*? '*/' {skip();}
    ;

LINE_COMMENT
    :	'//' ~('\n'|'\r')* '\r'? '\n' {skip();}
    ;

WS  :	(' '|'\r'|'\t'|'\n') {skip();} ;
