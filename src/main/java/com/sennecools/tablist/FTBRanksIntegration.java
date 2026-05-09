package com.sennecools.tablist;

import dev.ftb.mods.ftbranks.api.FTBRanksAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.UserManager;
import dev.ftb.mods.ftbranks.api.PermissionValue;
import dev.ftb.mods.ftbranks.api.Rank;
import net.luckperms.api.model.user.User;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class FTBRanksIntegration {

    static LuckPerms api = LuckPermsProvider.get();

    public static String getPlayerRankName(ServerPlayer player) {
        List<Rank> ranks = FTBRanksAPI.manager().getRanks(player);
        if (!ranks.isEmpty()) {
            return ranks.getFirst().getName();
        }
        return "";
    }

    public static String getLuckPermsUserPrefix(ServerPlayer player) {
        User user = api.getPlayerAdapter(ServerPlayer.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();
        return prefix;
    }

    public static int getPlayerRankPower(ServerPlayer player) {
        List<Rank> ranks = FTBRanksAPI.manager().getRanks(player);
        if (!ranks.isEmpty()) {
            return ranks.stream().mapToInt(Rank::getPower).max().orElse(0);
        }
        return 0;
    }

    public static String getFormattedDisplayName(ServerPlayer player) {
        PermissionValue value = FTBRanksAPI.getPermissionValue(player, "ftbranks.name_format");
        if (value.isEmpty()) {
            return null;
        }
        //? if >=1.21.9 {
        /*return value.asString()
                .map(format -> format.replace("{name}", player.getGameProfile().name()))
                .orElse(null);*/
        //?} else {
        return value.asString()
                .map(format -> format.replace("{name}", player.getGameProfile().getName()))
                .orElse(null);
        //?}
    }
}
