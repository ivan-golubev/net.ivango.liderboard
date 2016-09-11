package net.ivango.liderboard.storage.types;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Player {
    private final int id;
    private int place, score;
    private @NonNull String name, avatarURL;
}