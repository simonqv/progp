# S2

## 1 Grammatik för Leona-språket

### Tokens 

* FORW, BACK, LEFT, RIGHT, DOWN, UP, COLOR, REP 
* PERIOD, QUOTE
* DECIMAL
* HEX
* ERROR

### Grammatik i BNF-format

<LEONA> ::= <FORW> | <BACK> | <LEFT> | <RIGHT> | <UP> | <DOWN> | <COLOR> | <REP>

<FORW> ::= "FORW " <DECMIAL> <PERIOD> 

<BACK> ::= "BACK " <DECIMAL> <PERIOD>

<LEFT> ::= "LEFT " <DECIMAL> <PERIOD>

<RIGHT> ::= "RIGHT " <DECIMAL> <PERIOD>

<UP> ::= "UP" <PERIOD>

<DOWN> ::= "DOWN" <PERIOD>

<COLOR> ::= "COLOR " <HEX>

<REP> ::= "REP " <DECIMAL> <QUOTE> <LEONA> <QUOTE>

<PERIOD> ::= "."

<QUOTE> ::= "(")"

<DECIMAL> ::= "[0-9]+"

<HEX> ::= "(#[0-9A-F]{6})"

<ERROR> ::= 