package rip.faith_in_humanity.time.toggl.utils;

public class Response<T> {
    private int code;
    private T body;

    public Response() {
    }

    public Response(int code, T body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
