package net.kwas.acore.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Figure out how to use annotation to intercept methods for timing purposes!
public class Stopwatch {

  private static final Logger LOG = LoggerFactory.getLogger(Stopwatch.class);

  public static Stopwatch start(String label) {
    var stopwatch = new Stopwatch(label);
    stopwatch.start();
    return stopwatch;
  }

  private final String label;
  private long startTime = 0L;

  public Stopwatch(String label) {
    this.label = label;
  }

  public void start() {
    startTime = System.currentTimeMillis();
  }

  public void stop() {
    var time = System.currentTimeMillis() - startTime;
    var msg = label + " finished in " + time + "ms";
    LOG.info(msg);
  }

}
