package com.xiaocm.integration.sync.infrastructure.factory;

import org.springframework.stereotype.Component;

import com.xiaocm.integration.sync.domain.SyncType;
import com.xiaocm.integration.sync.domain.strategy.SyncStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.EnumMap;
import java.util.List;

@Component
public class SyncTypeFactory {

    private final Map<SyncType, SyncStrategy> strategies;

    @Autowired
    public SyncTypeFactory(List<SyncStrategy> strategyList) {
        strategies = new EnumMap<>(SyncType.class);
        for (SyncStrategy strategy : strategyList) {
            strategies.put(strategy.getSyncType(), strategy);
        }
    }

    public SyncStrategy createSyncStrategy(SyncType type) {
        SyncStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported sync type: " + type);
        }
        return strategy;
    }
}
