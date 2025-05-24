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
    // TODO: Investigate!
    // Some spells (e.g. 58644) omit the $ in front of a reference.
    // This seems like a weirdo typo or edge case.
    // Adding numeric definition here effectively makes dollar signs optional in formulas.
    // This may be fine...or it may cause havoc on some descriptions.
    // Remove and ignore said spells if things get dicey.
    | numericDefinition
    | number
    ;

/*
 * Formula Function
 * Functions that operate on formula fragments and are formula fragments themselves.
 */
formulaFunction: DOLLAR_SIGN (
    min
    | max
    | floor
    | functionConditional
    )
    ;

minHeader: (UPPER_M_CHAR UPPER_I_CHAR UPPER_N_CHAR) | (LOWER_M_CHAR LOWER_I_CHAR LOWER_N_CHAR) ;
min: minHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

maxHeader: (UPPER_M_CHAR UPPER_A_CHAR UPPER_X_CHAR) | (LOWER_M_CHAR LOWER_A_CHAR LOWER_X_CHAR) ;
max: maxHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

floorHeader: (UPPER_F_CHAR UPPER_L_CHAR UPPER_O_CHAR UPPER_O_CHAR UPPER_R_CHAR) | (LOWER_F_CHAR LOWER_L_CHAR LOWER_O_CHAR LOWER_O_CHAR LOWER_R_CHAR) ;
floor: floorHeader OPEN_PAREN formulaFragment CLOSE_PAREN ;

functionConditionalHeader: (UPPER_C_CHAR UPPER_O_CHAR UPPER_N_CHAR UPPER_D_CHAR) | (LOWER_C_CHAR LOWER_O_CHAR LOWER_N_CHAR LOWER_D_CHAR) ;
functionConditional: functionConditionalHeader OPEN_PAREN condition=booleanFunction COMMA true=formulaFragment COMMA false=formulaFragment CLOSE_PAREN ;

/*
 * Boolean Function
 * Functions that return boolean values.
 * Only used within conditional functions.
 */
booleanFunction: DOLLAR_SIGN (
    greaterThan
    | greaterThanOrEqual
    | equal
    )
    ;

greaterThanHeader: (UPPER_G_CHAR UPPER_T_CHAR) | (LOWER_G_CHAR LOWER_T_CHAR) ;
greaterThan: greaterThanHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

greaterThanOrEqualHeader: (UPPER_G_CHAR UPPER_T_CHAR UPPER_E_CHAR) | (LOWER_G_CHAR LOWER_T_CHAR LOWER_E_CHAR) ;
greaterThanOrEqual: greaterThanOrEqualHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

equalHeader: (UPPER_E_CHAR UPPER_Q_CHAR) | (LOWER_E_CHAR LOWER_Q_CHAR) ;
equal: equalHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;


/*
 * Numeric Reference
 * These references return numbers, and can be use in formulas or as text.
 */
numericReference: DOLLAR_SIGN numericDefinition ;
numericDefinition: minDamage
    | maxDamage
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
    | minRange
    | maxRange
    | maxStacks
    | maxTargetLevel
    | variableReference
    | attackPower
    | rangedAttackPower
    | mainWeaponMinDamage
    | mainWeaponMaxDamage
    | mainWeaponMinBaseDamage
    | mainWeaponMaxBaseDamage
    | mainWeaponSpeed
    | spellPower
    | spirit
    | playerLevel
    | playerHandedness
    ;

