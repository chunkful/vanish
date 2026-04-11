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
 * Represents an event triggered when vanish effects are applied to a player.
 * <p>
 * This event is specifically used in scenarios where a player enters a vanish state,
 * and any associated effects (e.g., gameplay changes, visibility adjustments, or status messages)
 * are applied. For instance, when a player is set to invisible mode, this event ensures
 * that corresponding actions such as visual changes or scheduled tasks for visibility updates
 * are executed.
 */
public final class VanishApplyEffectsEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    VanishApplyEffectsEvent(@NotNull final Player player) {
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
