package org.jb.persistence.web.dao;

import org.jb.common.config.enums.MappingType;
import org.jb.common.dictionary.project.ProjectMapping;
import org.jb.common.dictionary.project.enums.ProjectLayer;
import org.jb.persistence.generic.DAO;
import org.jb.persistence.web.annotation.PathParam;
import org.jb.persistence.web.annotation.QueryParam;
import org.jb.persistence.web.annotation.Request;
import org.jb.persistence.web.annotation.WebAggregation;
import org.jb.persistence.web.annotation.WebComposition;
import org.jb.persistence.web.annotation.WebEntity;
import org.jb.persistence.web.request.RequestManager;
import org.jb.persistence.web.xml.XMLPathParam;
import org.jb.persistence.web.xml.XMLQueryParam;
import org.jb.persistence.web.xml.XMLRequest;
import org.jb.persistence.web.xml.XMLWebAggregation;
import org.jb.persistence.web.xml.XMLWebComposition;
import org.jb.persistence.web.xml.XMLWebEntity;
import org.jb.stream.annotation.StreamEntity;
import org.jb.stream.processor.StreamDriveType;
import org.jb.stream.processor.StreamProcessor;
import org.jb.stream.reflection.util.FieldUtil;
import org.jb.stream.xml.XMLStreamEntity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by fabiano on 19/04/17.
 */

public class WebDAO extends DAO {
    private RequestManager manager;

    public WebDAO() {
        super();
        manager = new RequestManager();
    }

    public String insert(Object object) throws IOException {
        XMLWebEntity webEntity = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(object.getClass().getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webEntity = XMLWebEntity.adapt(object.getClass().getAnnotation(WebEntity.class));
            request = webEntity.getInsert();
        }
        else if(ProjectMapping.getMappingType(object.getClass().getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webEntity = XMLWebEntity.getAnnotation(object.getClass());
            request = webEntity.getInsert();
        }
        else {
            //DO NOTHING
        }

        StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
        processor.configure(object.getClass());
        String xml = processor.write(object);

        return manager.sendRequest(request, xml);
    }

    public String update(Object object) throws IOException {
        XMLWebEntity webEntity = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(object.getClass().getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webEntity = XMLWebEntity.adapt(object.getClass().getAnnotation(WebEntity.class));
            request = webEntity.getUpdate();
        }
        else if(ProjectMapping.getMappingType(object.getClass().getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webEntity = XMLWebEntity.getAnnotation(object.getClass());
            request = webEntity.getUpdate();
        }
        else {
            //DO NOTHING
        }

        StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
        processor.configure(object.getClass());
        String xml = processor.write(object);

        return manager.sendRequest(request, xml);
    }

    public String delete(Class<?> c, Integer id) throws IOException {
        XMLWebEntity webEntity = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webEntity = XMLWebEntity.adapt(c.getAnnotation(WebEntity.class));
            request = webEntity.getDelete();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webEntity = XMLWebEntity.getAnnotation(c);
            request = webEntity.getDelete();
        }
        else {
            //DO NOTHING
        }

        XMLPathParam[] pathParams = request.getPathParameters();
        String[] pathTexts = null;
        Object[] pathValues = null;

        if(pathParams != null && pathParams.length > 0) {
            pathTexts = new String[1];
            pathValues = new Object[1];

            for(int i = 0; i < 1; i++) {
                pathTexts[i] = pathParams[i].getParam();
                pathValues[i] = id;
            }
        }

        XMLQueryParam[] queryParams = request.getQueryParameters();
        String[] queryTexts = null;
        Object[] queryValues = null;

        if(queryParams != null && queryParams.length > 0) {
            queryTexts = new String[1];
            queryValues = new Object[1];

            for(int i = 0; i < 1; i++) {
                queryTexts[i] = queryParams[i].getParam();
                queryValues[i] = id;
            }
        }

        String result = manager.sendRequest(request, null, pathTexts, pathValues, queryTexts, queryValues);

        StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
        processor.configure(c);

        return manager.sendRequest(request, result);
    }

    public Object findById(Class<?> c, Integer id) throws IOException {
        XMLWebEntity webEntity = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webEntity = XMLWebEntity.adapt(c.getAnnotation(WebEntity.class));
            request = webEntity.getFindById();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webEntity = XMLWebEntity.getAnnotation(c);
            request = webEntity.getFindById();
        }
        else {
            //DO NOTHING
        }

        XMLPathParam[] pathParams = request.getPathParameters();
        String[] pathTexts = null;
        Object[] pathValues = null;

        if(pathParams != null && pathParams.length > 0) {
            pathTexts = new String[1];
            pathValues = new Object[1];

            for(int i = 0; i < 1; i++) {
                pathTexts[i] = pathParams[i].getParam();
                pathValues[i] = id;
            }
        }

        XMLQueryParam[] queryParams = request.getQueryParameters();
        String[] queryTexts = null;
        Object[] queryValues = null;

        if(queryParams != null && queryParams.length > 0) {
            queryTexts = new String[1];
            queryValues = new Object[1];

            for(int i = 0; i < 1; i++) {
                queryTexts[i] = queryParams[i].getParam();
                queryValues[i] = id;
            }
        }

        String result = manager.sendRequest(request, null, pathTexts, pathValues, queryTexts, queryValues);

        StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
        processor.configure(c);

        return (Object) processor.read(result);
    }

