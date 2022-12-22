package com.justinmtech.sentiment.file;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import com.justinmtech.sentiment.Sentiment;
import com.justinmtech.sentiment.player.SPlayer;
import com.justinmtech.sentiment.questions.PlayerResponse;
import com.justinmtech.sentiment.questions.Question;
import junit.framework.TestCase;

import java.util.Optional;
import java.util.UUID;

public class ResponseFileManagerTest extends TestCase {
    private ServerMock server;
    private Sentiment plugin;
    private PlayerResponse responseObject;
    private String response;
    private UUID uuid;
    private Question question;
    private SPlayer sPlayer;

    public void setUp() throws Exception {
        super.setUp();
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Sentiment.class);
        uuid = UUID.randomUUID();
        sPlayer = new SPlayer("bobman", uuid);
        question = new Question("Do you like the server?");
        response = "Yes.. In fact I love it...";
        responseObject = new PlayerResponse(sPlayer, question, response);
    }

    public void tearDown() {
        MockBukkit.unload();
        server = null;
        plugin = null;
    }

    public void testAddResponseToFile() {
        assertNotNull(plugin.getResponseFileManager());
        plugin.getQuestionManager().addQuestion(question.getContent());
        boolean success = plugin.getResponseFileManager().addResponseToFile(responseObject, question);
        assertTrue(success);

    }

/*    //TODO Fix this
    public void testGetResponse() {
        plugin.getQuestionManager().addQuestion(question.getContent());
        Optional<PlayerResponse> response = plugin.getResponseFileManager().getResponse(question, responseObject.getResponseId());
        assertTrue(response.isPresent());
    }

    //TODO Fix this
    public void testRemoveResponseFromFile() {
        boolean removed = plugin.getResponseFileManager().removeResponseFromFile(question, responseObject.getResponseId());
        assertTrue(removed);
    }*/
}