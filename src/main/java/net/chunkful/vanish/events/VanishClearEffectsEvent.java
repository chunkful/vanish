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

package net.chunkful.vanish.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;


/**
 * Represents an event triggered when a player's vanish effects are cleared.
 * <p>
 * This event is specifically used in scenarios where a player's invisibility
 * or vanish state is removed, and any associated effects (e.g., gameplay changes
 * or visual adjustments) are reverted. For instance, when a player exits the vanish
 * mode, this event ensures that corresponding effects such as scheduler tasks or
 * visibility settings are cleared.
 */
public final class VanishClearEffectsEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    VanishClearEffectsEvent(@NotNull final Player player) {
        super(player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
