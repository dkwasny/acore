grammar SpellDescription;

/*
 * Grammar entry point for spell descriptions
 */
spellDescription: text ;

/*
 * Grammar entry point for spell description variables
 */
spellDescriptionVariables: spellDescriptionVariable (WS+ spellDescriptionVariable)* ;
spellDescriptionVariable: DOLLAR_SIGN identifier EQUAL variableDefinition ;

variableDefinition: numericConditional | formula | formulaFragment ;

/*
 * Plain text
 * Can contain pretty much anything.
 */
text: string+ ;
string: formula
    | stringConditional
    | numericReference
    | stringReference
    | number
    | identifier
    | miscChars
    ;

/*
 * Formula
 * Contains recursive fragments to create a full formula.
 */
formula: DOLLAR_SIGN OPEN_CURLY formulaFragment CLOSE_CURLY ;

formulaFragment: OPEN_PAREN formulaFragment CLOSE_PAREN
    | formulaFragment (STAR | FORWARD_SLASH) formulaFragment
    | formulaFragment (PLUS | HYPHEN) formulaFragment
    | formulaFunction
    | numericReference
    | number
    ;

/*
 * Formula Function
 * Functions that operate on formula fragments.
 */
formulaFunction: DOLLAR_SIGN (
    max
    | greaterThan
    )
    ;

max: LOWER_M_CHAR LOWER_A_CHAR LOWER_X_CHAR OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;
greaterThan: LOWER_G_CHAR LOWER_T_CHAR OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

/*
 * Numeric Reference
 * These references return numbers, and can be use in formulas or as text.
 */
numericReference: DOLLAR_SIGN numericDefinition ;
numericDefinition: damageBound
    | duration
    | auraPeriod
    | procCharges
    | procChance
    | chainTargets
    | radius
    | miscValue
    | pointsPerCombo
    | amplitude
    | chainAmplitude
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

damageBound: spellId=positiveInteger? (LOWER_M_CHAR | UPPER_M_CHAR) index=positiveInteger ;
duration: spellId=positiveInteger? LOWER_D_CHAR ;
auraPeriod: spellId=positiveInteger? LOWER_T_CHAR index=positiveInteger ;
procCharges: spellId=positiveInteger? LOWER_N_CHAR ;
procChance: spellId=positiveInteger? LOWER_H_CHAR ;
chainTargets: spellId=positiveInteger? LOWER_X_CHAR ;
radius: spellId=positiveInteger? LOWER_A_CHAR index=positiveInteger ;
miscValue: spellId=positiveInteger? LOWER_Q_CHAR index=positiveInteger ;
pointsPerCombo: spellId=positiveInteger? LOWER_B_CHAR index=positiveInteger ;
// A missing index seems to imply the first index (1)
amplitude: spellId=positiveInteger? LOWER_E_CHAR index=positiveInteger? ;
chainAmplitude: spellId=positiveInteger? (LOWER_F_CHAR | UPPER_F_CHAR) index=positiveInteger? ;
maxTargets: spellId=positiveInteger? LOWER_I_CHAR ;
// A missing index seems to imply the first index (1)
// I don't think we care about minimum range.  There was only one spell (52601)
// that has a non-zero minumum range while also using $r in the description.
// Said minimum range is just hardcoded into the description.
maxRange: spellId=positiveInteger? LOWER_R_CHAR index=positiveInteger? ;
variableReference: LT_SIGN identifier GT_SIGN ;

attackPower: UPPER_A_CHAR UPPER_P_CHAR ;
rangedAttackPower: UPPER_R_CHAR UPPER_A_CHAR UPPER_P_CHAR ;
mainWeaponDamage: ((LOWER_M_CHAR LOWER_W_CHAR LOWER_B_CHAR) | (UPPER_M_CHAR UPPER_W_CHAR UPPER_B_CHAR)) ;
mainWeaponSpeed: UPPER_M_CHAR UPPER_W_CHAR UPPER_S_CHAR ;
// TODO: Split into different spell power types if we can get said values.
// I don't think the acore database has them.  Just the base value.
spellPower: UPPER_S_CHAR UPPER_P_CHAR (UPPER_H_CHAR | UPPER_S_CHAR) ;
spirit: UPPER_S_CHAR UPPER_P_CHAR UPPER_I_CHAR ;

/*
 * Conditionals
 * These had to be separated into numeric and string variants.
 * The numeric variant is used in variable definitions.
 * The string variant is used to render text in descriptions.
 */
numericConditional: numericConditionalIf numericConditionalElseIf* numericConditionalElse ;
numericConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE;
numericConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE ;
numericConditionalElse: OPEN_SQUARE formula CLOSE_SQUARE ;

