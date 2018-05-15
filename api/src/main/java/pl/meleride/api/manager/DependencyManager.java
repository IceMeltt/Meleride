package pl.meleride.api.manager;

import java.util.List;

public interface DependencyManager {

  void registerDependency(Class<?> clazz, Object instance);

  void injectDependencies(Object... dependenciedInstances);

}
