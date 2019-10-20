package com.shop.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lihaodi on 2016/4/8.
 */
public class HttpResponseUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);
    private static PrintWriter printWrite;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void response(HttpServletResponse httpResponse, String responseContent) {
        try {
            printWrite = httpResponse.getWriter();
            printWrite.write(mapper.writeValueAsString(responseContent));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (null != printWrite)
                printWrite.close();
        }
    }
}
