package ru.geekbrains.winter_market.auth.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppError {

    private int statusCode;

    private String message;
}
