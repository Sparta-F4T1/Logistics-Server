package com.logistic.user.application.port.in.command;

import java.util.Objects;


public record TestCommand (String id){
  public TestCommand {
    Objects.requireNonNull(id);
  }
}

