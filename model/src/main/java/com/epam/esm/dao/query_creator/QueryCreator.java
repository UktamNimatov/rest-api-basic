package com.epam.esm.dao.query_creator;

import com.epam.esm.dao.mapper.ColumnName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.StringJoiner;

@Component
public class QueryCreator {
    private static final Logger logger = LogManager.getLogger();
    private static final String COMMA_WHITESPACE = ", ";
    private static final String WHITE_SPACE = " ";
    private static final String EQUALS = "=";
    private static final String UPDATE = "UPDATE ";
    private static final String SET = " SET ";
    private static final String WHERE_ID = "WHERE id=";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_CREATE_DATE = "sortByCreateDate";
    private static final String SORT_BY_LAST_UPDATE_DATE = "sortByLastUpdateDate";

    public String createUpdateQuery(Map<String, String> fields, String tableName) {
        StringJoiner joiner = new StringJoiner(COMMA_WHITESPACE);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getValue() != null && !entry.getKey().equals(ColumnName.ID)) {
                joiner.add(entry.getKey() + EQUALS + '\'' + entry.getValue() + '\'');
            }
        }
        logger.info("current query is === " + "UPDATE " + tableName + " SET " + joiner + " WHERE id=" + fields.get("id"));
        return UPDATE + tableName + SET + joiner + WHERE_ID + fields.get(ColumnName.ID);
    }

    public String createSortQuery(Map<String, String> fields, String query) {
        if (fields.isEmpty()) {
            return "";
        }
        query += ORDER_BY;
        StringJoiner joiner = new StringJoiner(COMMA_WHITESPACE);
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getKey().equals(SORT_BY_NAME)) {
                joiner.add(ColumnName.NAME + WHITE_SPACE + entry.getValue());
            }
            if (entry.getKey().equals(SORT_BY_CREATE_DATE)) {
                joiner.add(ColumnName.CREATE_DATE + WHITE_SPACE + entry.getValue());
            }
            if (entry.getKey().equals(SORT_BY_LAST_UPDATE_DATE)) {
                joiner.add(ColumnName.LAST_UPDATE_DATE + WHITE_SPACE + entry.getValue());
            }
        }
        logger.info("current sort query is === "+ query + joiner);
        return query + joiner;
    }
}
