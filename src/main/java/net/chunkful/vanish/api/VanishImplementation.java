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

import net.chunkful.vanish.events.EventDispatcher;
import net.chunkful.vanish.storage.Storage;
import net.chunkful.vanish.visibility.VisibilityCalculator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

final class VanishImplementation implements VanishApi {

    private final Storage storage;
    private final EventDispatcher eventDispatcher;
    private final VisibilityCalculator visibilityCalculator;

    VanishImplementation(final Storage storage, final EventDispatcher eventDispatcher, final VisibilityCalculator visibilityCalculator) {
        this.storage = storage;
        this.eventDispatcher = eventDispatcher;
        this.visibilityCalculator = visibilityCalculator;
    }

    @Override
    public void vanish(final UUID uuid) {
        if (isVanished(uuid)) {
            return;
        }

        final Player player = Bukkit.getPlayer(uuid);

        if (player == null && !storage.supportsOfflinePlayers()) {
            throw new IllegalArgumentException("Offline players are not supported by the storage implementation.");
        }

        storage.setVanished(uuid, true);

        if (player != null) {
            eventDispatcher.applyEffects(player);
            eventDispatcher.enter(player);
        }
    }

    @Override
    public void unvanish(final UUID uuid) {
        if (!isVanished(uuid)) {
            return;
        }

        final Player player = Bukkit.getPlayer(uuid);

        if (player == null && !storage.supportsOfflinePlayers()) {
            throw new IllegalArgumentException("Offline players are not supported by the storage implementation.");
        }

        storage.setVanished(uuid, false);


        if (player != null) {
            eventDispatcher.exit(player);
            eventDispatcher.clearEvents(player);
        }
    }

    @Override
    public boolean isVanished(final UUID uuid) {
        final Player player = Bukkit.getPlayer(uuid);

        if (player == null && !storage.supportsOfflinePlayers()) {
            throw new IllegalArgumentException("Offline players are not supported by the storage implementation.");
        }

        return storage.isVanished(uuid);
    }

    @Override
    public boolean supportsOfflinePlayers() {
        return storage.supportsOfflinePlayers();
    }

    @Override
    public boolean canSee(final Player viewer, final Player target) {
        return visibilityCalculator.canSee(viewer, target);
    }

    @Override
    public int getSeeLevel(final Player player) {
        return visibilityCalculator.getSeeLevel(player);
    }

    @Override
    public int getUseLevel(final Player player) {
        return visibilityCalculator.getUseLevel(player);
    }

    @Override
    public int getMaximumUseLevel(final Player player) {
        return visibilityCalculator.getMaximumUseLevel(player);
    }

    @Override
    public Integer getLevelOverride(final UUID uuid) {
        return storage.getVanishLevelOverride(uuid);
    }

    @Override
    public void setLevelOverride(final UUID uuid, final Integer value) {
        final Player player = Bukkit.getPlayer(uuid);

        if (player == null && !storage.supportsOfflinePlayers()) {
            throw new IllegalArgumentException("Offline players are not supported by the storage implementation.");
        }

        storage.setVanishLevelOverride(uuid, value);

        if (player != null) {
            eventDispatcher.recalculate(player);
        }
    }

    @Override
    public boolean supportsLevels() {
        return visibilityCalculator.supportsLevels();
    }
}
