package com.bonc.medicine.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by Deng Jialong 2017/02/17
 */
public enum JacksonMapper {
    INSTANCE;
    private static final ObjectMapper mapper = new ObjectMapper().configure( JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true );
    private static Logger logger = LoggerFactory.getLogger( JacksonMapper.class );

    public Map< String, ? > readJsonToMap( String jsonData ) {
        Map map = new HashMap();
        try {
            map = mapper.readValue( jsonData, Map.class );
        } catch ( IOException e ) {
            logger.error( jsonData, e );
        }
        return map;
    }

    public List< String > readJsonToList( String jsonData ) {
        List list = new ArrayList();
        try {
            list = mapper.readValue( jsonData, List.class );
        } catch ( IOException e ) {
            logger.error( jsonData, e );
        }
        return list;
    }

    public String writeObjectToJson( Object obj ) {
        try {
            return mapper.writeValueAsString( obj );
        } catch ( JsonProcessingException e ) {
            logger.error( obj.toString(), e );
        }
        return "{}";
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
