
/*
 * Task for copying the bundled UI code to the src/main/resources/static
 * folder in the server project.
 * This is quite gnarly, but I hit issues trying a more elegant approach via
 * artifacts and configurations.  Maybe revisit down the road...
 */
tasks.register<Sync>("syncUiToServer") {
    dependsOn("acore-app-ui:build")
    from("acore-app-ui/dist")
    into("acore-app-server/src/main/resources/static")
}
