
import Data.List

toIntList :: String -> [Int]
toIntList x = map read (words x)::[Int]



-- writeOutput = unwords . (map show)



merge :: [Int] -> [String]
{-
merge [] = []

merge (a:b:c:rest)
  | abs a-b == 1 && abs a-c == 2 = show a : "-" : merge (findEnd (a+2 : rest))
  | otherwise = show a : merge rest

--merge a = [show a]
-}

findEnd [] = []

findEnd (a:b:rest)
  | abs a-b == 1 = findEnd (b:rest)
  | otherwise = a : b : rest



main = do
  _ <- getLine
  inputbusses <- getLine
  
  let sortedBusses = sort(toIntList inputbusses)
  let merged = merge sortedBusses 

  putStrLn (unwords merged)

  --interact (writeOutput . solve . inputSeq)

