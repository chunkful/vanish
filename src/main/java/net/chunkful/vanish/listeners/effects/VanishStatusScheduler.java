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

package net.chunkful.vanish.listeners.effects;

import net.chunkful.vanish.VanishPlugin;
import net.chunkful.vanish.api.VanishApi;
import net.chunkful.vanish.config.Config;
import net.chunkful.vanish.events.VanishApplyEffectsEvent;
import net.chunkful.vanish.events.VanishClearEffectsEvent;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import jakarta.inject.Inject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class VanishStatusScheduler implements Listener {

    private final VanishApi vanishApi;
    private final VanishPlugin plugin;
    private final Config config;
    private final Map<UUID, ScheduledTask> statusTask = new HashMap<>();

    @Inject
    public VanishStatusScheduler(final VanishApi vanishApi, final VanishPlugin plugin,
                                 final Config config) {
        this.vanishApi = vanishApi;
        this.plugin = plugin;
        this.config = config;
    }

    @EventHandler
    private void onVanish(final VanishApplyEffectsEvent event) {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();
        final ScheduledTask scheduledTask = player.getScheduler().runAtFixedRate(plugin, (createdTask) -> {
            config.messages().actionbar().sendActionbar(player, Placeholder.parsed("use_level", String.valueOf(vanishApi.getUseLevel(player))));
        }, () -> statusTask.remove(uniqueId), 1, 20);
        statusTask.put(uniqueId, scheduledTask);
    }

    @EventHandler
    private void onUnvanish(final VanishClearEffectsEvent event) {
        event.getPlayer().sendActionBar(Component.empty());
        final ScheduledTask scheduledTask = statusTask.remove(event.getPlayer().getUniqueId());
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
    }

}
