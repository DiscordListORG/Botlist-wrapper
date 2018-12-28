package org.dicordlist.botlistwrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dicordlist.botlistwrapper.core.models.AuthenticationProvider;
import org.dicordlist.botlistwrapper.core.models.Botlist;
import org.dicordlist.botlistwrapper.core.models.StatisticsProvider;
import org.dicordlist.botlistwrapper.impl.BotlistWrapperImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BotlistWrapperBuilder {

    private final Logger logger = LogManager.getLogger(BotlistWrapperImpl.class);

    private ExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private boolean loop = true;
    private long loopInterval = 5;
    private long loopDelay = 0;
    private TimeUnit loopTimeUnit = TimeUnit.MINUTES;
    private List<Botlist> botlists = new ArrayList<>();
    private StatisticsProvider provider;
    private OkHttpClient httpClient = new OkHttpClient();
    private AuthenticationProvider authenticationProvider;
    private ObjectMapper mapper = new ObjectMapper();
    private Consumer<Exception> errorHandler = (e) -> logger.error("An error occurred while posting stats", e);
    private Consumer<Class<? extends Botlist>> successHandler = (botlist) -> logger.info(String.format("Successfully posted stats to ", botlist.getCanonicalName()));

    public BotlistWrapperBuilder(StatisticsProvider provider, AuthenticationProvider authenticationProvider) {
        this.provider = provider;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Set's the executor for all HTTP requests
     * <strong>Must be a ScheduledExecutorService {@link java.util.concurrent.ScheduledExecutorService} when using loop</strong>
     * @param scheduler The scheduler
     * @return The current builder
     */
    public BotlistWrapperBuilder setScheduler(@NotNull ExecutorService scheduler) {
        if (loop && !(scheduler instanceof ScheduledExecutorService))
            throw new IllegalArgumentException("You must provide a ScheduledExecutorService when using loop");
        this.scheduler = scheduler;
        return this;
    }

    /**
     * Enables/disables the loop
     * Enabled by default
     * @param loop Whether the loop should be enabled or not
     * @return The current builder
     */
    public BotlistWrapperBuilder setLoop(boolean loop) {
        return this;
    }

    /**
     * Disables the loop which is usually enabled
     * @return The current builder
     */
    public BotlistWrapperBuilder disableLoop() {
        return setLoop(false);
    }

    /**
     * Sets the interval of the loop
     * @param loopInterval the interval
     * @return The current builder
     */
    public BotlistWrapperBuilder setLoopInterval(long loopInterval) {
        if (!loop)
            throw new IllegalArgumentException("Loop must be enabled for that");
        this.loopInterval = loopInterval;
        return this;
    }

    /**
     * Sets the loop delay between every execution
     * @param loopDelay The delay
     * @return The current builder
     */
    public BotlistWrapperBuilder setLoopDelay(long loopDelay) {
        if (!loop)
            throw new IllegalArgumentException("Loop must be enabled for that");
        this.loopDelay = loopDelay;
        return this;
    }

    /**
     * Sets the TimeUnit for the loop
     * @param loopTimeUnit The TimeUnit
     * @return The current builder
     */
    public BotlistWrapperBuilder setLoopTimeUnit(@NotNull TimeUnit loopTimeUnit) {
        if (!loop)
            throw new IllegalArgumentException("Loop must be enabled for that");
        this.loopTimeUnit = loopTimeUnit;
        return this;
    }

    /**
     * Set's the list of activated botlists
     * It's recommended to use {@link BotlistWrapperBuilder#registerBotlist(Botlist)}
     * @see BotlistWrapperBuilder#registerBotlist(Botlist)
     * @param botlists The list
     * @return The current builder
     */
    public BotlistWrapperBuilder setBotlists(@NotNull List<Botlist> botlists) {
        this.botlists = botlists;
        return this;
    }

    /**
     * Registers a botlist
     * @param botlist The botlist
     * @return The current builder
     */
    public BotlistWrapperBuilder registerBotlist(@NotNull Botlist botlist) {
        botlists.add(botlist);
        return this;
    }

    /**
     * Registers multiple botlists
     * @param botlists The botlists
     * @return The current builder
     */
    public BotlistWrapperBuilder registerBotlists(@NotNull Botlist... botlists) {
        for (Botlist botlist : botlists) {
            registerBotlist(botlist);
        }
        return this;
    }

    /**
     * Set's the JSON Object mapper
     * @param mapper the Mapper {@link com.fasterxml.jackson.databind.ObjectMapper}
     * @return The current builder
     */
    public BotlistWrapperBuilder setMapper(@NotNull ObjectMapper mapper) {
        this.mapper = mapper;
        return this;
    }

    /**
     * Error handler for http requests
     * Should be able to handle {@link java.io.IOException} and {@link org.dicordlist.botlistwrapper.core.exceptions.InvalidResponseException}
     * @param errorHandler The error handler
     * @return The current builder
     */
    public BotlistWrapperBuilder setErrorHandler(Consumer<Exception> errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    /**
     * Sets the successhandler which will be executed whenever an HTTP Request was successfull
     * @param successHandler The handler
     * @return The current builder
     */
    public BotlistWrapperBuilder setSuccessHandler(Consumer<Class<? extends Botlist>> successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public BotlistWrapper build() {
        return new BotlistWrapperImpl(scheduler, loop, loopInterval, loopDelay, loopTimeUnit, botlists, provider, httpClient, authenticationProvider, mapper, errorHandler, successHandler);
    }
}
