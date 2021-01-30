module F1 where

{- @authors Simon Larspers Qvist, Beata Johansson -}

{- 1 Fibonacci-talen -}    

fib :: Integer -> Integer
fib 0 = 0
fib 1 = 1
fib 2 = 1
fib n
    |odd n = (fib ((n+1) `div` 2))^2 + (fib ((n-1) `div` 2))^2                     {- https://www.nayuki.io/page/fast-fibonacci-algorithms used for inspiration -}
    |even n = fib (n `div` 2) * (2 * fib ((n `div` 2) + 1) - fib (n `div` 2))


{- 2 Rövarspråket -}    

rovarsprak :: String -> String
rovarsprak word = concat [if char `elem` ['a','o','e','u','i','y'] then [char] else [char,'o',char]|char <- word]


karpsravor :: String -> String
karpsravor "" = ""
karpsravor (x:'o':z:t) = x: karpsravor t where x = z 
karpsravor (x:t) = x: karpsravor t


--karpsravor word
--  |(take 3 word) == [[c1],'o',[c2]] && c1 == c2 && c1 `notElem` ['a','o','e','u','i','y'] = concat [karpsravor (drop 2)]
--  |otherwise = (head word) : karpsravor (tail)

--karpsravor "" = ""
-- karpsravor (c1:'o':c2:[tail])
--  |c1 `notElem` ['a','o','e','u','i','y'] && c1 == c2 = c1:karpsravor [tail]
--  |c1 `notElem` ['a','o','e','u','i','y'] && c1 /= c2 = c2:karpsravor [tail]

--karpsravor (v1:[tail])
--  |v1 `elem` ['a','o','e','u','i','y'] = v1:karpsravor [tail]

-- karpsravor word = concat [if [char, 'o', char] `elem` [word] then  else [] |char <- word]
-- karpsravor s = s

-- medellangd s = 1.0

-- skyffla s = s

