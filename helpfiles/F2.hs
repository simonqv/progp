module F2 where
import Data.List

{- 2 Molekylära Sekvenser -}
data Molseq = DNA {name :: String, seq :: String} | Protein {name :: String, seq :: String} 
    deriving(Show)

string2seq :: String -> String -> Molseq
string2seq name seq
    | seq == [char | char <- seq, char `elem` "ACGT"] = DNA name seq 
    | otherwise = Protein name seq

seqName :: Molseq -> String
seqName molseq = name molseq

seqSequence :: Molseq -> String
seqSequence molseq = F2.seq molseq

seqLength :: Molseq -> Int
seqLength molseq = length (F2.seq molseq)

seqDistance :: Molseq -> Molseq -> Double
seqDistance (DNA n1 s1) (DNA n2 s2)
    | alfa s1 s2 < 0.74 = -3/4 * log(1 - (4 * (alfa s1 s2))/3) 
    | otherwise = 3.3
seqDistance (Protein n1 s1) (Protein n2 s2)
    | alfa s1 s2 <= 0.94 = -19/20 * log(1 - (20 * (alfa s1 s2))/19) 
    | otherwise = 3.7
seqDistance a b = error "Arguments are not same Molseq types!"

alfa :: String -> String -> Double
alfa a b = fromIntegral((length [ (x,y) | (x,y) <- zip a b, x /= y ])) / fromIntegral(length a)

{- 2 Profiler och sekvenser -}

data Profile = M {type123 :: Molseq, countOfSeq :: Int, profileName :: String, matrix :: [[(Char, Int)]]}

-- Construct Matrix
nucleotides = "ACGT"
aminoacids = sort "ARNDCEQGHILKMFPSTWYVX"
 
makeProfileMatrix :: [Molseq] -> [[(Char, Int)]]
makeProfileMatrix [] = error "Emptysequencelist"
makeProfileMatrix sl = res
    where 
        t = seqType (head sl)
        defaults =
            if (t == DNA) then
                zip nucleotides (replicate (length nucleotides) 0)
            else
                zip aminoacids (replicate (length aminoacids) 0)
        strs = map seqSequence sl
        tmpl = map (map (\x -> ((head x), (length x))) . group . sort) (transpose strs)
        equalFst a b = (fst a) == (fst b)
        res = map sort (map (\l -> unionBy equalFst l defaults) tmpl)

molseqs2profile :: String -> [Molseq] -> Profile
molseqs2profile name seqList = M (string2seq (head seqList)) (length seqList) (makeProfileMatrix seqList)









