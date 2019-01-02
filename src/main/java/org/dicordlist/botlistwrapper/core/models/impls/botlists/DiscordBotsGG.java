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
        return "";
    }

    @Override
    public String getShardIdField() {
        return "";
    }

    @Override
    public String getShardCountField() {
        return "";
    }

    @Override
    public MediaType getContentType() {
        return MediaType.parse("application/json");
    }
}
