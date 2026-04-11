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

package net.chunkful.vanish.config;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import space.arim.dazzleconf.LoadResult;
import space.arim.dazzleconf.backend.DataEntry;
import space.arim.dazzleconf.backend.DataList;
import space.arim.dazzleconf.engine.DeserializeInput;
import space.arim.dazzleconf.engine.SerializeDeserialize;
import space.arim.dazzleconf.engine.SerializeOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CommandHook {

    private final List<String> commandTemplates;

    private CommandHook(final List<String> commandTemplates) {
        this.commandTemplates = commandTemplates;
    }

    public static CommandHook of(final String resolvedCommands) {
        return new CommandHook(List.of(resolvedCommands));
    }

    public static CommandHook empty() {
        return new CommandHook(Collections.emptyList());
    }

    public void execute(final Player player) {
        final List<String> resolvedCommands = PlaceholderAPI.setPlaceholders(player, commandTemplates);
        resolvedCommands.forEach(resolvedCommand -> Bukkit.dispatchCommand(player, resolvedCommand));
    }

    public void execute(final Player viewer, final Player target) {
        final List<String> resolvedCommands = PlaceholderAPI.setPlaceholders(target, PlaceholderAPI.setRelationalPlaceholders(viewer, target, commandTemplates));
        resolvedCommands.forEach(resolvedCommand -> Bukkit.dispatchCommand(viewer, resolvedCommand));
    }

    static final class Serdes implements SerializeDeserialize<CommandHook> {

        @Override
        public @NonNull LoadResult<@NonNull CommandHook> deserialize(@NonNull final DeserializeInput deser) {
            return deser.requireDataList().map(dataList -> {
                final List<String> commandTemplates = new ArrayList<>();
                dataList.forEach(dataEntry -> commandTemplates.add((String) dataEntry.getValue()));
                return new CommandHook(commandTemplates);
            });
        }

        @Override
        public void serialize(@NonNull final CommandHook value, @NonNull final SerializeOutput ser) {
            final DataList.Mut list = new DataList.Mut();
            for (final String commandTemplate : value.commandTemplates) {
                list.add(new DataEntry(commandTemplate));
            }
            ser.outDataList(list);
        }
    }

}
