package cat.itacademy.s05.t01.n01.blackjack.utils;

import cat.itacademy.s05.t01.n01.blackjack.domain.mongo.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public static Deck standard52Deck() {
        List<Card> cards = new ArrayList<>();
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value = getCardValue(rank);
                cards.add(new Card(suit, rank, value));
            }
        }

        Collections.shuffle(cards);
        return new Deck(cards);
    }

    private static int getCardValue(String rank) {
        return switch (rank) {
            case "A" -> 11;
            case "J", "Q", "K" -> 10;
            default -> Integer.parseInt(rank);
        };
    }

    public static Deck multipleDecks(int numberOfDecks) {
        List<Card> combined = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            combined.addAll(standard52Deck().getCards());
        }
        Collections.shuffle(combined);
        return new Deck(combined);
    }
}
