package com.wsm.common.dao;

import com.wsm.common.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface IBaseDao<T extends BaseModel, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
