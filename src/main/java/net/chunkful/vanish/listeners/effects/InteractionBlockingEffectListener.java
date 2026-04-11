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

import net.chunkful.vanish.api.VanishApi;
import jakarta.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.GenericGameEvent;

public final class InteractionBlockingEffectListener implements Listener {

    private final VanishApi api;

    @Inject
    public InteractionBlockingEffectListener(final VanishApi api) {
        this.api = api;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (api.isVanished(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof final Player player) {
            if (api.isVanished(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof final Player player) {
            if (api.isVanished(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onEntityDamageByBlock(final EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof final Player player) {
            if (api.isVanished(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onEntityTarget(final EntityTargetEvent event) {
        if (event.getTarget() instanceof final Player player) {
            if (api.isVanished(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onSculkTrigger(final GenericGameEvent event) {
        if (event.getEntity() instanceof final Player player) {
            if (api.isVanished(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onEntityPickupItem(final EntityPickupItemEvent event) {
        if (event.getEntity() instanceof final Player player) {
            if (api.isVanished(player)) {
                event.setCancelled(true);
            }
        }
    }


}
