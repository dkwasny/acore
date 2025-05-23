grammar SpellDescription;

spellDescriptionVariables: WS* spellDescriptionVariable (WS+ spellDescriptionVariable)* WS* ;
spellDescriptionVariable: DOLLAR_SIGN identifier EQUAL variableDefinition ;

spellDescription: text ;

text: string+ ;
string: formula
    | stringConditional
    | reference
    | number
    | identifier
    | miscChars
    ;

variableDefinition: (numericConditional | formula | formulaFragment) ;

identifier: (LOWER_A_CHAR
    | LOWER_B_CHAR
    | LOWER_D_CHAR
    | LOWER_E_CHAR
    | LOWER_G_CHAR
    | LOWER_H_CHAR
    | LOWER_I_CHAR
    | LOWER_L_CHAR
    | LOWER_M_CHAR
    | LOWER_N_CHAR
    | LOWER_O_CHAR
    | LOWER_Q_CHAR
    | LOWER_R_CHAR
    | LOWER_S_CHAR
    | LOWER_T_CHAR
    | LOWER_W_CHAR
    | LOWER_X_CHAR
    | LOWER_Z_CHAR
    | UPPER_A_CHAR
    | UPPER_B_CHAR
    | UPPER_G_CHAR
    | UPPER_H_CHAR
    | UPPER_I_CHAR
    | UPPER_L_CHAR
    | UPPER_M_CHAR
    | UPPER_P_CHAR
    | UPPER_R_CHAR
    | UPPER_S_CHAR
    | UPPER_W_CHAR
    | OTHER_CHARS
    | DIGITS)+ ;

miscChars: (WS
    | PERIOD
    | NON_WORD
    | COMMA
    | COLON
    | EQUAL
    | HYPHEN)+ ;

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
    | miscValue
    | pointsPerCombo
    | amplitude
    | maxTargets
    | maxRange
    | variableReference
    | attackPower
    | rangedAttackPower
    | mainWeaponDamage
    | mainWeaponSpeed
    | spellPower
    | spirit
    ;

damageBound: DOLLAR_SIGN spellId=positiveInteger? (LOWER_M_CHAR | UPPER_M_CHAR) index=positiveInteger ;
duration: DOLLAR_SIGN spellId=positiveInteger? LOWER_D_CHAR ;
auraPeriod: DOLLAR_SIGN spellId=positiveInteger? LOWER_T_CHAR index=positiveInteger ;
procCharges: DOLLAR_SIGN spellId=positiveInteger? LOWER_N_CHAR ;
procChance: DOLLAR_SIGN spellId=positiveInteger? LOWER_H_CHAR ;
chainTargets: DOLLAR_SIGN spellId=positiveInteger? LOWER_X_CHAR ;
radius: DOLLAR_SIGN spellId=positiveInteger? LOWER_A_CHAR index=positiveInteger ;
miscValue: DOLLAR_SIGN spellId=positiveInteger? LOWER_Q_CHAR index=positiveInteger ;
pointsPerCombo: DOLLAR_SIGN spellId=positiveInteger? LOWER_B_CHAR index=positiveInteger ;
// A missing index seems to imply the first index (1)
amplitude: DOLLAR_SIGN spellId=positiveInteger? LOWER_E_CHAR index=positiveInteger? ;
maxTargets: DOLLAR_SIGN spellId=positiveInteger? LOWER_I_CHAR ;
// A missing index seems to imply the first index (1)
// I don't think we care about minimum range.  There was only one spell (52601)
// that has a non-zero minumum range while also using $r in the description.
// Said minimum range is just hardcoded into the description.
maxRange: DOLLAR_SIGN spellId=positiveInteger? LOWER_R_CHAR index=positiveInteger? ;
variableReference: DOLLAR_SIGN '<' identifier '>' ;

attackPower: DOLLAR_SIGN UPPER_A_CHAR UPPER_P_CHAR ;
rangedAttackPower: DOLLAR_SIGN UPPER_R_CHAR UPPER_A_CHAR UPPER_P_CHAR ;
mainWeaponDamage: DOLLAR_SIGN ((LOWER_M_CHAR LOWER_W_CHAR LOWER_B_CHAR) | (UPPER_M_CHAR UPPER_W_CHAR UPPER_B_CHAR)) ;
mainWeaponSpeed: DOLLAR_SIGN UPPER_M_CHAR UPPER_W_CHAR UPPER_S_CHAR ;
// TODO: Split into different spell power types if we can get said values.
// I don't think the acore database has them.  Just the base value.
spellPower: DOLLAR_SIGN UPPER_S_CHAR UPPER_P_CHAR (UPPER_H_CHAR | UPPER_S_CHAR) ;
spirit: DOLLAR_SIGN UPPER_S_CHAR UPPER_P_CHAR UPPER_I_CHAR ;

