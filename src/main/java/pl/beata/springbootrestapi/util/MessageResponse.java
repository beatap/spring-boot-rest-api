package pl.beata.springbootrestapi.util;

public interface MessageResponse {

    String SUCCESS_ADD = "Success add car.";
    String SUCCESS_MOD = "Success modified car.";
    String ERROR_ADD = "Not create. Such a car exists or other error!";
    String ERROR_MOD = "No data object modify or other error!";
}
