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

import net.chunkful.vanish.storage.Storage;
import org.bukkit.entity.Player;

public final class OverrideAccountingVisibilityCalculator extends AbstractVisibilityCalculator {

    private final Storage storage;
    private final VisibilityCalculator delegate;

    public OverrideAccountingVisibilityCalculator(final Storage storage, final VisibilityCalculator delegate) {
        this.storage = storage;
        this.delegate = delegate;
    }

    @Override
    public int getUseLevel(final Player player) {
        final Integer vanishLevelOverride = storage.getVanishLevelOverride(player.getUniqueId());

        final int delegateUseLevel = delegate.getUseLevel(player);
        if (vanishLevelOverride == null) {
            return delegateUseLevel;
        }

        if (delegateUseLevel < vanishLevelOverride) {
            return delegateUseLevel;
        }

        return vanishLevelOverride;
    }

    @Override
    public int getSeeLevel(final Player player) {
        return delegate.getSeeLevel(player);
    }
}