numericConditional: numericConditionalIf WS* numericConditionalElseIf* WS* numericConditionalElse ;
numericConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE;
numericConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE ;
numericConditionalElse: OPEN_SQUARE formula CLOSE_SQUARE ;

stringConditional: stringConditionalIf WS* stringConditionalElseIf* WS* stringConditionalElse ;
stringConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE text? CLOSE_SQUARE;
stringConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE text? CLOSE_SQUARE ;
stringConditionalElse: OPEN_SQUARE text? CLOSE_SQUARE ;

conditionalFragment: OPEN_PAREN WS* conditionalFragment WS* CLOSE_PAREN
    | EXCLAMATION_POINT WS* conditionalFragment
    | conditionalFragment WS* AMPERSAND WS* conditionalFragment
    | conditionalFragment WS* PIPE WS* conditionalFragment
    | conditionalSpellRef
    ;
conditionalSpellRef: (LOWER_A_CHAR | LOWER_S_CHAR) positiveInteger ;

number: (positiveInteger | decimal) ;

positiveInteger: DIGITS ;
decimal: HYPHEN? DIGITS? PERIOD? DIGITS ;

// All reference types
reference: formulaReference
    | localizedString
    | genderString
    | arithmeticDamageString
    | hearthstoneLocation
    | damageString
    | shorthandDamageString
    ;

localizedString: DOLLAR_SIGN (UPPER_L_CHAR | LOWER_L_CHAR) text (COLON text)* ';';
genderString: DOLLAR_SIGN (UPPER_G_CHAR | LOWER_G_CHAR) male=identifier COLON female=identifier ';' ;

damageString: DOLLAR_SIGN (damageStringFragment | auraDamageStringFragment) ;
damageStringFragment: spellId=positiveInteger? LOWER_S_CHAR index=positiveInteger ;
auraDamageStringFragment: spellId=positiveInteger? LOWER_O_CHAR index=positiveInteger ;
arithmeticDamageString: DOLLAR_SIGN (STAR | FORWARD_SLASH) right=positiveInteger SEMI_COLON (damageStringFragment | auraDamageStringFragment) ;

// Some spells omit the `d` constant when referring to damage values from other spells.
// This leaves it just as a simple integer.  Scary stuff in the grand scheme of things.
shorthandDamageString: DOLLAR_SIGN spellId=positiveInteger ;

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
LOWER_B_CHAR: 'b' ;
LOWER_D_CHAR: 'd' ;
LOWER_E_CHAR: 'e' ;
LOWER_G_CHAR: 'g' ;
LOWER_H_CHAR: 'h' ;
LOWER_I_CHAR: 'i' ;
LOWER_L_CHAR: 'l' ;
LOWER_M_CHAR: 'm' ;
LOWER_N_CHAR: 'n' ;
LOWER_O_CHAR: 'o' ;
LOWER_Q_CHAR: 'q' ;
LOWER_R_CHAR: 'r' ;
LOWER_S_CHAR: 's' ;
LOWER_T_CHAR: 't' ;
LOWER_W_CHAR: 'w' ;
LOWER_X_CHAR: 'x' ;
LOWER_Z_CHAR: 'z' ;
UPPER_A_CHAR: 'A' ;
UPPER_B_CHAR: 'B' ;
UPPER_G_CHAR: 'G' ;
UPPER_H_CHAR: 'H' ;
UPPER_I_CHAR: 'I' ;
UPPER_L_CHAR: 'L' ;
UPPER_M_CHAR: 'M' ;
UPPER_P_CHAR: 'P' ;
UPPER_R_CHAR: 'R' ;
UPPER_S_CHAR: 'S' ;
UPPER_W_CHAR: 'W' ;
OTHER_CHARS: [bcfijkpuvyCDEFJKNOQTUVXYZ]+ ;
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
