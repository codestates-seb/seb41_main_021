package com.yata.backend.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Collection;
@Component
@Slf4j
public class CustomBeanUtils<T> {
   public T copyNonNullProperties(T source, T destination) {
      if (source == null || destination == null || source.getClass() != destination.getClass()) {
         return null;
      }

      final BeanWrapper src = new BeanWrapperImpl(source);
      final BeanWrapper dest = new BeanWrapperImpl(destination);

      for (final Field property : source.getClass().getDeclaredFields()) {
         Object sourceProperty = src.getPropertyValue(property.getName());
         log.info(property.getClass().getName());
         log.info("sourceProperty : {}", sourceProperty);
         if (sourceProperty != null && !(sourceProperty instanceof Collection<?>)) {
            dest.setPropertyValue(property.getName(), sourceProperty);
         }
      }

      return destination;
   }
}