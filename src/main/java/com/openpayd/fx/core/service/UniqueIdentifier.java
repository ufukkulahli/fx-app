package com.openpayd.fx.core.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UniqueIdentifier {
    public UUID getRandom() {
        return UUID.randomUUID();
    }
}