// References to data from the spell itself
// A missing index seems to imply the first index (1).
minDamage: spellId=positiveInteger? LOWER_M_CHAR index=positiveInteger? ;
maxDamage: spellId=positiveInteger? UPPER_M_CHAR index=positiveInteger? ;
duration: spellId=positiveInteger? (UPPER_D_CHAR | LOWER_D_CHAR) ;
auraPeriod: spellId=positiveInteger? (UPPER_T_CHAR | LOWER_T_CHAR) index=positiveInteger? ;
procCharges: spellId=positiveInteger? LOWER_N_CHAR ;
procChance: spellId=positiveInteger? (UPPER_H_CHAR | LOWER_H_CHAR) ;
chainTargets: spellId=positiveInteger? LOWER_X_CHAR index=positiveInteger? ;
// Radius has a concept of a min and max (via level), but all the radii I see
// in the SpellRadius DBC file have the same min and max.
// Just going to use the base radius for the time being.
radius: spellId=positiveInteger? (UPPER_A_CHAR | LOWER_A_CHAR) index=positiveInteger? ;
miscValue: spellId=positiveInteger? LOWER_Q_CHAR index=positiveInteger? ;
pointsPerCombo: spellId=positiveInteger? LOWER_B_CHAR index=positiveInteger ;
amplitude: spellId=positiveInteger? LOWER_E_CHAR index=positiveInteger? ;
chainAmplitude: spellId=positiveInteger? (UPPER_F_CHAR | LOWER_F_CHAR) index=positiveInteger? ;
maxTargets: spellId=positiveInteger? LOWER_I_CHAR ;
minRange: spellId=positiveInteger? LOWER_R_CHAR index=positiveInteger? ;
maxRange: spellId=positiveInteger? UPPER_R_CHAR index=positiveInteger? ;
maxStacks: spellId=positiveInteger? LOWER_U_CHAR ;
maxTargetLevel: spellId=positiveInteger? LOWER_V_CHAR ;

// References to variables found in the SpellDescriptionVariables DBC file.
variableReference: LT_SIGN identifier GT_SIGN ;

// References to data from the player
attackPower: UPPER_A_CHAR UPPER_P_CHAR ;
rangedAttackPower: UPPER_R_CHAR UPPER_A_CHAR UPPER_P_CHAR ;
mainWeaponMinDamage: LOWER_M_CHAR LOWER_W_CHAR ;
mainWeaponMaxDamage: UPPER_M_CHAR UPPER_W_CHAR ;
mainWeaponMinBaseDamage: LOWER_M_CHAR LOWER_W_CHAR LOWER_B_CHAR ;
mainWeaponMaxBaseDamage: UPPER_M_CHAR UPPER_W_CHAR UPPER_B_CHAR ;
mainWeaponSpeed: (UPPER_M_CHAR UPPER_W_CHAR UPPER_S_CHAR) | (LOWER_M_CHAR LOWER_W_CHAR LOWER_S_CHAR) ;
// TODO: Split into different spell power types if we can get said values.
// I don't think the acore database has them.  Just the base value.
spellPower: UPPER_S_CHAR UPPER_P_CHAR (UPPER_H_CHAR | UPPER_S_CHAR) ;
spirit: UPPER_S_CHAR UPPER_P_CHAR UPPER_I_CHAR ;
playerLevel: (UPPER_P_CHAR UPPER_L_CHAR) | (LOWER_P_CHAR LOWER_L_CHAR) ;
playerHandedness: UPPER_H_CHAR UPPER_N_CHAR UPPER_D_CHAR ;

