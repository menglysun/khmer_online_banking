package org.springclass.onlinebankingsystem.shared.object;

public record ResponseObject(int code, String message) {

    public static ResponseObject success() {
        return new ResponseObject(200, "success");
    }

    public static ResponseObject error() {
        return new ResponseObject(404, "Error object not found");
    }
}
