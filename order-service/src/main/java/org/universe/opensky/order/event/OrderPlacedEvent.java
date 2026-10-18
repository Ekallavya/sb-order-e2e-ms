package org.universe.opensky.order.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderPlacedEvent {

    String orderNumber;
    String email;
    String fName;
    String lName;

}
