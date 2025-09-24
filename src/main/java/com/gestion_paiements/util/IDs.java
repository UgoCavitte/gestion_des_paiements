package com.gestion_paiements.util;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class IDs {

    // Returns the first available ID
    public static int getAvailableID(@NotNull Set<? extends WithID> usedIDs) {
        if (usedIDs.isEmpty()) return 0;

        Set<Integer> usedIDsSet = usedIDs.stream().map(WithID::getId).collect(Collectors.toSet());

        return Stream.iterate(0, i -> i + 1).filter(i -> !usedIDsSet.contains(i)).findFirst().orElse(-1);
    }
}
