package com.smokiyenko.burokrat.app;

import com.google.common.base.Objects;

/**
 * Created by s.mokiyenko on 9/7/14.
 */
public class SessionResponse {

    private final String jsessionid;
    private final String firstName;
    private final String secondName;

    public SessionResponse(final String id, final String name, final String sername) {
        this.jsessionid = id;
        this.firstName = name;
        this.secondName = sername;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(SessionResponse.class).add("jsessionid", jsessionid).add("firstName", firstName).add("secondName", secondName).toString();
    }
}
