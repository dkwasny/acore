grammar SpellDescription;

spellDescriptionVariables: WS* spellDescriptionVariable (WS+ spellDescriptionVariable)* WS* ;
spellDescriptionVariable: DOLLAR_SIGN identifier EQUAL variableDefinition ;

spellDescription: text ;

text: string+ ;
string: formula
    | reference
    | stringConditional
    | number
    | identifier
    | miscChars
    ;

variableDefinition: (numericConditional | formula | formulaFragment) ;

identifier: (LOWER_A_CHAR
    | LOWER_D_CHAR
    | LOWER_G_CHAR
    | LOWER_H_CHAR
    | LOWER_L_CHAR
    | LOWER_M_CHAR
    | LOWER_N_CHAR
    | LOWER_O_CHAR
    | LOWER_S_CHAR
    | LOWER_T_CHAR
    | LOWER_X_CHAR
    | LOWER_Z_CHAR
    | UPPER_M_CHAR
    | OTHER_CHARS
    | DIGITS)+ ;

miscChars: (WS
    | PERIOD
    | NON_WORD
    | COMMA
    | COLON
    | EQUAL)+ ;

formula: DOLLAR_SIGN '{' WS* formulaFragment WS* '}' ;

formulaFragment: OPEN_PAREN WS* formulaFragment WS* CLOSE_PAREN
    | formulaFragment WS* (STAR | FORWARD_SLASH) WS* formulaFragment
    | formulaFragment WS* (PLUS | HYPHEN) WS* formulaFragment
    | formulaReference
    | formulaFunction
    | number
    ;

formulaFunction: max
    | greaterThan
    ;

max: maxHeader OPEN_PAREN WS* left=formulaFragment WS* COMMA WS* right=formulaFragment WS* CLOSE_PAREN ;
maxHeader: DOLLAR_SIGN LOWER_M_CHAR LOWER_A_CHAR LOWER_X_CHAR ;

greaterThan: greaterThanHeader OPEN_PAREN WS* left=formulaFragment WS* COMMA WS* right=formulaFragment WS* CLOSE_PAREN ;
greaterThanHeader: DOLLAR_SIGN LOWER_G_CHAR LOWER_T_CHAR ;

// References that can show up in formulas
formulaReference: damageBound
    | duration
    | auraPeriod
    | procCharges
    | procChance
    | chainTargets
    | radius
    | variableReference
    ;

numericConditional: numericConditionalIf WS* numericConditionalElseIf* WS* numericConditionalElse ;
numericConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE;
numericConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE ;
numericConditionalElse: OPEN_SQUARE formula CLOSE_SQUARE ;

stringConditional: stringConditionalIf WS* stringConditionalElseIf* WS* stringConditionalElse ;
stringConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE text CLOSE_SQUARE;
stringConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE text CLOSE_SQUARE ;
stringConditionalElse: OPEN_SQUARE text CLOSE_SQUARE ;

conditionalFragment: OPEN_PAREN WS* conditionalFragment WS* CLOSE_PAREN
    | EXCLAMATION_POINT WS* conditionalFragment
    | conditionalFragment WS* AMPERSAND WS* conditionalFragment
    | conditionalFragment WS* PIPE WS* conditionalFragment
    | conditionalSpellRef
    ;
conditionalSpellRef: (LOWER_A_CHAR | LOWER_S_CHAR) positiveInteger ;
damageBound: DOLLAR_SIGN spellId=positiveInteger? (LOWER_M_CHAR | UPPER_M_CHAR) index=positiveInteger ;
duration: DOLLAR_SIGN spellId=positiveInteger? LOWER_D_CHAR ;
auraPeriod: DOLLAR_SIGN spellId=positiveInteger? LOWER_T_CHAR index=positiveInteger ;
procCharges: DOLLAR_SIGN spellId=positiveInteger? LOWER_N_CHAR ;
procChance: DOLLAR_SIGN spellId=positiveInteger? LOWER_H_CHAR ;
chainTargets: DOLLAR_SIGN spellId=positiveInteger? LOWER_X_CHAR ;
radius: DOLLAR_SIGN spellId=positiveInteger? LOWER_A_CHAR index=positiveInteger ;
variableReference: DOLLAR_SIGN '<' identifier '>' ;

positiveInteger: DIGITS ;
decimal: HYPHEN? DIGITS (PERIOD DIGITS)? ;

number: (positiveInteger | decimal) ;

// All reference types
reference: formulaReference
    | localizedString
    | genderString
    | auraDamageString
    | dividedDamageString
    | damageString
    | hearthstoneLocation
    ;

localizedString: DOLLAR_SIGN LOWER_L_CHAR identifier (COLON identifier)* ';';
genderString: DOLLAR_SIGN LOWER_G_CHAR male=identifier COLON female=identifier ';' ;
auraDamageString: DOLLAR_SIGN spellId=positiveInteger? LOWER_O_CHAR index=positiveInteger ;
dividedDamageString: DOLLAR_SIGN FORWARD_SLASH divisor=positiveInteger SEMI_COLON LOWER_S_CHAR index=positiveInteger ;
damageString: DOLLAR_SIGN spellId=positiveInteger? LOWER_S_CHAR index=positiveInteger ;
hearthstoneLocation: DOLLAR_SIGN LOWER_Z_CHAR ;

STAR: '*' ;
FORWARD_SLASH: '/' ;
PLUS: '+' ;
HYPHEN: '-' ;
EXCLAMATION_POINT: '!' ;
PIPE: '|' ;
AMPERSAND: '&' ;

OPEN_PAREN: '(' ;
CLOSE_PAREN: ')' ;

OPEN_SQUARE: '[' ;
CLOSE_SQUARE: ']' ;

LOWER_A_CHAR: 'a' ;
LOWER_D_CHAR: 'd' ;
LOWER_G_CHAR: 'g' ;
LOWER_H_CHAR: 'h' ;
LOWER_L_CHAR: 'l' ;
LOWER_M_CHAR: 'm' ;
LOWER_N_CHAR: 'n' ;
LOWER_O_CHAR: 'o' ;
LOWER_S_CHAR: 's' ;
LOWER_T_CHAR: 't' ;
LOWER_X_CHAR: 'x' ;
LOWER_Z_CHAR: 'z' ;
UPPER_M_CHAR: 'M' ;
OTHER_CHARS: [bcefijkpqruvwyABCDEFGHIJKLNOPQRSTUVWXYZ]+ ;
WS: [ \r\t\n]+ ;
NON_WORD: [%']+ ;
PERIOD: '.' ;
COMMA: ',' ;
COLON: ':' ;
EQUAL: '=' ;
DOLLAR_SIGN: '$' ;
QUESTION_MARK: '?' ;
SEMI_COLON: ';' ;

DIGITS: [0-9]+ ;
