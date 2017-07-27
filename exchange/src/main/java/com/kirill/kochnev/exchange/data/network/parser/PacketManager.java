package com.kirill.kochnev.exchange.data.network.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kirill.kochnev.exchange.data.network.models.IBaseModel;
import com.kirill.kochnev.exchange.data.network.models.SubList;
import com.kirill.kochnev.exchange.data.network.models.SubscribtionResponse;

import java.lang.reflect.Type;

/**
 * Created by Kirill Kochnev on 27.07.17.
 */

public class PacketManager {

    private Gson gson;

    public PacketManager() {
        this.gson = new GsonBuilder().registerTypeAdapter(IBaseModel.class, new BaseModelDeserializer()).create();
    }

    public IBaseModel parse(String rawJson) {
        return gson.fromJson(rawJson, IBaseModel.class);
    }

    private class BaseModelDeserializer implements JsonDeserializer<IBaseModel> {
        @Override
        public IBaseModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();
            IBaseModel result = null;
            if (json.getAsJsonObject().get("subscribed_count") != null) {
                result = gson.fromJson(json, SubscribtionResponse.class);
            } else if (json.getAsJsonObject().get("ticks") != null) {
                result = gson.fromJson(json, SubList.class);
            }
            return result;
        }
    }
}
