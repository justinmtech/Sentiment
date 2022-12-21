package com.justinmtech.sentiment.player;

import com.justinmtech.sentiment.questions.Question;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SPlayerTest extends TestCase {
    private SPlayer player;
    private UUID uuid;

    public void setUp() throws Exception {
        super.setUp();
        uuid = UUID.randomUUID();
        Set<String> questions = new HashSet<>();
        questions.add("no");
        questions.add("yes");
        player = new SPlayer("test", uuid, questions, false);
    }

    public void tearDown() throws Exception {
        player = null;
    }

    public void testTestGetName() {
        Assert.assertEquals("test", player.getName());
    }

    public void testGetUuid() {
        Assert.assertEquals(uuid, player.getUuid());
    }

    public void testGetQuestionsAnswered() {
        Assert.assertEquals(2, player.getQuestionsAnswered().size());
        Assert.assertTrue(player.getQuestionsAnswered().contains("yes"));
        Assert.assertTrue(player.getQuestionsAnswered().contains("no"));
    }

    public void testSetQuestionsAnswered() {
        Set<String> questions = new HashSet<>();
        questions.add("why?");
        questions.add("ok");
        player.setQuestionsAnswered(questions);
        Assert.assertTrue(player.getQuestionsAnswered().contains("why?"));
        Assert.assertTrue(player.getQuestionsAnswered().contains("ok"));
    }

    public void testAddQuestionAnswered() {
        player.addQuestionAnswered(new Question("test test test?"));
        assertTrue(player.getQuestionsAnswered().contains("test test test?"));
    }

    public void testHasAnsweredQuestion() {
        player.addQuestionAnswered(new Question("test test test?"));
        assertTrue(player.hasAnsweredQuestion(new Question("test test test?")));
    }

    public void testIsOptOut() {
        assertFalse(player.isOptOut());
    }

    public void testSetOptOut() {
        player.setOptOut(true);
        assertTrue(player.isOptOut());
    }
}