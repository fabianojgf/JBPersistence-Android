package org.jb.persistence.generic;

import java.io.IOException;
import java.util.List;

/**
 * Created by fabiano on 23/04/17.
 */

public abstract class DAO {
    public DAO() {
        super();
    }

    public abstract String insert(Object object) throws IOException;

    public abstract String update(Object object) throws IOException;

    public abstract String delete(Class<?> c, Integer id) throws IOException;

    public abstract Object findById(Class<?> c, Integer id) throws IOException;

    public abstract List<?> find(Class<?> c, String[] params, Object[] values) throws IOException;

    public abstract List<?> findAll(Class<?> c) throws IOException;
}
