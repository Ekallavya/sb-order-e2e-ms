package org.universe.opensky.gateway.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class User {
    private Long id;
    private String name;
    private String address;
}
