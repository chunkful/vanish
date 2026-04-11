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

package net.chunkful.vanish.api;

import net.chunkful.vanish.events.EventDispatcher;
import net.chunkful.vanish.storage.Storage;
import net.chunkful.vanish.visibility.VisibilityCalculator;

public final class ApiModule {

    public VanishApi api(final Storage storage, final EventDispatcher eventDispatcher, final VisibilityCalculator visibilityCalculator) {
        return new VanishImplementation(storage, eventDispatcher, visibilityCalculator);
    }

}
