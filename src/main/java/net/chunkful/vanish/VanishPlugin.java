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

package net.chunkful.vanish;

import net.chunkful.vanish.api.ApiModule;
import net.chunkful.vanish.command.VanishCommand;
import net.chunkful.vanish.config.ConfigModule;
import net.chunkful.vanish.integration.DiscordSrvIntegration;
import net.chunkful.vanish.integration.OpenInvIntegration;
import net.chunkful.vanish.integration.VanishExpansion;
import net.chunkful.vanish.listeners.FakeMessageListener;
import net.chunkful.vanish.listeners.HookListener;
import net.chunkful.vanish.listeners.JoinQuitDispatcherListener;
import net.chunkful.vanish.listeners.VanishHostListener;
import net.chunkful.vanish.listeners.effects.*;
import net.chunkful.vanish.listeners.protection.*;
import net.chunkful.vanish.listeners.effects.*;
import net.chunkful.vanish.listeners.protection.*;
import net.chunkful.vanish.storage.StorageModule;
import net.chunkful.vanish.visibility.VisibilityCalculatorModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import space.arim.injector.Injector;
import space.arim.injector.InjectorBuilder;
import space.arim.injector.SpecificationSupport;

public class VanishPlugin extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {
        try {
            enable();
        } catch (final Exception e) {
            getSLF4JLogger().error("Failed to enable plugin", e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void enable() {
        this.injector = new InjectorBuilder()
                .specification(SpecificationSupport.JAKARTA)
                .bindInstance(VanishPlugin.class, this)
                .addBindModules(new ConfigModule(), new StorageModule(), new VisibilityCalculatorModule(), new ApiModule())
                .build();

        // Listeners
        registerListener(JoinQuitDispatcherListener.class);
        registerListener(MessageBlockingEffectListener.class);
        registerListener(HookListener.class);
        registerListener(FakeMessageListener.class);
        registerListener(VanishHostListener.class);

        registerListener(BlockBreakProtectionListener.class);
        registerListener(BlockPlaceProtectionListener.class);
        registerListener(EntityDamageProtectionListener.class);
        registerListener(PlayerDropItemProtectionListener.class);
        registerListener(PlayerInteractProtectionListener.class);

        registerListener(AffectSpawningEffectListener.class);
        registerListener(HidingEffectListener.class);
        registerListener(InteractionBlockingEffectListener.class);
        registerListener(MetadataEffectListener.class);
        registerListener(SleepStatusEffectListener.class);
        registerListener(VanishStatusScheduler.class);

        // Expansions (also mostly just listeners)
        injector.request(VanishExpansion.class).register();
        if (Bukkit.getPluginManager().isPluginEnabled("OpenInv")) {
            injector.request(OpenInvIntegration.class).register();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
            injector.request(DiscordSrvIntegration.class).register();
        }

        // Vanish command
        injector.request(VanishCommand.class).register();
    }

    private void registerListener(final Class<? extends Listener> listenerClass) {
        Bukkit.getPluginManager().registerEvents(injector.request(listenerClass), this);
    }
}
