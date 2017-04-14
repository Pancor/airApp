package pl.pancor.android.air.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class DataResponse<T> {

    @SerializedName("error")
    private final boolean error;

    @Nullable @SerializedName("message")
    private final String message;

    @Nullable @SerializedName("data")
    private final List<T> data;

    /**
     * Use this to handle data response from server
     * @param error false if everything go right, true if something go wrong
     * @param message when error is set to true, message say, what's wrong
     * @param data it's data response (json), it's set when error is false
     */
    public DataResponse(boolean error, @Nullable String message, @Nullable List<T> data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public List<T> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data response wit error set to " + error;
    }
}
