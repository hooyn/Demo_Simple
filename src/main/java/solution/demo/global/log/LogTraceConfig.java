package hooyn.base.global.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Order(0)
@RequiredArgsConstructor
public class LogTraceConfig implements Filter {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // body 값을 사용하기 위해 HttpServletRequestWrapper 를 상속한 클래스 ContentCachingRequestWrapper를 사용합니다.
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        String method = ((HttpServletRequest) request).getMethod();
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        String queryString = ((HttpServletRequest) request).getQueryString();
        String clientIP = request.getRemoteAddr();

        // QueryString을 붙여서 URI를 생성합니다.
        if (queryString != null)
            requestURI = requestURI + "?" + queryString;

        // API가 실행 완료되는 시간을 알기 위한 로직을 작성합니다.
        long start = System.currentTimeMillis();
        chain.doFilter(requestWrapper, responseWrapper);
        long end = System.currentTimeMillis();

        // API Request에 대한 로그만 남깁니다.
        if(responseWrapper.getStatus()!=200){
            log.error("\n" +
                            "[REQUEST] {} - {} {} - {}\n" +
                            "ClientIP : {}\n" +
                            "RequestBody : {}\n" +
                            "Response : {}\n",
                    method,
                    requestURI,
                    responseWrapper.getStatus(),
                    (end - start) / 1000.0,
                    clientIP,
                    getRequestBody(requestWrapper),
                    getResponseBody(responseWrapper));
        } else {
            log.info("\n" +
                            "[REQUEST] {} - {} {} - {}\n" +
                            "ClientIP : {}\n" +
                            "RequestBody : {}\n" +
                            "Response : {}\n",
                    method,
                    requestURI,
                    responseWrapper.getStatus(),
                    (end - start) / 1000.0,
                    clientIP,
                    getRequestBody(requestWrapper),
                    getResponseBody(responseWrapper));
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request){

        String payload = " [] ";
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null){
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0){
                payload = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
            }
        }

        return payload;
    }

    private String getResponseBody(final HttpServletResponse response) throws IOException {

        String payload = " [] ";
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null){
            if (wrapper.getContentType() != null && wrapper.getContentType().contains("json")){
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0){
                    payload = new String(buf, 0, buf.length, StandardCharsets.UTF_8);

                    try {
                        payload = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.objectMapper.readTree(payload));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            wrapper.copyBodyToResponse();
        }

        return payload;
    }
}