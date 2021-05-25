module BusNumbers where
--import Data.List
{-
readInput = (map read) . words

writeOutput = unwords . (map show)


solve [] = []
solve list = res
  where
    sorted_list = sort list
    res = merge sorted_list




merge :: [Int] -> [String]
merge [] = []
merge (a:b:c:rest)
  | abs a-b == 1 && abs a-c == 2 = show a : "-" : merge (findEnd (a+2 : rest))
  |otherwise = show a : merge rest
merge a = [show a]


findEnd [] = []
findEnd (a:b:rest)
  | abs a-b == 1 = findEnd (b:rest)
  | otherwise = a : b : rest

-}

main = do
  inputNum <- getLine
  --inputSeq <- getLine
  --let x = inputSeq
  putStrLn inputNum
  --interact (writeOutput . solve . inputSeq)

