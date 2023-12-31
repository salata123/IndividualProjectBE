package com.example.individualprojectbe.amadeus.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentDto {
    private Long id;
    private Location departure;
    private Location arrival;
}