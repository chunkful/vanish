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

import net.chunkful.vanish.VanishPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.arim.dazzleconf.Configuration;
import space.arim.dazzleconf.StandardErrorPrint;
import space.arim.dazzleconf.backend.Backend;
import space.arim.dazzleconf.backend.PathRoot;
import space.arim.dazzleconf.backend.yaml.YamlBackend;
import space.arim.dazzleconf.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;

public final class ConfigModule {

    private static final Logger log = LoggerFactory.getLogger(ConfigModule.class);

    public Configuration<Config> configuration() {
        return Configuration.defaultBuilder(Config.class)
                .addSimpleSerializer(new TypeToken<>() {
                }, new Message.Serdes())
                .addSimpleSerializer(new TypeToken<>() {
                }, new CommandHook.Serdes())
                .build();
    }

    public Backend backend(final VanishPlugin vanishPlugin) throws IOException {
        Files.createDirectories(vanishPlugin.getDataPath());
        return new YamlBackend(new PathRoot(vanishPlugin.getDataPath().resolve("config.yml")));
    }

    public Config config(final Configuration<Config> configuration, final Backend backend) {
        return configuration.configureOrFallback(backend, new StandardErrorPrint(printable -> log.error(printable.printString())));
    }

}
