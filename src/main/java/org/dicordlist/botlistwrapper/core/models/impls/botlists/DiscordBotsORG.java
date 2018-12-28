package org.dicordlist.botlistwrapper.core.models.impls.botlists;

import okhttp3.MediaType;
import org.dicordlist.botlistwrapper.core.models.Botlist;

/**
 * Wrapper class for https://discordbots.org/api/docs#bots
 */
public class DiscordBotsORG implements Botlist {
    @Override
    public String getEndPointUrl() {
        return "https://discordbots.org/api/bots/%s/stats";
    }

    @Override
    public String getServerCountField() {
        return "server_count";
    }

    @Override
    public String getShardsField() {
        return "shards";
    }

    @Override
    public String getShardIdField() {
        return "shard_id";
    }

    @Override
    public String getShardCountField() {
        return "shard_count";
    }

    @Override
    public int getSuccessCode() {
        return 200;
    }

    @Override
    public MediaType getContentType() {
        return null;
    }
}
