package org.dicordlist.botlistwrapper.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dicordlist.botlistwrapper.BotlistWrapper;
import org.dicordlist.botlistwrapper.core.exceptions.InvalidResponseException;
import org.dicordlist.botlistwrapper.core.models.AuthenticationProvider;
import org.dicordlist.botlistwrapper.core.models.Botlist;
import org.dicordlist.botlistwrapper.core.models.StatisticsProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BotlistWrapperImpl implements BotlistWrapper {

    //private final Logger logger = LogManager.getLogger(BotlistWrapperImpl.class);

    private final ExecutorService scheduler;
    private final boolean loop;
    private final long loopInterval;
    private final long loopDelay;
    private final TimeUnit loopTimeUnit;
    private final List<Botlist> botlists;
    private final StatisticsProvider provider;
    private final OkHttpClient httpClient;
    private final AuthenticationProvider authenticationProvider;
    private final ObjectMapper mapper;
    private final Consumer<Exception> errorHandler;
    private final Consumer<Class<? extends Botlist>> successHandler;


    public BotlistWrapperImpl(ExecutorService scheduler, boolean loop, long loopInterval, long loopDelay, TimeUnit loopTimeUnit, List<Botlist> botlists, StatisticsProvider provider, OkHttpClient httpClient, AuthenticationProvider authenticationProvider, ObjectMapper mapper, Consumer<Exception> errorHandler, Consumer<Class<? extends Botlist>> successHandler) {
        this.scheduler = scheduler;
        this.loop = loop;
        this.loopInterval = loopInterval;
        this.loopDelay = loopDelay;
        this.loopTimeUnit = loopTimeUnit;
        this.botlists = botlists;
        this.provider = provider;
        this.httpClient = httpClient;
        this.authenticationProvider = authenticationProvider;
        this.mapper = mapper;
        this.errorHandler = errorHandler;
        this.successHandler = successHandler;

        if (this.loop)
            ((ScheduledExecutorService) scheduler).scheduleAtFixedRate(this::post, this.loopDelay, this.loopInterval, this.loopTimeUnit);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void post() {
        botlists.forEach(botlist -> scheduler.execute(() -> {
            ObjectNode jsonObject = mapper.createObjectNode();
            jsonObject.put(botlist.getServerCountField(), provider.getGuildCount());
            Integer[] shards = provider.getGuildCounts();
            if (shards.length != 0)
                jsonObject.putArray(botlist.getShardsField()).addAll((Collection<? extends JsonNode>) mapper.valueToTree(shards));
            int shardId = provider.getShardId();
            if (shardId != -1)
                jsonObject.put(botlist.getShardIdField(), shardId);
            int shardCount = provider.getShardCount();
            if (shardCount != -1)
                jsonObject.put(botlist.getShardCountField(), shardCount);

            RequestBody body = RequestBody.create(botlist.getContentType(), jsonObject.toString());
            Request request = new Request.Builder()
                    .url(botlist.getEndpointUrl(provider.getBotId()))
                    .post(body)
                    .addHeader("Authorization", botlist.getAuthorizationType() + authenticationProvider.getBotlistToken(botlist.getClass()))
                    .build();
            try (Response response = httpClient.newCall(request).execute()){
                int code = response.code();
                if (code != botlist.getSuccessCode())
                    throw new InvalidResponseException(code, "Got invalid error code from botlist", botlist);
                successHandler.accept(botlist.getClass());
            } catch (IOException | InvalidResponseException e) {
                errorHandler.accept(e);
            }
        }));
    }

}
