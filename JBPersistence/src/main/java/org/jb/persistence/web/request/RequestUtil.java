package org.jb.persistence.web.request;

import org.jb.persistence.web.annotation.QueryParam;
import org.jb.persistence.web.annotation.Request;
import org.jb.persistence.web.annotation.WebEntity;

/**
 * Created by fabiano on 05/06/17.
 */

public class RequestUtil {
    public enum EntityRequestType {
        INSERT,
        UPDATE,
        DELETE,
        FIND,
        FIND_BY_ID,
        FIND_ALL
    }

    public static Request getRequest(Class<?> c, EntityRequestType type) {
        WebEntity entity = c.getAnnotation(WebEntity.class);
        switch (type) {
            case INSERT:
                return entity.insert();
            case UPDATE:
                return entity.update();
            case DELETE:
                return entity.delete();
            case FIND:
                return entity.find();
            case FIND_BY_ID:
                return entity.findById();
            case FIND_ALL:
                return entity.findAll();
            default:
                return null;
        }
    }

    public static String[] getRequestParams(Class<?> c, EntityRequestType type) {
        Request request = getRequest(c, type);
        QueryParam[] params = request.queryParameters();
        String[] textParms = new String[params.length];
        for(int i = 0; i < params.length; i++) {
            textParms[i] = params[i].param();
        }
        return textParms;
    }
}
