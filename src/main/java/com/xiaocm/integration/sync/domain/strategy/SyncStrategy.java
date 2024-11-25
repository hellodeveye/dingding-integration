package com.xiaocm.integration.sync.domain.strategy;

import com.xiaocm.integration.sync.domain.SyncType;

public interface SyncStrategy {
    SyncType getSyncType();
    void sync(String organizationId);
}
