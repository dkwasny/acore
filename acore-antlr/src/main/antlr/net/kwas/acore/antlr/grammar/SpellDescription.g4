grammar SpellDescription;

desc: (expr)+ EOF ;

expr: text
    | funcout
    | ref
    ;

funcout: '${' funcin '}' ;

funcin: funcref ('*'|'/') funcref
    | funcref ('+'|'-') funcref
    ;

funcref: ref
    | NUM
    ;

ref: M
    | S
    | L
    ;

text: TEXT ;

M: '$m' NUM ;
S: '$s' NUM ;
L: '$l' LOCAL ';';

TEXT: [a-zA-Z,.% \r\t\n]+ ;

LOCAL: [a-zA-Z:]+ ;

NUM: [0-9.]+ ;
