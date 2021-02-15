module F2 where
import Data.List

{- 2 Molekylära Sekvenser -}

data SeqType = DNA | Protein deriving (Eq, Show)
data MolSeq = MolSeq {name :: String, seq :: String, seqType :: SeqType} deriving (Eq, Show)

string2seq :: String -> String -> MolSeq
string2seq name seq
    | seq == [char | char <- seq, char `elem` "ACGT"] = MolSeq name seq DNA
    | otherwise = MolSeq name seq Protein

seqName :: MolSeq -> String
seqName molseq = name molseq

seqSequence :: MolSeq -> String
seqSequence molseq = F2.seq molseq

seqLength :: MolSeq -> Int
seqLength molseq = length (F2.seq molseq)

seqDistance :: MolSeq -> MolSeq -> Double

seqDistance (MolSeq n1 s1 DNA) (MolSeq n2 s2 DNA)
    | alfa s1 s2 < 0.74 = -3/4 * log(1 - (4 * (alfa s1 s2))/3) 
    | otherwise = 3.3
seqDistance (MolSeq n1 s1 Protein) (MolSeq n2 s2 Protein)
    | alfa s1 s2 <= 0.94 = -19/20 * log(1 - (20 * (alfa s1 s2))/19) 
    | otherwise = 3.7
seqDistance a b = error "Arguments are not same Molseq types!"

alfa :: String -> String -> Double
alfa a b = fromIntegral((length [ (x,y) | (x,y) <- zip a b, x /= y ])) / fromIntegral(length a)


{- 2 Profiler och sekvenser -}

data Profile = M {profType :: SeqType, numberOfSeq :: Int, profName :: String, profileMatrix :: [[(Char, Int)]]} deriving (Show)

-- Construct Matrix
nucleotides = "ACGT"
aminoacids = sort "ARNDCEQGHILKMFPSTWYVX"
 
makeProfileMatrix :: [MolSeq] -> [[(Char, Int)]]
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
 
molseqs2profile :: String -> [MolSeq] -> Profile
molseqs2profile name molList = M (seqType (head molList)) (length molList) (name) (makeProfileMatrix molList)

profileName :: Profile -> String
profileName profile = profName profile

profileFrequency :: Profile -> Int -> Char -> Double
profileFrequency p i c = relFreq
    where 
        -- get row i
        row = profileMatrix p !! i
        -- get element c
        element = [y | (x, y) <- row, x == c]
        -- divide with length
        relFreq = fromIntegral (head element) / fromIntegral (numberOfSeq p)








 {- Notes -}
-- seqTypeHelper :: MolSeq -> Bool
-- seqTypeHelper (DNA _ _ ) = True
-- seqTypeHelper (Protein _ _ ) = False

-- seqType :: MolSeq -> String -> String -> MolSeq
-- seqType x 
--     |seqTypeHelper x == True = DNA
--     |seqTypeHelper x == False = Protein

--instance Show DNA where
--    show (DNA _ _ ) = show DNA
--molseqs2profile :: String -> [MolSeq] -> Profile
--molseqs2profile name seqList = M (string2seq (head seqList)) (length seqList) (makeProfileMatrix seqList)









