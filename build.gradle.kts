import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("java")
    alias(libs.plugins.gremlin)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.resourceFactory.paperConvention)
    alias(libs.plugins.runTask.paper)
    alias(libs.plugins.shadow)
}

group = "net.chunkful"

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
    compileOnly(libs.paper)

    runtimeDownload(libs.dazzleconf.core)
    runtimeDownload(libs.dazzleconf.yaml)
    runtimeDownload(libs.jakarta.inject)
    runtimeDownload(libs.solidinjector)

    compileOnly(libs.discordsrv)
    compileOnly(libs.openinv)
    compileOnly(libs.placeholderapi)
}

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
        register("vanish.command") {
            description = "Allows you to use Vanish commands"
        }
        register("vanish.command.setlevel") {
            description = "Allows you to override your vanish level"
        }
        register("vanish.see.<level>") {
            description = "Allows you to see players with a vanish level of <level>"
        }
        register("vanish.use.<level>") {
            description = "Allows you to use vanish with a vanish level of <level>"
        }
        register("vanish.protection.block_place") {
            description = "Allows you to place blocks while vanished"
        }
        register("vanish.protection.block_break") {
            description = "Allows you to break blocks while vanished"
        }
        register("vanish.protection.entity_damage") {
            description = "Allows you to damage entities while vanished"
        }
        register("vanish.protection.player_interact") {
            description = "Allows you to interact with players while vanished"
        }
        register("vanish.protection.player_drop") {
            description = "Allows you to drop items while vanished"
        }
        register("vanish.hostname") {
            description = "Allows you to connect to the server using the vanish hostname"
        }
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

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    debugMode = System.getenv("CI") != "true"
    projectId = "chunkful-vanish"
    versionType = "release"
    uploadFile.set(tasks.shadowJar)
    gameVersions = listOf("1.21.11")
    loaders = listOf("paper", "folia")
    dependencies {
        required.project("placeholderapi")
        optional.project("openinv")
        optional.project("discordsrv")
        optional.project("luckperms")
    }
    syncBodyFrom = file("README.md").readText()

    val tagMessage: String? = System.getenv("CI_COMMIT_TAG_MESSAGE")
    tagMessage?.let {
        changelog.set(it)
    }
}

tasks.modrinth {
    dependsOn(tasks.modrinthSyncBody)
}

tasks.register("publish") {
    dependsOn(tasks.build, tasks.modrinth)
}