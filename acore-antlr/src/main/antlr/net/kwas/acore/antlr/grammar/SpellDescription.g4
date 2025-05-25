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
    | oneOffRules
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
    // Having both numericReference and numericDefinition as formula fragments
    // effectively makes dollar signs optional for all numeric references.
    // Only one spell actually leverages this ability (58644).
    // I'm not sure if this is a mistake, or some new syntax introduced in a
    // later version of WotLK.
    // I don't think this will cause any problems.
    // NOTE: Remove this rule and handle said spell directly if issues do arise.
    | numericDefinition
    | number
    ;

/*
 * Formula Function
 * Functions that operate on formula fragments and are formula fragments themselves.
 * Functions are used in both lower and upper case forms.  I created separate
 * "header" rules in an attempt to make the rules more readable.
 */
formulaFunction: DOLLAR_SIGN (
    min
    | max
    | floor
    | formulaConditional
    )
    ;

minHeader: (UPPER_M_CHAR UPPER_I_CHAR UPPER_N_CHAR) | (LOWER_M_CHAR LOWER_I_CHAR LOWER_N_CHAR) ;
min: minHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

maxHeader: (UPPER_M_CHAR UPPER_A_CHAR UPPER_X_CHAR) | (LOWER_M_CHAR LOWER_A_CHAR LOWER_X_CHAR) ;
max: maxHeader OPEN_PAREN left=formulaFragment COMMA right=formulaFragment CLOSE_PAREN ;

floorHeader: (UPPER_F_CHAR UPPER_L_CHAR UPPER_O_CHAR UPPER_O_CHAR UPPER_R_CHAR) | (LOWER_F_CHAR LOWER_L_CHAR LOWER_O_CHAR LOWER_O_CHAR LOWER_R_CHAR) ;
floor: floorHeader OPEN_PAREN formulaFragment CLOSE_PAREN ;

formulaConditionalHeader: (UPPER_C_CHAR UPPER_O_CHAR UPPER_N_CHAR UPPER_D_CHAR) | (LOWER_C_CHAR LOWER_O_CHAR LOWER_N_CHAR LOWER_D_CHAR) ;
formulaConditional: formulaConditionalHeader OPEN_PAREN condition=booleanFunction COMMA true=formulaFragment COMMA false=formulaFragment CLOSE_PAREN ;

/*
 * Boolean Function
 * Functions that return boolean values.
 * Only used within conditional functions.
 * Like formula functions, both lower and upper case are supported.
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
 * These references return numbers, and can be used in formulas or as text.
 *
 * References can show up in spells in upper and lower case forms.
 * Sometimes, this makes sense (e.g. min vs max damage).
 * These are split into separate rules for clarity.
 * Other times, I don't know the difference and assume they mean the same
 * thing (e.g. aura period).
 * These rules just allow either case to show up.
 *
 * A missing index on a reference implies the first index is used.
 * For example, "$m" == "$m1".
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
    | minRange
    | maxRange
    | cumulativeAura
    | maxTargets
    | maxTargetLevel
    | variableReference
    | attackPower
    | rangedAttackPower
    | mainWeaponMinDamage
    | mainWeaponMaxDamage
    | mainWeaponMinBaseDamage
    | mainWeaponMaxBaseDamage
    | mainWeaponHandedness
    | mainWeaponSpeed
    | spellPower
    | spirit
    | characterLevel
    ;

// References to data from the spell itself
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
pointsPerCombo: spellId=positiveInteger? LOWER_B_CHAR index=positiveInteger? ;
amplitude: spellId=positiveInteger? LOWER_E_CHAR index=positiveInteger? ;
chainAmplitude: spellId=positiveInteger? (UPPER_F_CHAR | LOWER_F_CHAR) index=positiveInteger? ;
minRange: spellId=positiveInteger? LOWER_R_CHAR index=positiveInteger? ;
maxRange: spellId=positiveInteger? UPPER_R_CHAR index=positiveInteger? ;
cumulativeAura: spellId=positiveInteger? LOWER_U_CHAR ;  // AKA "Max Stacks"
maxTargets: spellId=positiveInteger? LOWER_I_CHAR ;
maxTargetLevel: spellId=positiveInteger? LOWER_V_CHAR ;

// References to variables found in the SpellDescriptionVariables DBC file
variableReference: LT_SIGN identifier GT_SIGN ;

// References to data from the character
attackPower: UPPER_A_CHAR UPPER_P_CHAR ;
rangedAttackPower: UPPER_R_CHAR UPPER_A_CHAR UPPER_P_CHAR ;
mainWeaponMinDamage: LOWER_M_CHAR LOWER_W_CHAR ;
mainWeaponMaxDamage: UPPER_M_CHAR UPPER_W_CHAR ;
mainWeaponMinBaseDamage: LOWER_M_CHAR LOWER_W_CHAR LOWER_B_CHAR ;
mainWeaponMaxBaseDamage: UPPER_M_CHAR UPPER_W_CHAR UPPER_B_CHAR ;
mainWeaponSpeed: (UPPER_M_CHAR UPPER_W_CHAR UPPER_S_CHAR) | (LOWER_M_CHAR LOWER_W_CHAR LOWER_S_CHAR) ;
mainWeaponHandedness: UPPER_H_CHAR UPPER_N_CHAR UPPER_D_CHAR ;
// TODO: Split into different spell power types if we can get said values.
// I don't think the acore database has them.  Just the base value.
spellPower: UPPER_S_CHAR UPPER_P_CHAR (UPPER_H_CHAR | UPPER_S_CHAR) ;
spirit: UPPER_S_CHAR UPPER_P_CHAR UPPER_I_CHAR ;
characterLevel: (UPPER_P_CHAR UPPER_L_CHAR) | (LOWER_P_CHAR LOWER_L_CHAR) ;

// KWAS TODO: Compare skill output for Judgement of Righteousness (20187)
// to your actual character.

/*
 * Conditionals
 * These had to be separated into numeric and string variants.
 * These are not used in formulas.  See formulaConditional for the formula version.
 */
