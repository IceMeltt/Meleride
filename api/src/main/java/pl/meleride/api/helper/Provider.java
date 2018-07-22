package pl.meleride.api.helper;

public interface Provider<T> {

  boolean isAvailable();

  T get();

}
