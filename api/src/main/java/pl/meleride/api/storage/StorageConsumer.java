package pl.meleride.api.storage;

@FunctionalInterface
public interface StorageConsumer<T> {

  void accept(T t) throws StorageException;

}
