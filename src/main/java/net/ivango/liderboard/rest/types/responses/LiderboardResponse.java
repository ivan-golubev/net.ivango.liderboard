package net.ivango.liderboard.rest.types.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ivango.liderboard.storage.types.Player;

import java.util.List;

@Data
@AllArgsConstructor
public class LiderboardResponse extends Response {
    private List<Player> liderboard;
}