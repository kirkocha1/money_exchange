package com.kirill.kochnev.exchange.data.network.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kirill.kochnev.exchange.data.enums.MessageType;
import com.kirill.kochnev.exchange.data.network.models.CommonModel;
import com.kirill.kochnev.exchange.data.network.models.SubList;
import com.kirill.kochnev.exchange.data.network.models.SubscribtionResponse;

import java.lang.reflect.Type;

import static com.kirill.kochnev.websocket.RxSocketWrapper.CONNECTED_MESSAGE;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class PacketManager {

    private Gson gson;

    public PacketManager() {
        this.gson = new GsonBuilder().registerTypeAdapter(CommonModel.class, new BaseModelDeserializer()).create();
    }

    public CommonModel parse(String rawJson) {
        if (rawJson.equals(CONNECTED_MESSAGE)) {
            return new CommonModel(MessageType.CONNECTED);
        }
        return gson.fromJson(rawJson, CommonModel.class);
    }

    private class BaseModelDeserializer implements JsonDeserializer<CommonModel> {
        @Override
        public CommonModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();
            CommonModel result = new CommonModel();
            if (json.getAsJsonObject().get("subscribed_count") != null) {
                result = gson.fromJson(json, SubscribtionResponse.class);
                result.setMessageType(MessageType.SUBSCRIPTION);
            } else if (json.getAsJsonObject().get("ticks") != null) {
                result = gson.fromJson(json, SubList.class);
                result.setMessageType(MessageType.DATA);
            }
            return result;
        }
    }
}
