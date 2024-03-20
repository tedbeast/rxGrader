package org.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Program {
    private int totalTests;
    private int passingTests;
    private String id;
    private String type;
    private String report;
}
