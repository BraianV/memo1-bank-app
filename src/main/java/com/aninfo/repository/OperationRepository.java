package com.aninfo.repository;

import com.aninfo.model.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OperationRepository extends CrudRepository<Operation, Integer> {

    Operation findOperationById(Integer id);
    @Override
    List<Operation> findAll();

}


