package org.solution.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Employee {

    private Integer id;
    private String firstName;
    private String lastName;
    private Long salary;
    private Integer managerId;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
