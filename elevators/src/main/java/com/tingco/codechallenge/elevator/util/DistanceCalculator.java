package com.tingco.codechallenge.elevator.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DistanceCalculator {

    private final static Logger LOG = LoggerFactory
        .getLogger(DistanceCalculator.class.getCanonicalName());

    private DistanceCalculator() {
    }

    public static Integer findElevatorIdWithShortestDistance(Map<Integer, Integer> distanceById) {
        if (distanceById.size() == 1) {
            LOG.trace("There is only one elevator with mapping distance by id: {} ", distanceById);
            return distanceById.values().stream().findFirst().get();
        }
        Integer shortestDistance = distanceById.keySet().stream().reduce(
            (distance1, distance2) -> distance1 < distance2 ? distance1 : distance2)
            .get();
        return distanceById.get(shortestDistance);
    }
}
