package org.dicordlist.botlistwrapper.core.models.impls.botlists;

import okhttp3.MediaType;
import org.dicordlist.botlistwrapper.core.models.Botlist;

/**
 * Wrapper class for https://discord.bots.gg/docs/endpoints#post_bots_id_stats
 */
public class DiscordBotsGG implements Botlist {
    @Override
    public String getEndPointUrl() {
        return "https://discord.bots.gg/api/v1/bots/%s/stats";
    }

    @Override
    public String getServerCountField() {
        return "guildCount";
    }

    @Override
    public String getShardsField() {
        return "notUsed";
    }

    @Override
    public String getShardIdField() {
        return "shardId";
    }

    @Override
    public String getShardCountField() {
        return "shardCount";
    }

    @Override
    public MediaType getContentType() {
        return MediaType.parse("application/json");
    }
}
