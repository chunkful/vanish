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
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import space.arim.dazzleconf.LoadResult;
import space.arim.dazzleconf.engine.DeserializeInput;
import space.arim.dazzleconf.engine.SerializeDeserialize;
import space.arim.dazzleconf.engine.SerializeOutput;

public final class Message {

    private final String messageTemplate;

    Message(final String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public static Message of(final String messageTemplate) {
        return new Message(messageTemplate);
    }

    String messageTemplate() {
        return messageTemplate;
    }

    public void send(final Player receiver, final TagResolver... resolvers) {
        final String resolvedMessage = PlaceholderAPI.setPlaceholders(receiver, messageTemplate);
        receiver.sendRichMessage(resolvedMessage, resolvers);
    }

    public void send(final Player receiver, final Player subject, final TagResolver... resolvers) {
        final String resolvedMessage = PlaceholderAPI.setPlaceholders(subject, messageTemplate);
        receiver.sendRichMessage(resolvedMessage, resolvers);
    }

    public void sendActionbar(final Player receiver, final TagResolver... resolvers) {
        final String resolvedMessage = PlaceholderAPI.setPlaceholders(receiver, messageTemplate);
        receiver.sendActionBar(MiniMessage.miniMessage().deserialize(resolvedMessage, resolvers));
    }

    static final class Serdes implements SerializeDeserialize<Message> {

        @Override
        public @NonNull LoadResult<@NonNull Message> deserialize(@NonNull final DeserializeInput deser) {
            return deser.requireString().map(Message::new);
        }

        @Override
        public void serialize(@NonNull final Message value, @NonNull final SerializeOutput ser) {
            ser.outString(value.messageTemplate());
        }
    }

}
