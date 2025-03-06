/*
 * A series of hardcoded tasks that delegate to NPM.
 * I want the UI to be built with native tooling but still executed via
 * stupid simple Gradle tasks.
 */

tasks.register<Exec>("npmInstall") {
    inputs.files("package.json")
    outputs.files("package-lock.json")
    outputs.dirs("node_modules")

    commandLine("npm", "install")
}

tasks.register<Exec>("npmBuild") {
    dependsOn("npmInstall")

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

tasks.register("build") {
    dependsOn("npmBuild")
}

/*
 * Task for running the Vite dev server.
 * The code should ideally work regardless of if it's hosted from
 * Vite's dev server or Spring Boot.
 */
tasks.register<Exec>("viteRun") {
    dependsOn("npmInstall")

    commandLine("npm", "run", "dev")
}
