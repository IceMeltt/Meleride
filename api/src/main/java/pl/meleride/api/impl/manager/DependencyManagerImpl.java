package pl.meleride.api.impl.manager;

import pl.meleride.api.annotation.Dependency;
import pl.meleride.api.manager.DependencyManager;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class DependencyManagerImpl implements DependencyManager {

  private final Map<Class<?>, Object> dependencies = new LinkedHashMap<>();

  @Override
  public void registerDependency(Class<?> clazz, Object instance) {
    if (this.dependencies.containsKey(clazz) && this.dependencies.containsValue(instance)) {
      throw new IllegalStateException("Dependency: " + clazz.getName() + " with value: " + instance + " is already registered!");
    }

    this.dependencies.put(clazz, instance);
  }

  @Override
  public void injectDependencies(Object... dependenciedInstances) {

    for (Object dependenciedInstance : dependenciedInstances) {
      for (Field field : dependenciedInstance.getClass().getDeclaredFields()) {
        if (field.isAnnotationPresent(Dependency.class)) {

          Object instance = this.dependencies.get(field.getType());

          if (instance == null) {
            throw new IllegalStateException("Dependency is not registered!");
          }

          try {
            field.setAccessible(true);
            field.set(dependenciedInstance, instance);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }

    }
  }

}
