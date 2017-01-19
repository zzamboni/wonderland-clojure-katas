(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= [:heart 10] (play-round [:heart 3] [:heart 10])))
    (is (= [:spade :ace] (play-round [:spade :ace] [:club 4]))))
  (testing "queens are higher rank than jacks"
    (is (= [:club :queen] (play-round [:club :jack] [:club :queen]))))
  (testing "kings are higher rank than queens"
    (is (= [:diamond :king] (play-round [:diamond :king] [:heart :queen]))))
  (testing "aces are higher rank than kings"
    (is (= [:heart :ace] (play-round [:spade :king] [:heart :ace]))))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= [:club 10] (play-round [:club 10] [:spade 10]))))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= [:diamond 10] (play-round [:club 10] [:diamond 10]))))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= [:heart 10] (play-round [:diamond 10] [:heart 10])))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (is (= :player2-wins (play-game [] [[:heart 3]])))
    (is (= :player1-wins (play-game [[:club 10]] [[:spade 10]])))
    (is (#{:player1-wins :player2-wins} (let [[p1 p2] (split-at 26 (shuffle cards))] (play-game p1 p2))))))

