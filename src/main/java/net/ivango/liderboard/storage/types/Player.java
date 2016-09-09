package net.ivango.liderboard.storage.types;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Player {
    private int place, score;
    private final int id;
    private @NonNull String name, avatarURL;
}