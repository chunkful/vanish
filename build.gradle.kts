import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("java")
    alias(libs.plugins.shadow)
    alias(libs.plugins.paperweightUserdev)
    alias(libs.plugins.runTask.paper)
    alias(libs.plugins.resourceFactory.paperConvention)
    alias(libs.plugins.gremlin)
}

group = "net.chunkful"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "helpchat"
        url = uri("https://repo.helpch.at/releases")
    }
    maven {
        name = "scarsz"
        url = uri("https://nexus.scarsz.me/content/groups/public/")
    }
    // Needed for OpenInv - Yuck!
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)

    runtimeDownload(libs.dazzleconf.core)
    runtimeDownload(libs.dazzleconf.yaml)
    runtimeDownload(libs.jakarta.inject)
    runtimeDownload(libs.solidinjector)

    compileOnly(libs.discordsrv)
    compileOnly(libs.openinv)
    compileOnly(libs.placeholderapi)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

paperPluginYaml {
    main = "net.chunkful.vanish.VanishPlugin"
    loader = "xyz.jpenilla.gremlin.runtime.platformsupport.DefaultsPaperPluginLoader"
    apiVersion = "1.21"
    foliaSupported = true

    dependencies {
        server {
            register("PlaceholderAPI") {
                required = true
                load = PaperPluginYaml.Load.BEFORE
            }
            register("OpenInv") {
                required = false
                load = PaperPluginYaml.Load.BEFORE
            }
            register("DiscordSRV") {
                required = false
                load = PaperPluginYaml.Load.BEFORE
            }
        }
    }

    permissions {
        register("vanish.command")
        register("vanish.command.setlevel")
        register("vanish.see.<level>")
        register("vanish.use.<level>")
        register("vanish.protection.block_place")
        register("vanish.protection.block_break")
        register("vanish.protection.entity_damage")
        register("vanish.protection.player_interact")
        register("vanish.protection.player_drop")
        register("vanish.hostname")
    }
}

runPaper {
    folia {
        registerTask()
    }
}

tasks {
    runPaper {
        downloadPluginsSpec {
            modrinth("luckperms", "v5.5.17-bukkit")
            modrinth("openinv", "5.3.0")
            modrinth("placeholderapi", "2.12.2")
        }
    }
}

tasks.getByName("runFolia", RunServer::class) {
    downloadPlugins {
        url("https://ci.lucko.me/job/LuckPerms-Folia/9/artifact/bukkit/loader/build/libs/LuckPerms-Bukkit-5.5.11.jar")
        url("https://github.com/Jikoo/OpenInv/releases/download/5.1.15/OpenInv.jar")
        url("https://ci.extendedclip.com/job/PlaceholderAPI/212/artifact/build/libs/PlaceholderAPI-2.11.7-DEV-212.jar")
    }
}

tasks.assemble {
    dependsOn(tasks.writeDependencies)
}

configurations.compileOnly {
    extendsFrom(configurations.runtimeDownload.get())
}
configurations.testImplementation {
    extendsFrom(configurations.runtimeDownload.get())
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}