package org.dicordlist.botlistwrapper.core.models.impls.botlists;

import okhttp3.MediaType;
import org.dicordlist.botlistwrapper.core.models.Botlist;

/**
 * Wrapper class for https://botlistspace.gitbook.io/api/docs/bots
 */
public class BotlistSPACE implements Botlist {
    @Override
    public String getEndPointUrl() {
        return "https://api.botlist.space/v1/bots/%s";
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
        return "not_used_2";
    }

    @Override
    public String getShardCountField() {
        return "no_used_2";
    }

    @Override
    public MediaType getContentType() {
        return MediaType.parse("application/json");
    }
}
