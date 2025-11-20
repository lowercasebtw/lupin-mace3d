plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.loom)
    alias(libs.plugins.publishing)
    alias(libs.plugins.blossom)
    alias(libs.plugins.ksp)
    alias(libs.plugins.fletchingtable.fabric)
    alias(libs.plugins.fletchingtable.neoforge)
}

class ModData {
    val id = property("mod.id").toString()
    val name = property("mod.name").toString()
    val version = property("mod.version").toString()
    val group = property("mod.group").toString()
    val description = property("mod.description").toString()
    val source = property("mod.source").toString()
    val issues = property("mod.issues").toString()
    val license = property("mod.license").toString()
    val modrinth = property("mod.modrinth").toString()
    val curseforge = property("mod.curseforge").toString()

    val mcVersion = property("mod.mc_version").toString()
    val mcVersionRange = property("mod.mc_version_range").toString()
}

class Dependencies {
    val neoForgeVersion = property("deps.neoforge_version").toString()

    val fabricLoaderVersion = property("deps.fabric_loader_version").toString()
    val fabricApiVersion = property("deps.fabric_api_version").toString()

    val mixinconstraintsVersion = property("deps.mixinconstraints_version").toString()
    val mixinsquaredVersion = property("deps.mixinsquared_version").toString()
}

class LoaderData {
    val loader = loom.platform.get().name.lowercase()
    val isFabric = loader == "fabric"
    val isNeoForge = loader == "neoforge"
}

val mod = ModData()
val deps = Dependencies()
val loader = LoaderData()

version = mod.version
group = mod.group
base {
    archivesName.set("${mod.id}-${mod.version}-${mod.mcVersion}+${loader.loader}")
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">=1.21.11")
        replace("ResourceLocation", "Identifier")
    }
}

blossom {
    replaceToken("@MODID@", mod.id)
}

loom {
    silentMojangMappingsLicense()
    runConfigs.all {
        ideConfigGenerated(stonecutter.current.isActive)
    }

    runConfigs.remove(runConfigs["server"]) // Removes server run configs
}

loom.runs {
    afterEvaluate {
        val mixinJarFile = configurations.runtimeClasspath.get().incoming.artifactView {
            componentFilter {
                it is ModuleComponentIdentifier && it.group == "net.fabricmc" && it.module == "sponge-mixin"
            }
        }.files.first()
        configureEach {
            vmArg("-javaagent:$mixinJarFile") // Mixin Hotswap doesn't work on NeoForge, but doesn't hurt to keep
            property("mixin.hotSwap", "true")
            property("mixin.debug.export", "true") // Puts mixin outputs in /run/.mixin.out
        }
    }
}

fletchingTable {
    mixins.create("main") {
        mixin("default", "${mod.id}.mixins.json")
    }

    lang.create("main") {
        patterns.add("assets/${mod.id}/lang/**")
    }
}

repositories {
    mavenCentral()
    maven("https://maven.parchmentmc.org") // Parchment
    maven("https://maven.neoforged.net/releases") // NeoForge
    maven("https://maven.bawnorton.com/releases") // MixinSquared
    maven("https://api.modrinth.com/maven") // Modrinth
}

dependencies {
    minecraft("com.mojang:minecraft:${mod.mcVersion}")

    @Suppress("UnstableApiUsage")
    mappings(loom.layered {
        // Mojmap mappings
        officialMojangMappings()
        // Parchment mappings (it adds parameter mappings & javadoc)
        optionalProp("deps.parchment_version") {
            var snapshot = !mod.mcVersion.toString().contains(".")
            parchment("org.parchmentmc.data:parchment-${if (snapshot) "1.21.10" else mod.mcVersion}:$it@zip")
        }
    })

    include(implementation("com.moulberry:mixinconstraints:${deps.mixinconstraintsVersion}")!!)!!
    include(implementation(annotationProcessor("com.github.bawnorton.mixinsquared:mixinsquared-${loader.loader}:${deps.mixinsquaredVersion}")!!)!!)
    if (loader.isFabric) {
        modImplementation("net.fabricmc:fabric-loader:${deps.fabricLoaderVersion}")
        modImplementation(fabricApi.module("fabric-resource-loader-v0", deps.fabricApiVersion)) // NOTE: Required for the /resources/assets/ files to be laoded by the game
    } else if (loader.isNeoForge) {
        "neoForge"("net.neoforged:neoforge:${deps.neoForgeVersion}")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.processResources {
    val props = buildMap {
        put("id", mod.id)
        put("name", mod.name)
        put("version", mod.version)
        put("description", mod.description)
        put("source", mod.source)
        put("issues", mod.issues)
        put("license", mod.license)
        put("modrinth", mod.modrinth)
        put("curseforge", mod.curseforge)
        if (loader.isFabric) {
            put("fabric_loader_version", deps.fabricLoaderVersion)
        } else if (loader.isNeoForge) {
            put("neoforge_version", deps.neoForgeVersion)
        }

        val mcVersionRange = if (mod.mcVersionRange.contains(' ')) {
            val parts = mod.mcVersionRange.trim().split(' ')
            ">=" + parts.first() + ' ' + "<=" + parts.last()
        } else {
            mod.mcVersionRange
        }

        put("minecraft_version_range", mcVersionRange)
    }

    props.forEach(inputs::property)
    filesMatching("**/lang/en_us.json") { // Defaults description to English translation
        expand(props)
        filteringCharset = "UTF-8"
    }

    if (loader.isFabric) {
        filesMatching("fabric.mod.json") { expand(props) }
        exclude(listOf("META-INF/neoforge.mods.toml"))
    }

    if (loader.isNeoForge) {
        filesMatching("META-INF/neoforge.mods.toml") { expand(props) }
        exclude(listOf("fabric.mod.json"))
    }
}

if (stonecutter.current.isActive) {
    rootProject.tasks.register("buildActive") {
        group = "project"
        dependsOn(tasks.named("build"))
    }
}

fun <T> optionalProp(property: String, block: (String) -> T?): T? =
    findProperty(property)?.toString()?.takeUnless { it.isBlank() }?.let(block)
