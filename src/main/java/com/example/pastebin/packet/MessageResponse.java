package com.example.pastebin.packet;

import java.io.Serializable;

public record MessageResponse(String message) implements IResponse {
}
