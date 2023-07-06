package com.epam.cs.dao;

import com.epam.cs.entity.AbstractEntity;
import com.epam.cs.exception.DaoException;


import java.util.List;

public abstract class BaseDao<T extends AbstractEntity> {
    public  abstract  boolean insert(T t) throws DaoException;
    public  abstract  boolean delete(T t) throws DaoException;
    public abstract List<T> findAll( ) throws DaoException;
    public abstract boolean update(T t) throws DaoException;
}
