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

package net.chunkful.vanish.listeners.protection;

import net.chunkful.vanish.api.VanishApi;
import jakarta.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;

public final class PlayerDropItemProtectionListener extends AbstractProtectionListener {

    @Inject
    public PlayerDropItemProtectionListener(final VanishApi api) {
        super(api);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerDropItem(final PlayerDropItemEvent event) {
        if (shouldProtect(event.getPlayer(), "player_drop")) {
            event.setCancelled(true);
        }
    }

}
