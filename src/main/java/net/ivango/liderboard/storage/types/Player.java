package net.ivango.liderboard.storage.types;

import lombok.Data;
import lombok.NonNull;

@Data
public class Player {
    private int place, score;
    private @NonNull int id;
    private @NonNull String name, avatarURL;
}