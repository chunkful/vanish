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

public final class EventDispatcher {

    public void enter(final Player player) {
        final VanishEnterEvent vanishEnterEvent = new VanishEnterEvent(player);
        vanishEnterEvent.callEvent();
    }

    public void exit(final Player player) {
        final VanishExitEvent vanishExitEvent = new VanishExitEvent(player);
        vanishExitEvent.callEvent();
    }

    public void applyEffects(final Player player) {
        final VanishApplyEffectsEvent vanishApplyEffectsEvent = new VanishApplyEffectsEvent(player);
        vanishApplyEffectsEvent.callEvent();
    }

    public void clearEvents(final Player player) {
        final VanishClearEffectsEvent vanishClearEffectsEvent = new VanishClearEffectsEvent(player);
        vanishClearEffectsEvent.callEvent();
    }

    public void recalculate(final Player player) {
        final VanishRecalculateEvent vanishRecalculateEvent = new VanishRecalculateEvent(player);
        vanishRecalculateEvent.callEvent();
    }

}
