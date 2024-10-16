package org.example.quiz.logger;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.logging.Logger;

public class TestLoggerExtension implements BeforeAllCallback, AfterAllCallback {

  private static final Logger log = Logger.getLogger(TestLoggerExtension.class.getName());

  @Override
  public void beforeAll(ExtensionContext context) {
    log.info(String.format("Test is started...%s", context.getDisplayName()));
  }

  @Override
  public void afterAll(ExtensionContext context) {
    log.info(String.format("Test is ended...%s", context.getDisplayName()));
  }
}