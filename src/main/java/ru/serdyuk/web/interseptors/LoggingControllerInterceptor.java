package ru.serdyuk.web.interseptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggingControllerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,  Object handler) throws Exception {
        log.info("LoggingControllerInterceptor.preHandle -> The request is being prepared to be sent to the controller");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("LoggingControllerInterceptor.postHandle -> The request has been processed. Preparing for shipment to the customer");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("LoggingControllerInterceptor.afterCompletion -> The response has been sent to the client.");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
