package ru.weather.bot.exception;

public class LocationNotFoundException extends RuntimeException {
  public LocationNotFoundException(String message) {
    super(message);
  }
}