/*
 * Conditionals
 * These had to be separated into numeric and string variants.
 * The numeric variant is used in variable definitions.
 * The string variant is used to render text in descriptions.
 * These are not used in formulas.  See `cond()` for the formula version.
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
    | booleanFunction
    ;
// TODO: Do a and s mean the same thing here?
// Would `a` perhaps mean an aura is active on the player at the moment?
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

//$gHowdy dude:Goodday my lady:c;, $ghow's it hagin?:you look pretty.;
localizedString: (UPPER_L_CHAR | LOWER_L_CHAR) text (COLON text)* SEMI_COLON ;
genderString: (UPPER_G_CHAR | LOWER_G_CHAR) text (COLON text)* SEMI_COLON ;

// These might appear to return numbers, but can actually return two numbers
// if the spell has variance (i.e. dice rolls).
// Ex: `123` vs `123 to 234`
damageString: spellId=positiveInteger? (UPPER_S_CHAR | LOWER_S_CHAR) index=positiveInteger? ;
auraDamageString: spellId=positiveInteger? LOWER_O_CHAR index=positiveInteger? ;
// TODO: Investigate
// Making semi colon option is necessary for some spells (71180)
// This gets scary considering that the trailing reference can be led by a number.
// It's really impossible to parse in that case, so it probably doesn't happen.
// Maybe make dedicated parser rules for this?  Would be kinda gross though...
arithmeticDamageString: (STAR | FORWARD_SLASH) right=positiveInteger SEMI_COLON? (damageString | auraDamageString | numericDefinition) ;

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
    | LOWER_C_CHAR
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
    | LOWER_P_CHAR
    | LOWER_Q_CHAR
    | LOWER_R_CHAR
    | LOWER_S_CHAR
    | LOWER_T_CHAR
    | LOWER_U_CHAR
    | LOWER_V_CHAR
    | LOWER_W_CHAR
    | LOWER_X_CHAR
    | LOWER_Z_CHAR
    | UPPER_A_CHAR
    | UPPER_B_CHAR
    | UPPER_C_CHAR
    | UPPER_D_CHAR
    | UPPER_E_CHAR
    | UPPER_F_CHAR
    | UPPER_G_CHAR
    | UPPER_H_CHAR
    | UPPER_I_CHAR
    | UPPER_L_CHAR
    | UPPER_M_CHAR
    | UPPER_N_CHAR
    | UPPER_O_CHAR
    | UPPER_P_CHAR
    | UPPER_Q_CHAR
    | UPPER_R_CHAR
    | UPPER_S_CHAR
    | UPPER_T_CHAR
    | UPPER_W_CHAR
    | UPPER_X_CHAR
    | OTHER_CHARS
    | DIGITS)+ ;

// TODO: Add colon and semi colon
// They mess with localized and gender string atm if present.
// Need to not use `text` there.
miscChars: (WS
    | PERIOD
    | NON_WORD
    | COMMA
    | EQUAL
    | HYPHEN
    | PLUS
    | LT_SIGN
    | GT_SIGN
    | OPEN_SQUARE
    | CLOSE_SQUARE
    | OPEN_PAREN
    | CLOSE_PAREN
    | QUESTION_MARK
    // TODO: Add $ to text after finding the superset of possible references
    // Adding it now will allow unrecognized operations to sneak through.
//    | DOLLAR_SIGN
    )+ ;

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
LOWER_C_CHAR: 'c' ;
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
LOWER_P_CHAR: 'p' ;
LOWER_Q_CHAR: 'q' ;
LOWER_R_CHAR: 'r' ;
LOWER_S_CHAR: 's' ;
LOWER_U_CHAR: 'u' ;
LOWER_T_CHAR: 't' ;
LOWER_V_CHAR: 'v' ;
LOWER_W_CHAR: 'w' ;
LOWER_X_CHAR: 'x' ;
LOWER_Z_CHAR: 'z' ;
UPPER_A_CHAR: 'A' ;
UPPER_B_CHAR: 'B' ;
UPPER_C_CHAR: 'C' ;
UPPER_D_CHAR: 'D' ;
UPPER_E_CHAR: 'E' ;
UPPER_F_CHAR: 'F' ;
UPPER_G_CHAR: 'G' ;
UPPER_H_CHAR: 'H' ;
UPPER_I_CHAR: 'I' ;
UPPER_L_CHAR: 'L' ;
UPPER_M_CHAR: 'M' ;
UPPER_O_CHAR: 'O' ;
UPPER_N_CHAR: 'N' ;
UPPER_P_CHAR: 'P' ;
UPPER_Q_CHAR: 'Q' ;
UPPER_R_CHAR: 'R' ;
UPPER_S_CHAR: 'S' ;
UPPER_T_CHAR: 'T' ;
UPPER_W_CHAR: 'W' ;
UPPER_X_CHAR: 'X' ;
OTHER_CHARS: [bijkvyJKTUVYZ]+ ;
WS: [ \r\t\n]+ ;
// TODO: Torture (47263) has "spells have a @% chance" in its description.
// What the heck does that mean?  It's the only spell like that so who cares for now.
NON_WORD: [%'"#@`]+ ;
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
