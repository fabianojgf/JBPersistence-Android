package org.jb.persistence.sqlite.dao;

import org.jb.persistence.generic.DAO;

import java.io.IOException;
import java.util.List;

/**
 * Created by fabiano on 23/04/17.
 */

public class SqliteDAO extends DAO {
    public SqliteDAO() {
        super();
    }

    public String create() {
        return null;
    }

    @Override
    public String insert(Object object) throws IOException {
        return null;
    }

    @Override
    public String update(Object object) throws IOException {
        return null;
    }

    @Override
    public String delete(Class<?> c, Integer id) throws IOException {
        return null;
    }

    @Override
    public Object findById(Class<?> c, Integer id) throws IOException {
        return null;
    }

    @Override
    public List<?> find(Class<?> c, String[] params, Object[] values) throws IOException {
        return null;
    }

    @Override
    public List<?> findAll(Class<?> c) throws IOException {
        return null;
    }
}
