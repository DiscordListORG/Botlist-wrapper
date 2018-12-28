package org.dicordlist.botlistwrapper.core.models.impls.botlists;

import okhttp3.MediaType;
import org.dicordlist.botlistwrapper.core.models.Botlist;

import java.util.HashMap;

/**
 * Wrapper class for https://docs.botsfordiscord.com/methods/bots
 */
public class BotsForDiscordCOM implements Botlist {

    private final String id;

    /**
     * Constructor for signified id
     * @param id The bot's id as a string
     */
    public BotsForDiscordCOM(String id) {
        this.id = id;
    }

    /**
     * Constructor for long id
     * @param id The bot's id as a long
     */
    public BotsForDiscordCOM(long id) {
        this(String.valueOf(id));
    }

    @Override
    public String getEndPointUrl() {
        return "https://botsfordiscord.com/api\n" +
                "/bot/:id";
    }

    @Override
    public String getServerCountField() {
        return "server_count";
    }

    @Override
    public String getShardsField() {
        return "not_used_1";
    }

    @Override
    public String getShardIdField() {
        return "not_used_2";
    }

    @Override
    public String getShardCountField() {
        return "not_used_3";
    }

    @Override
    public MediaType getContentType() {
        return MediaType.parse("application/json");
    }

    @Override
    public HashMap<String, String> additionalValues() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        return map;
    }
}
