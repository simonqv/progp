(ns bernoulli (:gen-class))


(defn bernoulli [n]
  ;;(def ar (vector [1.0]))
  ;;(println (nth ar 0))
  (loop [m 1 ar [1.0]]
    (def bm 0)
    (if (> m n)
    ar
    (recur (+ m 1) 
           (loop [k 0 bm 0.0]
              (if (> k (- m 1))
                (conj ar (/ bm (+ m 1)))
                          
                      
                (recur (inc k) (- bm (* (binom (+ m 1) k) (nth ar k)))    )))
    )

)))



(defn binom [n k]
  (loop [i 1 r 1.0]
    (if (> i k)
      r
      (recur (+ i 1) (* r (/ (+ (- n i) 1) i))))))

(defn formater [x] 
(format "%.6f" x)
)

(defn -main
		(map println (map formater (bernoulli 20)))
)






