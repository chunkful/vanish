/*
 * vanish
 * Copyright (C) 2026 MCMDEV
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.chunkful.vanish.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public sealed interface VanishApi permits VanishImplementation {

    void vanish(UUID uuid);

    default void vanish(final Player player) {
        vanish(player.getUniqueId());
    }

    void unvanish(UUID uuid);

    default void unvanish(final Player player) {
        unvanish(player.getUniqueId());
    }

    boolean isVanished(UUID uuid);

    default boolean isVanished(final Player player) {
        return isVanished(player.getUniqueId());
    }

    boolean canSee(Player viewer, Player target);

    int getSeeLevel(Player player);

    int getUseLevel(Player player);

    int getMaximumUseLevel(Player player);

    Integer getLevelOverride(UUID uuid);

    void setLevelOverride(UUID uuid, Integer value);

    boolean supportsOfflinePlayers();

    boolean supportsLevels();

}
