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
import net.chunkful.vanish.events.VanishApplyEffectsEvent;
import net.chunkful.vanish.events.VanishClearEffectsEvent;
import jakarta.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public final class MetadataEffectListener implements Listener {

    private final VanishPlugin plugin;

    @Inject
    public MetadataEffectListener(final VanishPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onVanish(final VanishApplyEffectsEvent event) {
        final Player player = event.getPlayer();

        player.setMetadata("vanished", getValue());
    }

    @EventHandler
    private void onUnvanish(final VanishClearEffectsEvent event) {
        final Player player = event.getPlayer();

        player.removeMetadata("vanished", plugin);
    }

    private MetadataValue getValue() {
        return new FixedMetadataValue(plugin, true);
    }

}
