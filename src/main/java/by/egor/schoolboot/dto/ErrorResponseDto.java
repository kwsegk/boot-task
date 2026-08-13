package by.egor.schoolboot.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    String message;
    String detailedMessage;
    LocalDateTime errorTime;

    public ErrorResponseDto(String message, String detailedMessage, LocalDateTime errorTime) {
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.errorTime = errorTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }
}
