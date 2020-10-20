package spark.utils;


import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Options.Buffer;


import java.io.IOException;


public enum CustomHelper implements Helper<Object> {

    ifEqual{
        @Override
        public Object apply(Object o1, Options options) throws IOException {
            Buffer buffer = options.buffer();
            if(o1 instanceof String) {
                String s2 = options.param(0, null);
                if(s2.equals(o1)){
                    buffer.append(options.fn());
                }else{
                    buffer.append(options.inverse());
                }
            }else{
                if(o1 == options.param(0)){
                    buffer.append(options.fn());
                }else{
                    buffer.append(options.inverse());
                }
            }
            return buffer;
        }
    },

}
