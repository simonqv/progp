Object subclass #Bernoulli.
Bernoulli class extend [


bernoulli: n [
  |bm ar mn ar|
  ar := Array new: n+1.
  ar at: 1 put: 1.

  1 to: n do: [:m |
    bm := 0.
    mn  := m - 1.
    0 to: mn do: [ :k |
      'hej' printNl.
      bk := ar at: k+1.
      bk printNl.
      bm := bm - (Bernoulli binom: m k: (k-1))*bk.
    ].
    i := m+1.
    ar at: i put: bm.
  ].
  ^ar
]


binom: n k:k [
    |r|
    r := 1.
    1 to: k do: [:i |
      r := (r * (self - i + 1))/i.
     ].
    ^r
 ]

]


"MAIN"
ans := Bernoulli bernoulli: 20.

"ar do: [:a | Transcript show: a printString; cr]."
