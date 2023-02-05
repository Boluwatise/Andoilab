package com.example.androidlabs;

public class Todo {
  String name;
  boolean urgent;

  public Todo(String name, boolean urgent) {
    this.name = name;
    this.urgent = urgent;
  }

  public String getName() {
    return name;
  }
  public boolean getUrgent() {
    return urgent;
  }
}
