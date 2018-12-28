package org.dicordlist.botlistwrapper.core.models;

import okhttp3.MediaType;

public interface Botlist {

    /**
     * The endpoint url in the following format <code>https://botlist.com/api/%s/url</code>
     * The <code>%s</code> represents the bot id
     *
     * @return the unformatted endpoint url
     */
    String getEndPointUrl();

    default String getEndpointUrl(long botId) {
        return String.format(getEndPointUrl(), botId);
    }

    /**
     * @return The name of the the JSON field for the current server count
     */
    String getServerCountField();

    /**
     * @return The name of the field which represents the guild counts of the specific shards
     */
    String getShardsField();

    /**
     * @return The name of the field which represents the current shard
     */
    String getShardIdField();

    /**
     * @return The name of the field that represents the current count of shards
     */
    String getShardCountField();

    /**
     * @return The type of the Authorization token (if needed) like Bot
     */
    default String getAuthorizationType() {
        return "";
    }

    /**
     * Override if not 200
     * @return The usual response code
     */
    default int getSuccessCode() {
        return 200;
    }

    /**
     * @return The content type of the requests usually <code>application/json</code>
     */
    MediaType getContentType();

}
