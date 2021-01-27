module F1 where

-- https://www.nayuki.io/page/fast-fibonacci-algorithms
-- used for inspiration
fib :: Integer -> Integer
-- base cases
fib 0 = 0
fib 1 = 1
fib 2 = 1

-- recursive call for odd and even Integers n.
fib n
    |odd n = (fib ((n+1) `div` 2))^2 + (fib ((n-1) `div` 2))^2
    |even n = fib (n `div` 2) * (2 * fib ((n `div` 2) + 1) - fib (n `div` 2))


rovarsprak :: String -> String
rovarsprak word = concat [if char `elem` ['a','o','e','u','i','y'] then [char] else [char,'o',char]|char <- word]



karpsravor :: String -> String
karpsravor word = concat [if char ==  |char <- word]
-- karpsravor "" = ""

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

