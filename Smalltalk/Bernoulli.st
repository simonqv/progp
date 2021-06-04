
Object subclass: #Bernoulli.
Bernoulli instanceVariableNames: 'seq'.

Bernoulli class extend [


new [
     | r |
     <category: 'instance creation'>
     r := super new.
     r init.
     ^r
    ]

 init [
        |seq|
       seq := 2
      ]


      calc: n [
            | bm l |
            1 to: n do: [:m |
             n printNl.

              bm := 0.
              l := (m-1).
              0 to: l do: [:k |
                'halloooo' printNl.
            ]]]

]


myObj := Bernoulli new.
myObj calc: 20
