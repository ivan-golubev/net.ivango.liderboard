package net.ivango.liderboard.rest.types.responses;

import lombok.*;
import net.ivango.liderboard.storage.types.Player;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LiderboardResponse extends Response {
    private List<Player> liderboard;
}