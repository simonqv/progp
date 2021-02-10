module F2 where

{- 2 Molekylära Sekvenser -}

-- data Molseq = DNA String String | Protein String String deriving(Show)
data Molseq = DNA {name :: String, seq :: String} | Protein {name :: String, seq :: String} 
    deriving(Show, Eq)

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

    