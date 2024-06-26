module F1 where
import Data.Char

{- Namn: Simon Larspers Qvist, Beata Johansson -}

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
rovarsprak ord = concat [if bokstav `elem` ['a','o','e','u','i','y'] then [bokstav] else [bokstav,'o',bokstav]|bokstav <- ord]


karpsravor :: String -> String
karpsravor "" = ""
karpsravor ord = concat [ if head ord `notElem` ['a','o','e','u','i','y'] then head ord: karpsravor (drop 3 ord) else head ord: karpsravor (tail ord)]


{- 3 medellangd -}

medellangd :: String -> Double
medellangd mening = fromIntegral (antalBokstaver (mening)) / fromIntegral (raknaOrd (mening))

antalBokstaver :: String -> Integer
antalBokstaver mening = toInteger (length [ bokstav | bokstav <- mening, isAlpha bokstav])

raknaOrd :: String -> Integer
raknaOrd mening = acc (mening ++ " ") 0
    where 
        acc mening i 
            | mening == "" = i 
            | isAlpha (head mening) && not (isAlpha (head (tail mening))) = acc (tail mening) (i+1)
            | otherwise = acc (tail mening) (i)


{- 4 Listskyffling -}

skyffla :: [a] -> [a]
skyffla [] = []
skyffla lista = concat [[a | (i, a) <- zip [0..] lista, even i], skyffla [a | (i, a) <- zip [0..] lista, odd i]]
    