    public List<?> find(Class<?> c, String[] params, Object[] values) throws IOException {
        XMLWebEntity webEntity = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webEntity = XMLWebEntity.adapt(c.getAnnotation(WebEntity.class));
            request = webEntity.getFind();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webEntity = XMLWebEntity.getAnnotation(c);
            request = webEntity.getFind();
        }
        else {
            //DO NOTHING
        }

        XMLPathParam[] pathParams = request.getPathParameters();
        String[] pathTexts = null;
        Object[] pathValues = null;

        if(pathParams != null && pathParams.length > 0) {
            pathTexts = new String[params.length];
            pathValues = new Object[params.length];

            for(int i = 0; i < params.length; i++) {
                for(int j = 0; j < pathParams.length; j++) {
                    if(pathParams[j].getParam().equals(params[i])) {
                        pathTexts[i] = params[i];
                        pathValues[i] = values[i];
                        break;
                    }
                }
            }
        }

        XMLQueryParam[] queryParams = request.getQueryParameters();
        String[] queryTexts = null;
        Object[] queryValues = null;

        if(queryParams != null && queryParams.length > 0) {
            queryTexts = new String[params.length];
            queryValues = new Object[params.length];

            for(int i = 0; i < params.length; i++) {
                for(int j = 0; j < queryParams.length; j++) {
                    if(queryParams[j].getParam().equals(params[i])) {
                        queryTexts[i] = params[i];
                        queryValues[i] = values[i];
                        break;
                    }
                }
            }
        }

        String result = manager.sendRequest(request, null, pathTexts, pathValues, queryTexts, queryValues);

        StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
        processor.configure(c);

