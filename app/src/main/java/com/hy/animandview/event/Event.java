package com.hy.animandview.event;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hy
 * @date 2020/10/27
 * desc:
 **/
public class Event {

    private String mAction;
    private Bundle mBundle = new Bundle();

    public Event(@Nullable String action) {
        this.mAction = action;
    }

    public Event(@Nullable String action, @NonNull Bundle bundle) {
        this.mAction = action;
        this.mBundle = bundle;
    }

    public @Nullable String getAction() {
        return mAction;
    }

    public void setAction(@Nullable String action) {
        this.mAction = action;
    }

    public @NonNull Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(@NonNull Bundle bundle) {
        this.mBundle = bundle;
    }
}
