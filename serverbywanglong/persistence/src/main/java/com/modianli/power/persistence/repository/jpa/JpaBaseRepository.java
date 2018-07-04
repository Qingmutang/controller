package com.modianli.power.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by gao on 17-3-3.
 */
@NoRepositoryBean
public interface JpaBaseRepository <T, ID extends Serializable> extends JpaRepository<T, ID> {
}
