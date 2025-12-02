/*
 * A series of hardcoded tasks that delegate to NPM.
 * I want the UI to be built with native tooling but still executed via
 * stupid simple Gradle tasks.
 */
val npmInstall by tasks.registering(Exec::class) {
    inputs.files("package.json")
    outputs.files("package-lock.json")
    outputs.dirs("node_modules")

    commandLine("npm", "install")
}

val npmBuild by tasks.registering(Exec::class) {
    dependsOn(npmInstall)

    inputs.files(
        "eslint.config.js",
        "index.html",
        "package-lock.json",
        "tsconfig.app.json",
        "tsconfig.json",
        "tsconfig.node.json",
        "vite.config.ts"
    )
    inputs.dir("node_modules")
    inputs.dir("src")
    inputs.dir("public")
    outputs.dirs("dist")

    commandLine("npm", "run", "build")
}

val createJar by tasks.registering(Jar::class) {
    dependsOn(npmBuild)

    from("dist")
    into("static")

    archiveBaseName = project.name
    archiveVersion = project.version.toString()
    destinationDirectory = layout.buildDirectory
}

val build by tasks.registering {
    dependsOn(createJar)
}

/*
 * Custom configuration and artifact specification.
 * This seems to be the bare minimum for exporting a jar to the java plugin
 * within a different module.
 */
val javaArtifactConfigName = "javaArtifact"
val javaArtifactConfig = configurations.create(javaArtifactConfigName)
javaArtifactConfig.attributes {
    attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 21)
}

artifacts {
    add(javaArtifactConfigName, createJar)
}

/*
 * Task for running the Vite dev server.
 * Using the Vite server allows for much faster turnaround when editing the UI code.
 */
val viteRun by tasks.registering(Exec::class) {
    dependsOn(npmInstall)

    commandLine("npm", "run", "dev")
}

/*
 * Custom clean task for both output directories.
 */
val clean by tasks.registering(Delete::class) {
    delete("dist")
    delete("build")
}

val npmClean by tasks.registering(Delete::class) {
    delete("node_modules")
}
