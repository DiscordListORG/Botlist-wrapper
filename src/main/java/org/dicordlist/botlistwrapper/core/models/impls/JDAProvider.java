package org.dicordlist.botlistwrapper.core.models.impls;

import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.JDA;
import org.dicordlist.botlistwrapper.core.models.StatisticsProvider;
import org.dicordlist.botlistwrapper.util.Producer;


public class JDAProvider implements StatisticsProvider {

    private final Producer<Integer> guildCountProvider;
    private final Producer<Integer> shardCountProvider;
    private final Producer<Integer[]> guildCountsProvider;
    private final Producer<Integer> shardIdProvider;
    private final long botId;

    /**
     * Constructor for not sharded bots
     *
     * @param jda Your jda instance
     */
    public JDAProvider(JDA jda) {
        this.guildCountProvider = () -> Math.toIntExact(jda.getGuildCache().size());
        this.shardCountProvider = () -> -1;
        this.guildCountsProvider = () -> new Integer[0];
        this.shardIdProvider = () -> -1;
        this.botId = jda.getSelfUser().getIdLong();
    }

    /**
     * Constructor for sharded bots
     *
     * @param shardManager Your shardmanager instance
     */
    public JDAProvider(ShardManager shardManager) {
        this.guildCountProvider = () -> -1;
        this.shardCountProvider = () -> Math.toIntExact(shardManager.getShardCache().size());
        this.guildCountsProvider = () -> shardManager.getShards().stream().mapToInt(shard -> Math.toIntExact(shard.getGuildCache().size())).boxed().toArray(Integer[]::new);
        this.shardIdProvider = () -> shardManager.getShards().get(0).getShardInfo().getShardId();
        this.botId = shardManager.getApplicationInfo().complete().getIdLong();
    }

    @Override
    public int getGuildCount() {
        return guildCountProvider.produce();
    }

    @Override
    public int getShardCount() {
        return shardCountProvider.produce();
    }

    @Override
    public int getShardId() {
        return 0;
    }

    @Override
    public Integer[] getGuildCounts() {
        return guildCountsProvider.produce();
    }

    @Override
    public long getBotId() {
        return botId;
    }
}
