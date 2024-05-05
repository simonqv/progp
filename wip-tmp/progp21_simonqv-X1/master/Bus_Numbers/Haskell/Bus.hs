
import Data.List

toIntList :: String -> [Int]
toIntList x = map read (words x)::[Int]



-- writeOutput = unwords . (map show)



merge :: [Int] -> [String]
merge [] = []
merge (a:b:c:rest)
  | b-a == 1 && c-a == 2 = (show a ++ "-" ++ (show (head (findEnd (c : rest))))) : merge (drop 1 ( findEnd (c : rest)))
  | b-a == 1 && c-a /= 2 = show a : show b : merge (c : rest)
  | a == b = merge (b:c:rest)
  | otherwise = show a : merge (b:c:rest)
merge (a:rest) = show a : merge rest


findEnd :: [Int] -> [Int]
findEnd [] = []
findEnd (a:b:rest)
  | b-a == 1 = findEnd (b:rest)
  | b-a == 0 = findEnd (b:rest)
  | otherwise = a : b : rest
findEnd (a:rest) = a : rest



main = do
  _ <- getLine
  inputbusses <- getLine
  
  let sortedBusses = sort(toIntList inputbusses)
  let merged = merge sortedBusses 

  putStrLn (unwords merged)

  --interact (writeOutput . solve . inputSeq)

