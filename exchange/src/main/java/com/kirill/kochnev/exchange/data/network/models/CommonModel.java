package com.kirill.kochnev.exchange.data.network.models;

import com.kirill.kochnev.exchange.data.enums.MessageType;

import java.util.List;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class CommonModel {

    private MessageType messageType;

    private String errorMessage;

    public CommonModel() {
    }

    public CommonModel(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public boolean isEmptyList() {
        return true;
    }

    public List<Tick> getList() {
        return null;
    }

}
