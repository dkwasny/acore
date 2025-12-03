package net.kwas.acore.dbc.model;

public record DbcHeader(
  long recordCount,
  long fieldCount,
  long recordSize,
  long stringBlockSize
) {

  public long getRecordOffset() {
    return recordSize * recordCount;
  }

}
