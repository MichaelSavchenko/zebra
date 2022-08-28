package com.mihadev.zebra.les.twitter;

import org.springframework.stereotype.Service;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mihadev.zebra.les.twitter.Constants.MAX_TWIT_LENGTH;

@Service
public class TwitSender {
    private final Twitter twitter;
    private LocalDateTime twitTime = LocalDateTime.MIN;

    public TwitSender() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(System.getenv("OAUTH_CONSUMER_KEY"))
                .setOAuthConsumerSecret("OAUTH_CONSUMER_SECRET")
                .setOAuthAccessToken("OAUTH_ACCESS_TOKEN")
                .setOAuthAccessTokenSecret("OAUTH_ACCESS_TOKEN_SECRET");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    void send(String quote) {
        if (LocalDateTime.now().minusHours(3).isAfter(twitTime)) {
            if (quote.length() < MAX_TWIT_LENGTH) {
                sendTwit(quote, twitter);
                twitTime = LocalDateTime.now();
            } else {
                sendMultiplyTwits(quote, twitter);
                twitTime = LocalDateTime.now();
            }
        }
    }

    private static void sendMultiplyTwits(String randomStringQuote, Twitter twitter) {
        List<String> twits = splitTwit(randomStringQuote);
        List<String> decoratedTwits = decorate(twits);
        long twitId = 0;
        for (int i = 0; i < decoratedTwits.size(); i++) {
            String twit = decoratedTwits.get(i);

            if (i == 0) {
                twitId = sendTwit(twit, twitter);
            } else {
                twitId = sendReply(twit, twitter, twitId);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("жопа");
            }
        }
    }

    private static long sendReply(String twit, Twitter twitter, long twitId) {
        try {
            return twitter.updateStatus(new StatusUpdate(twit).inReplyToStatusId(twitId)).getId();
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> decorate(List<String> twits) {
        List<String> result = new ArrayList<>(twits);
        for (int i = 0; i < result.size(); i++) {
            if (i == 0) {
                result.set(i, result.get(i) + "...");
            } else if (i == result.size() - 1) {
                result.set(result.size() - 1, "..." + result.get(result.size() - 1));
            } else {
                result.set(i, "..." + result.get(i) + "...");
            }
        }
        return result;
    }

    private static List<String> splitTwit(String randomStringQuote) {
        List<String> matchList = new ArrayList<>();
        Pattern regex = Pattern.compile(".{1,270}(?:\\s|$)", Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(randomStringQuote);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }
        return matchList;
    }

    private static long sendTwit(String twit, Twitter twitter) {
        try {
            System.out.println(twit);
            return twitter.updateStatus(twit).getId();
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
    }
}
