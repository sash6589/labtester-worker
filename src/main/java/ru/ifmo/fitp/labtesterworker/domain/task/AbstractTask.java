package ru.ifmo.fitp.labtesterworker.domain.task;

import java.util.Map;

public abstract class AbstractTask implements Task {

    protected Map<String, Object> storage;

    public Task setStorage(Map<String, Object> storage) {
        this.storage = storage;
        return this;
    }
}
