package io.simpolor.boardcomment.utils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.util.StringUtils;

public class QuerydslUtils {

    public static BooleanExpression likeOperation(StringPath column, String value) {

        if(StringUtils.isEmpty(value)){
            return null;
        }

        return column.upper().like(value+"%");
    }
}