stringConditional: stringConditionalIf stringConditionalElseIf* stringConditionalElse ;
stringConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE text? CLOSE_SQUARE;
stringConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE text? CLOSE_SQUARE ;
stringConditionalElse: OPEN_SQUARE text? CLOSE_SQUARE ;

conditionalFragment: OPEN_PAREN conditionalFragment CLOSE_PAREN
    | EXCLAMATION_POINT conditionalFragment
    | conditionalFragment AMPERSAND conditionalFragment
    | conditionalFragment PIPE conditionalFragment
    | conditionalSpellRef
    ;
conditionalSpellRef: (LOWER_A_CHAR | LOWER_S_CHAR) positiveInteger ;

/*
 * String References
 * These references only return strings, and can't be used in formulas or
 * variable definitions.
 */
stringReference: DOLLAR_SIGN (
    localizedString
    | genderString
    | damageString
    | auraDamageString
    | arithmeticDamageString
    | shorthandDamageString
    | hearthstoneLocation
    )
    ;

localizedString: (UPPER_L_CHAR | LOWER_L_CHAR) text (COLON text)* SEMI_COLON ;
genderString: (UPPER_G_CHAR | LOWER_G_CHAR) male=identifier COLON female=identifier SEMI_COLON ;

// These might appear to return numbers, but can actually return two numbers
// if the spell has variance (i.e. dice rolls).
// Ex: `123` vs `123 to 234`
damageString: spellId=positiveInteger? LOWER_S_CHAR index=positiveInteger ;
auraDamageString: spellId=positiveInteger? LOWER_O_CHAR index=positiveInteger ;
arithmeticDamageString: (STAR | FORWARD_SLASH) right=positiveInteger SEMI_COLON (damageString | auraDamageString | numericDefinition) ;

// Some spells omit the `d` constant when referring to damage values from other spells.
// This leaves it just as a simple integer.  Scary stuff in the grand scheme of things.
shorthandDamageString: spellId=positiveInteger ;

hearthstoneLocation: LOWER_Z_CHAR ;

/*
 * Building Blocks
 */
number: (positiveInteger | decimal) ;
positiveInteger: DIGITS ;
decimal: HYPHEN? DIGITS? PERIOD? DIGITS ;

// Intended to be any alphanumeric string.
// The letters have to be split out due to supporting logic intermixed with
// plain text (both of which are part of the grammar).
// TODO: Is there a better way to handle this?
identifier: (LOWER_A_CHAR
    | LOWER_B_CHAR
    | LOWER_D_CHAR
    | LOWER_E_CHAR
    | LOWER_F_CHAR
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
    | UPPER_F_CHAR
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
    | HYPHEN
    | PLUS)+ ;

/*
 * Lexer Rules
 * There should be little to no overlap on these rules to keep things simple.
 */
OPEN_PAREN: '(' ;
CLOSE_PAREN: ')' ;

OPEN_SQUARE: '[' ;
CLOSE_SQUARE: ']' ;

OPEN_CURLY: '{' ;
CLOSE_CURLY: '}' ;

LT_SIGN: '<' ;
GT_SIGN: '>' ;

// See `identifier` for more on why the letters are split out like this.
LOWER_A_CHAR: 'a' ;
LOWER_B_CHAR: 'b' ;
LOWER_D_CHAR: 'd' ;
LOWER_E_CHAR: 'e' ;
LOWER_F_CHAR: 'f' ;
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
UPPER_F_CHAR: 'F' ;
UPPER_G_CHAR: 'G' ;
UPPER_H_CHAR: 'H' ;
UPPER_I_CHAR: 'I' ;
UPPER_L_CHAR: 'L' ;
UPPER_M_CHAR: 'M' ;
UPPER_P_CHAR: 'P' ;
UPPER_R_CHAR: 'R' ;
UPPER_S_CHAR: 'S' ;
UPPER_W_CHAR: 'W' ;
OTHER_CHARS: [bcijkpuvyCDEJKNOQTUVXYZ]+ ;
WS: [ \r\t\n]+ ;
NON_WORD: [%']+ ;
PERIOD: '.' ;
COMMA: ',' ;
COLON: ':' ;
EQUAL: '=' ;
DOLLAR_SIGN: '$' ;
QUESTION_MARK: '?' ;
SEMI_COLON: ';' ;
STAR: '*' ;
FORWARD_SLASH: '/' ;
PLUS: '+' ;
HYPHEN: '-' ;
EXCLAMATION_POINT: '!' ;
PIPE: '|' ;
AMPERSAND: '&' ;
DIGITS: [0-9]+ ;