        return (List<?>) processor.read(result);
    }

    public List<?> findAll(Class<?> c) throws IOException {
        XMLWebEntity webEntity = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webEntity = XMLWebEntity.adapt(c.getAnnotation(WebEntity.class));
            request = webEntity.getFindAll();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webEntity = XMLWebEntity.getAnnotation(c);
            request = webEntity.getFindAll();
        }
        else {
            //DO NOTHING
        }

        String result = manager.sendRequest(request, null);

        StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
        processor.configure(c);

        return (List<?>) processor.read(result);
    }

    /* Operações que envolvem Join */

    public String addIntoCollection(Class<?> c, String collection, String[] params, Object[] values) throws NoSuchFieldException, IOException {
        Field field = c.getDeclaredField(collection);

        XMLWebAggregation webAggregation = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webAggregation = XMLWebAggregation.adapt(field.getAnnotation(WebAggregation.class));
            request = webAggregation.getInsert();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webAggregation = XMLWebAggregation.getAnnotation(field);
            request = webAggregation.getInsert();
        }
        else {
            //DO NOTHING
        }

        if(request != null) {
            XMLPathParam[] pathParams = request.getPathParameters();
            String[] pathTexts = null;
            Object[] pathValues = null;

            if (pathParams != null && pathParams.length > 0) {
                pathTexts = new String[params.length];
                pathValues = new Object[params.length];

                for (int i = 0; i < params.length; i++) {
                    for (int j = 0; j < pathParams.length; j++) {
                        if (pathParams[j].getParam().equals(params[i])) {
                            pathTexts[i] = params[i];
                            pathValues[i] = values[i];
                            break;
                        }
                    }
                }
            }

            XMLQueryParam[] queryParams = request.getQueryParameters();
            String[] queryTexts = null;
            Object[] queryValues = null;

            if (queryParams != null && queryParams.length > 0) {
                queryTexts = new String[params.length];
                queryValues = new Object[params.length];

                for (int i = 0; i < params.length; i++) {
                    for (int j = 0; j < queryParams.length; j++) {
                        if (queryParams[j].getParam().equals(params[i])) {
                            queryTexts[i] = params[i];
                            queryValues[i] = values[i];
                            break;
                        }
                    }
                }
            }

            String result = manager.sendRequest(request, null, pathTexts, pathValues, queryTexts, queryValues);

            return result;
        }

        return null;
    }

    public String removeFromCollection(Class<?> c, String collection, String[] params, Object[] values) throws NoSuchFieldException, IOException {
        Field field = c.getDeclaredField(collection);

        XMLWebAggregation webAggregation = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webAggregation = XMLWebAggregation.adapt(field.getAnnotation(WebAggregation.class));
            request = webAggregation.getDelete();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webAggregation = XMLWebAggregation.getAnnotation(field);
            request = webAggregation.getDelete();
        }
        else {
            //DO NOTHING
        }

        if(request != null) {
            XMLPathParam[] pathParams = request.getPathParameters();
            String[] pathTexts = null;
            Object[] pathValues = null;

            if (pathParams != null && pathParams.length > 0) {
                pathTexts = new String[params.length];
                pathValues = new Object[params.length];

                for (int i = 0; i < params.length; i++) {
                    for (int j = 0; j < pathParams.length; j++) {
                        if (pathParams[j].getParam().equals(params[i])) {
                            pathTexts[i] = params[i];
                            pathValues[i] = values[i];
                            break;
                        }
                    }
                }
            }

            XMLQueryParam[] queryParams = request.getQueryParameters();
            String[] queryTexts = null;
            Object[] queryValues = null;

            if (queryParams != null && queryParams.length > 0) {
                queryTexts = new String[params.length];
                queryValues = new Object[params.length];

                for (int i = 0; i < params.length; i++) {
                    for (int j = 0; j < queryParams.length; j++) {
                        if (queryParams[j].getParam().equals(params[i])) {
                            queryTexts[i] = params[i];
                            queryValues[i] = values[i];
                            break;
                        }
                    }
                }
            }

            String result = manager.sendRequest(request, null, pathTexts, pathValues, queryTexts, queryValues);

            return result;
        }

        return null;
    }

    public List<?> listCollection(Class<?> c, String collection, String[] params, Object[] values) throws NoSuchFieldException, IOException {
        Field field = c.getDeclaredField(collection);
        Class<?> targetClass = FieldUtil.getCollectionType(field);

        XMLWebAggregation webAggregation = null;
        XMLWebComposition webComposition = null;
        XMLRequest request = null;

        if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.ANNOTATION) {
            webAggregation = XMLWebAggregation.adapt(field.getAnnotation(WebAggregation.class));
            webComposition = XMLWebComposition.adapt(field.getAnnotation(WebComposition.class));

            if(webAggregation != null) request = webAggregation.getList();
            if(webComposition != null) request = webComposition.getList();
        }
        else if(ProjectMapping.getMappingType(c.getClassLoader(), ProjectLayer.PERSISTENCE)
                == MappingType.XML) {
            webAggregation = XMLWebAggregation.getAnnotation(field);
            webComposition = XMLWebComposition.getAnnotation(field);

            if(webAggregation != null) request = webAggregation.getList();
            if(webComposition != null) request = webComposition.getList();
        }
        else {
            //DO NOTHING
        }

        if(request != null) {
            XMLPathParam[] pathParams = request.getPathParameters();
            String[] pathTexts = null;
            Object[] pathValues = null;

            if(pathParams != null && pathParams.length > 0) {
                pathTexts = new String[params.length];
                pathValues = new Object[params.length];

                for(int i = 0; i < params.length; i++) {
                    for(int j = 0; j < pathParams.length; j++) {
                        if(pathParams[j].getParam().equals(params[i])) {
                            pathTexts[i] = params[i];
                            pathValues[i] = values[i];
                            break;
                        }
                    }
                }
            }

            XMLQueryParam[] queryParams = request.getQueryParameters();
            String[] queryTexts = null;
            Object[] queryValues = null;

            if(queryParams != null && queryParams.length > 0) {
                queryTexts = new String[params.length];
                queryValues = new Object[params.length];

                for(int i = 0; i < params.length; i++) {
                    for(int j = 0; j < queryParams.length; j++) {
                        if(queryParams[j].getParam().equals(params[i])) {
                            queryTexts[i] = params[i];
                            queryValues[i] = values[i];
                            break;
                        }
                    }
                }
            }

            String result = manager.sendRequest(request, null, pathTexts, pathValues, queryTexts, queryValues);

            StreamProcessor processor  = new StreamProcessor(StreamDriveType.XML);
            processor.configure(targetClass);

            return (List<?>) processor.read(result);
        }

        return null;
    }
}
