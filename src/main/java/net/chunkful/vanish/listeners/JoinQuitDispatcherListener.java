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

package net.chunkful.vanish.listeners;

import net.chunkful.vanish.events.EventDispatcher;
import net.chunkful.vanish.storage.Storage;
import jakarta.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitDispatcherListener implements Listener {

    private final Storage storage;
    private final EventDispatcher eventDispatcher;

    @Inject
    public JoinQuitDispatcherListener(final Storage storage, final EventDispatcher eventDispatcher) {
        this.storage = storage;
        this.eventDispatcher = eventDispatcher;
    }

    @EventHandler
    private void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (!storage.isVanished(player.getUniqueId())) {
            return;
        }

        eventDispatcher.applyEffects(player);
    }

    @EventHandler
    private void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (!storage.isVanished(player.getUniqueId())) {
            return;
        }

        // This is counterproductive since we want this to stay as long as possible.
        // eventDispatcher.clearEvents(player);
    }

}
