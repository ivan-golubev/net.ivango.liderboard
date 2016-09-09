package net.ivango.liderboard.rest.types.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.ivango.liderboard.storage.types.Player;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LiderboardResponse extends Response {
    private List<Player> liderboard;
}