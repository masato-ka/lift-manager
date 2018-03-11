package ka.masato.lift.liftdevicemanager.application;

import ka.masato.lift.liftdevicemanager.async.AsyncEventPush;
import ka.masato.lift.liftdevicemanager.domain.model.Lift;
import ka.masato.lift.liftdevicemanager.domain.service.LiftsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/stream/sse")
public class SseApi {

    private final LiftsService liftsService;
    private final AsyncEventPush asyncEventPush;

    public SseApi(LiftsService liftsService, AsyncEventPush asyncEventPush) {
        this.liftsService = liftsService;
        this.asyncEventPush = asyncEventPush;
    }

    @GetMapping("/{liftId}")
    public SseEmitter sse(@PathVariable Integer liftId) throws IOException, InterruptedException {
        Lift lift = liftsService.getLiftById(liftId);

        SseEmitter sseEmitter = new SseEmitter(60000L);
        asyncEventPush.eventPush(sseEmitter, lift);
        return sseEmitter;
    }

}
