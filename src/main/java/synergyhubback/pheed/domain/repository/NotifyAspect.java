package synergyhubback.pheed.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import synergyhubback.pheed.service.NotifyService;

@Aspect
@Slf4j
@Component
@EnableAsync
public class NotifyAspect {


    private final NotifyService notifyService;

    public NotifyAspect(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @Pointcut
    public void annotationPointcut() {
    }

//    @Async
//    @AfterReturning
//    public void checkValue(JoinPoint joinPoint, Object result) throws Throwable {
//        NotifyInfo  notifyProxy = (NotifyInfo) result;
//        notifyService.send(
//                notifyProxy.getReceiver(),
//                notifyProxy.getNotifycationType(),
//                NotifyMessege.Notify_NEW_REQUEST.getMessage(),
//                "/api/vi/pheed/" + (notifyProxy.getGoUrlId())
//        );
//        log.info("result = {}", result);
//    }



}
