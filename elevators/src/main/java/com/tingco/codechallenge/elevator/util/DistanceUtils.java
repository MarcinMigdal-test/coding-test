package com.tingco.codechallenge.elevator.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DistanceUtils {

    private final static Logger LOG = LoggerFactory
        .getLogger(DistanceUtils.class.getCanonicalName());

    private DistanceUtils() {
    }

    public static Integer findElevatorIdWithShortestDistance(Map<Integer, Integer> distanceById) {
        if (distanceById.size() == 1) {
            LOG.info("There is only one elevator with mapping: {} ", distanceById);
            return distanceById.values().stream().findFirst().get();
        }
        Integer shortestDistance = distanceById.keySet().stream().reduce(
            (integer, integer2) -> integer.intValue() < integer2.intValue() ? integer : integer2)
            .get();
        return distanceById.get(shortestDistance);
    }
}
