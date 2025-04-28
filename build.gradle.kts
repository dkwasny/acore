
group = "net.kwas.acore"
version = "0.0.1-SNAPSHOT"

tasks.register<Sync>("syncUiToServer") {
    dependsOn("acore-ui:build")
    from("acore-ui/dist")
    into("acore-server/src/main/resources/static")
}
