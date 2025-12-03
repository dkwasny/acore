package net.kwas.acore.server.dbc;

import net.kwas.acore.dbc.reader.DbcReader;
import net.kwas.acore.server.AcoreConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class DbcMgr {

  private final DbcReader reader;

  @Autowired
  public DbcMgr(AcoreConfig config) {
    var dbcPath = Path.of(config.dbcPath());
    this.reader = new DbcReader(dbcPath);
  }

  public DbcMgr(DbcReader reader) {
    this.reader = reader;
  }

  public DbcReader getReader() {
    return reader;
  }

}
