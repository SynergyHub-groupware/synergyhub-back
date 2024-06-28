package synergyhubback.pheed.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import synergyhubback.pheed.aop.proxy.PheedInfo;
import synergyhubback.pheed.service.PheedService;

@Aspect
@Slf4j
@Component
@EnableAsync
public class PheedAspect {

    private final PheedService pheedService;

    public PheedAspect(PheedService pheedService) {
        this.pheedService = pheedService;
    }

    @Pointcut("@annotation(synergyhubback.pheed.annotation.NeedPheed)")
    public void annotationPointCut() {

    }


}
