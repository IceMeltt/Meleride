package pl.meleride.api.helper;

public interface StatusChangeable<T extends Enum<?>> {
  
  void setStatus(T status);
  
  T getStatus();
  
}
