package com.kirill.kochnev.exchange.domain.models;

import com.kirill.kochnev.exchange.data.enums.MessageType;

import java.util.List;

/**
 * Created by Kirill Kochnev on 30.07.17.
 */

public class CommonUiModel {

    private MessageType messageType;

    private List<TickUI> ticks;

    public CommonUiModel(MessageType messageType) {
        this.messageType = messageType;
    }

    public CommonUiModel(MessageType messageType, List<TickUI> ticks) {
        this.messageType = messageType;
        this.ticks = ticks;
    }

    public void setTicks(List<TickUI> ticks) {
        this.ticks = ticks;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public List<TickUI> getTicks() {
        return ticks;
    }
}
