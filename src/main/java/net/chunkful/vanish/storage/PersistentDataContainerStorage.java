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

package net.chunkful.vanish.storage;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

final class PersistentDataContainerStorage implements Storage {

    private final NamespacedKey stateKey = new NamespacedKey("vanish", "state");
    private final NamespacedKey levelOverrideKey = new NamespacedKey("vanish", "level_override");

    @Override
    public boolean isVanished(final UUID uuid) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (!player.isOnline() && !player.hasPlayedBefore()) return false;
        return player.getPersistentDataContainer().getOrDefault(stateKey, PersistentDataType.BOOLEAN, false);
    }

    @Override
    public void setVanished(final UUID uuid, final boolean vanished) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new IllegalArgumentException("Offline players are not supported by this storage.");
        }
        player.getPersistentDataContainer().set(stateKey, PersistentDataType.BOOLEAN, vanished);
    }

    @Override
    public Integer getVanishLevelOverride(final UUID uuid) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (!player.isOnline() && !player.hasPlayedBefore()) return null;
        return player.getPersistentDataContainer().get(levelOverrideKey, PersistentDataType.INTEGER);
    }

    @Override
    public void setVanishLevelOverride(final UUID uuid, final Integer level) {
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new IllegalArgumentException("Offline players are not supported by this storage.");
        }
        if (level == null) {
            player.getPersistentDataContainer().remove(levelOverrideKey);
        } else {
            player.getPersistentDataContainer().set(levelOverrideKey, PersistentDataType.INTEGER, level);
        }
    }

    @Override
    public boolean supportsOfflinePlayers() {
        return false;
    }
}
