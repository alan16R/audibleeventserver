package com.mechnicality.audibleeventserver.controller;

import com.mechnicality.audibleeventserver.service.UdpSenderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("event")
public class RestController {

    private final UdpSenderService senderService;
    public RestController(UdpSenderService senderService){

        this.senderService = senderService;
    }

    @PutMapping(path= "/enable/{onOff}")
    public void handleOnOff(
            @PathVariable(name = "onOff") boolean onOff
    ) {
        this.senderService.enable(onOff);
    }

}
