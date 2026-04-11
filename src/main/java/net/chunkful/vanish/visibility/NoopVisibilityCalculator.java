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

package net.chunkful.vanish.visibility;

import org.bukkit.entity.Player;

final class NoopVisibilityCalculator implements VisibilityCalculator {
    @Override
    public boolean canSee(final Player viewer, final Player target) {
        return viewer.equals(target);
    }

    @Override
    public boolean supportsLevels() {
        return false;
    }

    @Override
    public int getSeeLevel(final Player player) {
        throw new UnsupportedOperationException("The noop visibility calculator does not support levels.");
    }

    @Override
    public int getUseLevel(final Player player) {
        throw new UnsupportedOperationException("The noop visibility calculator does not support levels.");
    }

    @Override
    public int getMaximumUseLevel(final Player player) {
        throw new UnsupportedOperationException("The noop visibility calculator does not support levels.");
    }
}
