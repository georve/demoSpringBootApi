package com.georve.demoSpringBootApi.repository;

import com.georve.demoSpringBootApi.model.AbstractBaseEntity;
import com.georve.demoSpringBootApi.services.AbstractBaseService;
import com.georve.demoSpringBootApi.utils.GenericsQualify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class AbstractBaseRepositoryImpl<T extends AbstractBaseEntity, ID extends Serializable>
        implements AbstractBaseService<T,ID> {


    private AbstractBaseRepository<T, ID> abstractBaseRepository;

    @Autowired
    public AbstractBaseRepositoryImpl(AbstractBaseRepository<T, ID> abstractBaseRepository) {
        this.abstractBaseRepository = abstractBaseRepository;
    }

    @Override
    public List<T> findAll() {
        return abstractBaseRepository.findAll();
    }

    @Override
    public T saveOrUpdate(T entity) {
        return (T) abstractBaseRepository.saveAndFlush(entity);
    }

    @Override
    public double count() {
        return abstractBaseRepository.count();
    }

    @Override
    public T findById(ID entityId) {
        return  abstractBaseRepository.getOne(entityId);
    }

    @Override
    public void deleteById(ID entityId) {
         abstractBaseRepository.deleteById(entityId);
    }

    @Override
    public void delete(T entity) {
        abstractBaseRepository.delete(entity);
    }

    @Override
    public boolean exists(T entity) {
        return abstractBaseRepository.exists(Example.of(entity));
    }
}