// Numeric conditionals are only used in variable definitions
numericConditional: numericConditionalIf numericConditionalElseIf* numericConditionalElse ;
numericConditionalIf: DOLLAR_SIGN QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE;
numericConditionalElseIf: QUESTION_MARK conditionalFragment OPEN_SQUARE formula CLOSE_SQUARE ;
numericConditionalElse: OPEN_SQUARE formula CLOSE_SQUARE ;

// String conditionals are only used as text in descriptions
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
// Would `a` perhaps mean an aura is active on the character at the moment?
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
    | hearthstoneLocation
    )
    ;

// NOTE: It's important that colon not be a part of the text rule.
// This rule would need significant refactoring to allow it.
// Thankfully, no spells use colon in their description.
localizedString: (UPPER_L_CHAR | LOWER_L_CHAR) text (COLON text)* SEMI_COLON ;
genderString: (UPPER_G_CHAR | LOWER_G_CHAR) text (COLON text)* SEMI_COLON ;

// Damage strings might appear to return numbers, but can actually return two
// numbers if the spell has variance (i.e. dice rolls).
// Ex: `123` vs `123 to 234`
damageString: spellId=positiveInteger? (UPPER_S_CHAR | LOWER_S_CHAR) index=positiveInteger? ;
auraDamageString: spellId=positiveInteger? LOWER_O_CHAR index=positiveInteger? ;

// Making semicolon optional is necessary for some spells (e.g. 71180).
// This gets scary considering that the trailing reference can be led by a
// number (i.e. spellId) which would get merged into the "right" number of this rule.
// That would be an error in the description itself as that would be impossible to
// parse correctly.
// NOTE: Make semicolon required and handle said spells directly if this causes issues.
arithmeticDamageString: (STAR | FORWARD_SLASH) right=positiveInteger SEMI_COLON? (damageString | auraDamageString | numericDefinition) ;

hearthstoneLocation: LOWER_Z_CHAR ;

/*
 * One-off Rules
 * These rules are meant to handle spells that use syntax I don't
 * understand or just want to handle directly without affecting the
 * grammar as a whole.
 * These rules should have the lowest priority in the gammar.
 */
oneOffRules: percentDamageStringRule
    | ruleOfRhunok
    | tortureRule
    ;

// A few spells seem to have references without any letters
// indicating what attribute to use and are always followed by
// a percent sign.
// Some spells seem to reference other spells (e.g. $1234%), while others
// specify what index to retrieve the data from (e.g. $1%).
// There is no bulletproof way to tell which case is which
// aside from assuming values 1-3 are referencing indices
// and everything else is referencing spells.
//
// Relevant Spell IDs: 3827, 9943, 27039, 27208
percentDamageStringRule: DOLLAR_SIGN positiveInteger PERCENT ;

// Roar of Rhunok (57861) is the lone spell that has a stray dollar sign
// without any information on how it's used.
// Ex: "Increaseing damage taken by 20% for $."
// Yes, there's a typo in "Increaseing" as well.
// I'm assuming it's supposed to reference the duration of the spell (i.e. $d)
// but the text was never written correctly and fell through the cracks.
// I wonder what the game itself does?  Maybe one day I'll look into it.
ruleOfRhunok: WS DOLLAR_SIGN PERIOD ;

// Torture (47263) and its higher rank variants are the only spells to use
// the at-sign.
// Ex: "spells have a @% chance"
// Based on the description, it's referring to the proc chance of the spell.
// I don't know the why and how of this syntax, but I'll just assume all
// uses of it refer to proc chance for now.
tortureRule: AT_SIGN PERCENT ;

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
    | PERCENT
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
NON_WORD: ['"#`]+ ;
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
PERCENT: '%' ;
AT_SIGN: '@' ;
DIGITS: [0-9]+ ;
