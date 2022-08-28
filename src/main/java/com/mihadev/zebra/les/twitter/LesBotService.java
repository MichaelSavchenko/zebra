package com.mihadev.zebra.les.twitter;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.mihadev.zebra.les.twitter.Quotes.QUOTES;
import static java.util.Collections.singletonList;

class LesBotService {

    static String getRandomStringQuote() {
        Quote quote = getRandomQuote();
        return convertToAnswer(singletonList(quote));
    }

    private static Quote getRandomQuote() {
        Random random = new Random();
        int i = random.nextInt(QUOTES.size());
        return QUOTES.get(i);
    }

    private static String convertToAnswer(List<Quote> quotes) {
        if (quotes.isEmpty()) {
            return "";
        }
        return quotes.stream()
                .map(quote -> quote.getBook().bookName + " :\n" + quote.getText())
                .collect(Collectors.joining("\n\n"));
    }
}
