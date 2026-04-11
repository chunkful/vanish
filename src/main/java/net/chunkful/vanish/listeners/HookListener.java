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

import net.chunkful.vanish.api.VanishApi;
import net.chunkful.vanish.config.Config;
import net.chunkful.vanish.events.VanishApplyEffectsEvent;
import net.chunkful.vanish.events.VanishClearEffectsEvent;
import jakarta.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public final class HookListener implements Listener {

    private final VanishApi api;
    private final Config config;

    @Inject
    public HookListener(final VanishApi api, final Config config) {
        this.api = api;
        this.config = config;
    }

    @EventHandler
    private void onVanish(final VanishApplyEffectsEvent event) {
        config.vanishHooks().execute(event.getPlayer());
    }

    @EventHandler
    private void onUnvanish(final VanishClearEffectsEvent event) {
        config.unvanishHooks().execute(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        if (!api.isVanished(event.getPlayer())) {
            return;
        }

        if (event.getRightClicked() instanceof final Player target) {
            config.playerClickHook().execute(event.getPlayer(), target);
        }
    }
}
