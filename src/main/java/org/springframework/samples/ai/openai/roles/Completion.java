package org.springframework.samples.ai.openai.roles;

public class Completion {

    private String completion;

    public Completion(String completion) {
        this.completion = completion;
    }

    public String getCompletion() {
        return completion;
    }
}
