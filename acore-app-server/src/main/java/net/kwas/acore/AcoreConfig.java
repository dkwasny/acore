package net.kwas.acore;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("acore")
public record AcoreConfig(
    String dbcPath
) {
}
