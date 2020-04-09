package org.cfx.utils;

public enum Restriction {
    RESTRICTED("restricted"),
    UNRESTRICTED("unrestricted");

    private String restriction;

    Restriction(final String restriction) {

        this.restriction = restriction;
    }

    public String getRestriction() {
        return restriction;
    }

    public static Restriction fromString(final String s) {
        return valueOf(s.toUpperCase());
    }
}
