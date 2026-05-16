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

import jakarta.inject.Inject;
import net.chunkful.vanish.api.VanishApi;
import net.chunkful.vanish.config.Config;
import net.chunkful.vanish.events.VanishEnterEvent;
import net.chunkful.vanish.events.VanishExitEvent;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class StatusMessageListener implements Listener {

    private final VanishApi vanishApi;
    private final Config config;
    private final ComponentLogger componentLogger;

    @Inject
    public StatusMessageListener(final VanishApi vanishApi, final Config config, final ComponentLogger componentLogger) {
        this.vanishApi = vanishApi;
        this.config = config;
        this.componentLogger = componentLogger;
    }

    @EventHandler
    private void onJoin(final PlayerJoinEvent event) {
        if (vanishApi.isVanished(event.getPlayer())) {
            event.joinMessage(null);

            Bukkit.getOnlinePlayers().stream()
                    .filter(viewer -> vanishApi.canSee(viewer, event.getPlayer()))
                    .forEach(player -> config.messages().notifyJoin().send(player, event.getPlayer()));
        }
    }

    @EventHandler
    private void onQuit(final PlayerQuitEvent event) {
        if (vanishApi.isVanished(event.getPlayer())) {
            event.quitMessage(null);

            Bukkit.getOnlinePlayers().stream()
                    .filter(viewer -> vanishApi.canSee(viewer, event.getPlayer()))
                    .forEach(player -> config.messages().notifyQuit().send(player, event.getPlayer()));
        }
    }

    @EventHandler
    private void onEnter(final VanishEnterEvent event) {
        final Player target = event.getPlayer();

        for (final Player viewer : Bukkit.getOnlinePlayers()) {
            if (viewer.equals(target)) continue;
            if (!vanishApi.canSee(viewer, target)) {
                config.messages().fakeQuit().send(viewer, target);
            } else {
                config.messages().notifyEnter().send(viewer, target);
            }
        }

        config.messages().notifyJoin().log(componentLogger, target);
    }

    @EventHandler
    private void onExit(final VanishExitEvent event) {
        final Player target = event.getPlayer();

        for (final Player viewer : Bukkit.getOnlinePlayers()) {
            if (viewer.equals(target)) continue;
            if (!vanishApi.canSee(viewer, target)) {
                config.messages().fakeJoin().send(viewer, target);
            } else {
                config.messages().notifyExit().send(viewer, target);
            }
        }

        config.messages().notifyExit().log(componentLogger, target);
    }

}
