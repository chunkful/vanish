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

package net.chunkful.vanish.integration;

import net.chunkful.vanish.VanishPlugin;
import net.chunkful.vanish.events.VanishEnterEvent;
import net.chunkful.vanish.events.VanishExitEvent;
import github.scarsz.discordsrv.DiscordSRV;
import jakarta.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class DiscordSrvIntegration implements Listener {

    private final VanishPlugin plugin;

    @Inject
    public DiscordSrvIntegration(final VanishPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onEnter(final VanishEnterEvent event) {
        DiscordSRV.getPlugin().sendLeaveMessage(event.getPlayer(), null);
    }

    @EventHandler
    private void onExit(final VanishExitEvent event) {
        DiscordSRV.getPlugin().sendJoinMessage(event.getPlayer(), null);
    }

}
