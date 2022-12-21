package com.justinmtech.sentiment.questions;

import com.justinmtech.sentiment.player.SPlayer;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.UUID;

public class PlayerResponseTest extends TestCase {
    private PlayerResponse responseObject;
    private String response;
    private Question question;
    private SPlayer sPlayer;
    private UUID playerId;

    public void setUp() throws Exception {
        super.setUp();
        this.playerId = UUID.randomUUID();
        this.sPlayer = new SPlayer("mineman224", playerId);
        this.question = new Question("What do you think of our server?");
        this.response = "It's pretty good, except for the new update which I don't like because..";
        this.responseObject = new PlayerResponse(sPlayer, question, response);
    }

    public void tearDown() throws Exception {
        responseObject = null;
        playerId = null;
    }

    public void testGetResponse() {
        Assert.assertEquals(response, responseObject.getResponse());
    }

    public void testSetResponse() {
        String newResponse = "it sucks!";
        Assert.assertEquals(response, responseObject.getResponse());
        responseObject.setResponse(newResponse);
        Assert.assertEquals(newResponse, responseObject.getResponse());
    }

    public void testGetQuestion() {
        Assert.assertEquals(question, responseObject.getQuestion());
    }

    public void testSetQuestion() {
        Question newQuestion = new Question("What's your big brain take on the server?");
        responseObject.setQuestion(newQuestion);
        Assert.assertEquals(newQuestion, responseObject.getQuestion());
    }

    public void testGetResponseId() {
        Assert.assertNotNull(responseObject.getResponseId());
    }

    public void testSetResponseId() {
        UUID prevId = responseObject.getResponseId();
        responseObject.setRandomResponseId();
        Assert.assertNotSame(prevId, responseObject.getResponseId());
    }

    public void testGetPlayer() {
        assertEquals(sPlayer, responseObject.getPlayer());
    }

    public void testSetPlayer() {
        UUID newUUID = UUID.randomUUID();
        SPlayer newPlayer = new SPlayer("testplayer123", newUUID);
        responseObject.setPlayer(newPlayer);
        Assert.assertEquals(newPlayer, responseObject.getPlayer());
    }
}