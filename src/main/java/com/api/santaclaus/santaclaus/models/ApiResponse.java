package com.api.santaclaus.santaclaus.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    String response;
    Boolean status; 
    String token;
}
