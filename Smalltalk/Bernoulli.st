Object subclass: #Bernoulli.
Bernoulli class extend [

  bernoulli: n [
    |bm Ar bk bin y|

    Ar := Array new: n+1.
    Ar at: 1 put: 1.

    2 to: n+1 do: [:m |
      bm := 0.
      1 to: (m-1) do: [:k |
        bk := Ar at: (k).
        bin := Bernoulli binom: (m) and: (k-1).
        bm := (bm - (bin*bk)).
      ].
      y := (bm / m).

      Ar at: (m) put: y.
      ].
      ^Ar
    ]

    binom: l and: s [
        |r|
        r := 1.
        1 to: s do: [:i |
          r := (r * (l - i + 1))/i.
         ].
        ^r
     ]

  ]

   ans := Bernoulli bernoulli: 20.
   ans do: [:a | Transcript show: a printString; cr].
