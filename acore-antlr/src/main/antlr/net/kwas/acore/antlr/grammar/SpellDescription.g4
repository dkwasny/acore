grammar SpellDescription;

root: (spellDescriptionVariables | spellDescription) EOF ;

spellDescription: (expression)+ EOF ;

spellDescriptionVariables: WS* spellDescriptionVariable ((COMMA | WS) spellDescriptionVariable)* ;
spellDescriptionVariable: '$' name=word EQUAL (spellConditional | formula) ;

expression: formula
    | reference
    | spellConditional
    | number
    | word
    | text+=(WS
        | PERIOD
        | NON_WORD
        | COMMA
        | COLON
        | EQUAL)+
    ;

word: (LOWER_A_CHAR | LOWER_M_CHAR | LOWER_S_CHAR | OTHER_CHARS | DIGITS)+ ;
number: '-'? DIGITS (PERIOD DIGITS)? ;

formula: '${' WS* formulaFragment WS* '}' ;

formulaFragment: OPEN_PAREN WS* formulaFragment WS* CLOSE_PAREN
    | formulaFragment WS* (MULTIPLICATION | DIVISION) WS* formulaFragment
    | formulaFragment WS* (ADDITION | SUBTRACTION) WS* formulaFragment
    | formulaReference
    | formulaFunction
    | number
    ;

formulaFunction: max
    ;

max: '$max(' WS* left=formulaFragment WS* COMMA WS* right=formulaFragment WS* CLOSE_PAREN ;

// References that can show up in formulas
formulaReference: multiplier
    | spellEffect
    | duration
    | variableReference
    ;

spellConditional: spellConditionalIf WS* spellConditionalElseIf* WS* spellConditionalElse ;
spellConditionalIf: '$?' spellConditionalFragment '[' expression+ ']';
spellConditionalElseIf: '?' spellConditionalFragment '[' expression+ ']' ;
spellConditionalElse: '[' expression+ ']' ;
spellConditionalFragment: OPEN_PAREN WS* spellConditionalFragment WS* CLOSE_PAREN
    | EXCLAMATION_POINT spellConditionalFragment
    | spellConditionalFragment PIPE spellConditionalFragment
    | spellConditionalSpellRef
    | spellConditionalAuraRef
    ;
spellConditionalSpellRef: LOWER_S_CHAR number ;
spellConditionalAuraRef: LOWER_A_CHAR number ;

multiplier: '$' spellId=number? LOWER_M_CHAR index=number ;
spellEffect: '$' spellId=number? LOWER_S_CHAR index=number ;
duration: '$d' ;
variableReference: '$<' word '>' ;


// All reference types
reference: formulaReference
    | localizedString
    | genderString
    ;

localizedString: '$l' word (COLON word)* ';';
genderString: '$g' word COLON word ';' ;

MULTIPLICATION: '*' ;
DIVISION: '/' ;
ADDITION: '+' ;
SUBTRACTION: '-' ;
EXCLAMATION_POINT: '!' ;
PIPE: '|' ;

OPEN_PAREN: '(' ;
CLOSE_PAREN: ')' ;

LOWER_A_CHAR: 'a' ;
LOWER_M_CHAR: 'm' ;
LOWER_S_CHAR: 's' ;
//LOWER_G_CHAR: 'g' ;
OTHER_CHARS: [bcdefghijklnopqrtuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+ ;
WS: [ \r\t\n]+ ;
NON_WORD: [%']+ ;
PERIOD: '.' ;
COMMA: ',' ;
COLON: ':' ;
EQUAL: '=' ;

DIGITS: [0-9]+ ;
