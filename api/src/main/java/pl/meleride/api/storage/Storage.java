package pl.meleride.api.storage;

public interface Storage {

  void open() throws StorageException;

  void close() throws StorageException;

  boolean state() throws StorageException;

}
