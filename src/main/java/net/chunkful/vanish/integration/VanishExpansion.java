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
import net.chunkful.vanish.api.VanishApi;
import jakarta.inject.Inject;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class VanishExpansion extends PlaceholderExpansion implements Relational {

    private final VanishApi api;
    private final VanishPlugin plugin;

    @Inject
    public VanishExpansion(final VanishApi api, final VanishPlugin plugin) {
        this.api = api;
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getPluginMeta().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(" ", plugin.getPluginMeta().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        return List.of("vanished", "count");
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(final OfflinePlayer player, @NotNull final String params) {
        if (params.equals("vanished")) {
            if (!player.isOnline()) {
                return api.supportsOfflinePlayers() ? api.isVanished(player.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse() : null;
            }

            return api.isVanished(player.getUniqueId()) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
        }
        if (params.equals("count")) {
            if (player instanceof final Player viewer) {
                return String.valueOf(Bukkit.getOnlinePlayers().stream()
                        .filter(api::isVanished)
                        .filter(target -> api.canSee(viewer, target))
                        .count());
            } else {
                return String.valueOf(Bukkit.getOnlinePlayers().stream()
                        .filter(api::isVanished)
                        .count());
            }
        }

        return null;
    }

    @Override
    public String onPlaceholderRequest(final Player one, final Player two, final String identifier) {
        if (identifier.equals("vanished")) {
            if (one == null || two == null) {
                return null;
            }

            if (!api.isVanished(two)) {
                return PlaceholderAPIPlugin.booleanFalse();
            }
            if (one.equals(two)) return PlaceholderAPIPlugin.booleanTrue();
            return api.canSee(one, two) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
        }

        if (identifier.equals("can_see")) {
            if (one == null || two == null) {
                return null;
            }

            if (!api.isVanished(two)) {
                return PlaceholderAPIPlugin.booleanTrue();
            }
            if (one.equals(two)) return PlaceholderAPIPlugin.booleanTrue();
            return api.canSee(one, two) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
        }

        return null;
    }
}
