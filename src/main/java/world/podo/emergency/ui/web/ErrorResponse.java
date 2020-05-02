package world.podo.emergency.ui.web;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String message;
}
