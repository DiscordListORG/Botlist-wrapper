package org.dicordlist.botlistwrapper.core.models;

public interface StatisticsProvider {

    /**
     * @return The current guild count
     */
    int getGuildCount();

    /**
     * Return <code>-1</code> if you don't use sharding
     * @return the current shard count
     */
    int getShardCount();

    /**
     * Return <code>-1</code> if you don't use sharding
     * @return The current shard id if you're using multiple instances
     */
    int getShardId();

    /**
     * Return <code>-1</code> if you don't use sharding
     * @return The guild counts of all shards in a array
     */
    Integer[] getGuildCounts();

    /**
     * @return The bots id
     */
    long getBotId();

}
