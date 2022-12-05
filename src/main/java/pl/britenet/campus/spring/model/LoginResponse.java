package pl.britenet.campus.spring.model;

public class LoginResponse {

    private final boolean ok;
    private final String token;

    public LoginResponse(boolean ok, String token) {
        this.ok = ok;
        this.token = token;
    }

    public boolean isOk() {
        return ok;
    }

    public String getToken() {
        return token;
    }
}
