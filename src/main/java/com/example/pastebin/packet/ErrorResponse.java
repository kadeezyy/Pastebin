package com.example.pastebin.packet;


public record ErrorResponse(String error) implements IResponse {
}
