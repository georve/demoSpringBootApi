package com.georve.demoSpringBootApi.services;

import com.georve.demoSpringBootApi.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractBaseService<T extends AbstractBaseEntity, ID extends Serializable>{

    public abstract List<T> findAll();

    public T saveOrUpdate(T entity);

    public double count();

    public abstract Optional<T> findById(ID entityId);

    public abstract void deleteById(ID entityId);

    public abstract void delete(T entity);

    public abstract boolean exists(T entity);
}

