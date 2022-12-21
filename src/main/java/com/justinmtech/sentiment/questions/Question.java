package com.justinmtech.sentiment.questions;

import org.jetbrains.annotations.NotNull;

public class Question {
    private final String content;

    public Question(@NotNull String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
