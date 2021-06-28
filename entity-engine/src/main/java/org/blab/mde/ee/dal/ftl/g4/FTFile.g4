grammar FTFile;

options {
	language=Java;
}

tokens {
	TRUE,FALSE
}

@parser::header {

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.blab.mde.ee.dal.ftl.utils.*;
import org.blab.mde.ee.dal.ftl.*;
import java.io.File;
import org.blab.mde.core.exception.CrashedException;
}

@lexer::header {
import org.blab.mde.ee.dal.ftl.utils.*;
import org.blab.mde.ee.dal.ftl.*;
import java.io.File;
import org.blab.mde.core.exception.CrashedException;
}

@parser::members {
public FTFile ftFile;


@Override
public String getSourceName() {
    String fullFileName = super.getSourceName();
    File f = new File(fullFileName); // strip to simple name
    return f.getName();
}
}

@lexer::members {
public FTFile ftFile;

@Override
public String getSourceName() {
    String fullFileName = super.getSourceName();
    File f = new File(fullFileName); // strip to simple name
    return f.getName();
}
}

group[FTFile ftFile, String prefix]
@init {
FTFileLexer lexer = (FTFileLexer)_input.getTokenSource();
this.ftFile = lexer.ftFile = ftFile;
}
	:	oldStyleHeader?
		delimiters?
		('import' IDS {ftFile.importTemplates($IDS);})*?
		def[prefix]*
		EOF
	;



oldStyleHeader
    :   'group' ID ( ':' ID )?
	    ( 'implements' ID (',' ID)* )?
	    ';'
	;

groupName returns [String name]
@init {StringBuilder buf = new StringBuilder();}
	:	a=ID {buf.append($a.text);} ('.' a=ID {buf.append($a.text);})*
	;

delimiters
    :	'delimiters' a=STRING ',' b=STRING
     	{
     	ftFile.delimiterStartChar=$a.getText().charAt(1);
        ftFile.delimiterStopChar=$b.getText().charAt(1);
        }
    ;


def[String prefix] : templateDef[prefix] ;
	catch[RecognitionException re] {

	}

templateDef[String prefix]
@init {
    String template=null;
    int n=0; // num char to strip from left, right of template def
}
	:	(	'@' enclosing=ID '.' name=ID
		|	name=ID
		)
	    '::='
	    {Token templateToken = _input.LT(1);}
	    (	STRING     {template=$STRING.text; n=1;}
	    |	BIGSTRING  {template=$BIGSTRING.text; n=2;}
	    |	BIGSTRING_NO_NL  {template=$BIGSTRING_NO_NL.text; n=2;}
	    |	{
	    	template = "";
	    	String msg = "missing template at '"+_input.LT(1).getText()+"'";
    	    }
	    )
	    {
	    if ( $name.index >= 0 ) { // if ID missing
			template = MiscUtil.strip(template, n);
			String templateName = $name.text;
			if ( prefix.length()>0 ) templateName = prefix+$name.text;
			ftFile.defineTemplate(templateName, templateToken,
										 template, $name);
		}
	    }
	;


ID	:	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'-'|'_')*
	;

IDS : ID ('.' ID)* ;

STRING
	:	'"'
		(	'\\' '"'
		|	'\\' ~'"'
		|	{
			}
			'\n'
		|	~('\\'|'"'|'\n')
		)*
		'"'
        {
        String txt = getText().replaceAll("\\\\\"","\"");
		setText(txt);
		}
	;

BIGSTRING_NO_NL
	:	'<%' ( . )* '%>'
        // %\> is the escape to avoid end of string
        {
        String txt = getText().replaceAll("\\%\\\\>","\\%>");
		setText(txt);
		}
	;

BIGSTRING
	:	'<<'
		(	'\\' '>'  // \> escape
		|	'\\' ~'>' // allow this but don't collapse in action
		|	~'\\'
		)*?
        '>>'
        {
        String txt = getText();
        txt = MiscUtil.replaceEscapedRightAngle(txt); // replace \> with > unless <\\>
		setText(txt);
		}
	;

COMMENT
    :   '/*' ( . )*? '*/' {skip();}
    ;

LINE_COMMENT
    :	'//' ~('\n'|'\r')* '\r'? '\n' {skip();}
    ;

WS  :	(' '|'\r'|'\t'|'\n') {skip();} ;
