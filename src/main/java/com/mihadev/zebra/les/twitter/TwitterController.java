package com.mihadev.zebra.les.twitter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mihadev.zebra.les.twitter.LesBotService.getRandomStringQuote;

@RestController
public class TwitterController {
    private final TwitSender twitSender;

    public TwitterController(TwitSender twitSender) {
        this.twitSender = twitSender;
    }

    @GetMapping("/twit")
    public void twit() {
        String randomStringQuote = getRandomStringQuote();
        twitSender.send(randomStringQuote);
    }
}
