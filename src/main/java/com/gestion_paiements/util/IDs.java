package com.gestion_paiements.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public abstract class IDs {

    // Returns the first available ID
    public static int getAvailableID(@NotNull HashSet<Integer> usedIDs) {
        if (usedIDs.isEmpty()) return 0;

        else {
            // Checks every number until it is not in the set
            for (int testedID = 0; ; testedID++) {
                if (!usedIDs.contains(testedID)) {
                    return testedID;
                }
            }
        }
    }

}
