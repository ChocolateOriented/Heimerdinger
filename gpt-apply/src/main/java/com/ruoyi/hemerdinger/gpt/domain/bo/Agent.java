package com.ruoyi.hemerdinger.gpt.domain.bo;

import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.hemerdinger.gpt.constant.FictionConstant.*;

public class Agent {

    private List<Message> messages;
    private String prompt;

    public Agent(String prompt) {
        this.prompt = prompt;
        this.messages = new ArrayList<>();
        messages.add(new Message(SYSTEM, prompt));
    }
    public void appendSystemMessage(String message) {
        messages.add(new Message(SYSTEM, message));
    }
    public void appendUserMessage(String message) {
        messages.add(new Message(USER, message));
    }
    public void appendAssistantMessage(String message) {
        messages.add(new Message(ASSISTANT, message));
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
