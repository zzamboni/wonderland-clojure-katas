(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn rank [[suit rank]] (if (keyword? rank) (.indexOf ranks rank) (- rank 2)))
(defn suit [[suit rank]] (.indexOf suits suit))

(defn play-round [player1-card player2-card]
  (let [winner (cond
                 (not= (rank player1-card) (rank player2-card)) (first (sort-by rank > [player1-card player2-card]))
                 (= (rank player1-card) (rank player2-card)) (first (sort-by suit > [player1-card player2-card])) 
                 :default (assert false (str "Internal error in play-round. player1-card=" player1-card " player2-card=" player2-card)))]
    (println "Player 1 plays" player1-card "/ player 2 plays" player2-card "-- winning card is " winner)
    winner))

(defn play-game [player1-cards player2-cards]
  (loop [p1 player1-cards p2 player2-cards]
    (println "Player 1 cards:" p1)
    (println "Player 2 cards:" p2)
    (cond
      (empty? p1) (do (println "Player 2 wins!") :player2-wins)
      (empty? p2) (do (println "Player 1 wins!") :player1-wins)
      :else (let [[c1 & rp1] p1 [c2 & rp2] p2
                  winner (play-round c1 c2)
                  [np1 np2] (if (= winner c1) [(concat rp1 [c1 c2]) rp2] [rp1 (concat rp2 [c2 c1])])]
              (recur np1 np2)))))
