package com.justinmtech.sentiment.questions;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manage adding, removing and getting questions from the config file.
 */
public class QuestionManager {
    private final FileConfiguration config;

    public QuestionManager(FileConfiguration config) {
        this.config = config;
    }

    public List<Question> getQuestions() {
        List<Question> questionsList = new ArrayList<>();
        try {
            List<?> questions = getFile().getList("questions", new ArrayList<>());
            for (Object q : questions) {
                Question question = new Question(String.valueOf(q));
                questionsList.add(question);
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return questionsList;
    }

    public Map<String, Question> getMappedQuestions() {
        Map<String, Question> questionMap = new HashMap<>();
        for (Question q : getQuestions()) {
            questionMap.put(q.getContent(), q);
        }
        return questionMap;
    }

    public void addQuestion(@NotNull String question) {
        List<Question> questions = getQuestions();
        questions.add(new Question(question));
        updateQuestionsInFile(questions);
    }

    public boolean removeQuestion(@NotNull String question) {
        boolean removed = false;
        if (questionExists(question)) {
            List<Question> questions = getQuestions();
            removed = questions.remove(getMappedQuestions().get(question));
            updateQuestionsInFile(questions);
        }
        return removed;
    }

    private void updateQuestionsInFile(@NotNull List<Question> questions) {
        List<String> questionsAsStrings = new ArrayList<>();
        for (Question q : questions) {
            questionsAsStrings.add(q.getContent());
        }
        getFile().set("questions", questionsAsStrings);
    }

    public Question getQuestion(@NotNull String question) {
        return getMappedQuestions().get(question);
    }

    public boolean questionExists(@NotNull String question) {
        return getMappedQuestions().get(question) != null;
    }

    public FileConfiguration getFile() {
        return config;
    }
}
