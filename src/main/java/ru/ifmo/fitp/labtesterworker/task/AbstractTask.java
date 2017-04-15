package ru.ifmo.fitp.labtesterworker.task;

import java.util.Map;

public abstract class AbstractTask implements Task {

    protected Map<String, Object> storage;

    public void setStorage(Map<String, Object> storage) {
        this.storage = storage;
    }
}
