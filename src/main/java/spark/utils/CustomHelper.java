package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public enum CustomHelper implements Helper<Object> {

    isEqual{
        @Override
        public Object apply(Object o1, Options options) {
            if(o1 instanceof String) {
                String s2 = options.param(0, null);
                return s2.equals(o1);
            }else return o1 == options.param(0);
        }
    },

}
