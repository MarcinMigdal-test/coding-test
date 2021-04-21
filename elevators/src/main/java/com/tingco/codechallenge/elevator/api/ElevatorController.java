package com.tingco.codechallenge.elevator.api;

import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import java.util.List;

/**
 * Interface for the Elevator Controller.
 *
 * @author Sven Wesley
 */
public interface ElevatorController {

    /**
     * Request an elevator to the specified floor.
     *
     * @param toFloor addressed floor as integer.
     * @return The Elevator that is going to the floor, if there is one to move.
     */
    @Deprecated
    Elevator requestElevator(int toFloor);

    /**
     * A snapshot list of all elevators in the system.
     *
     * @return A List with all {@link Elevator} objects.
     */
    List<Elevator> getElevators();

    /**
     * Telling the controller that the given elevator is free for new operations.
     *
     * @param elevator the elevator that shall be released.
     */
    @Deprecated
    void releaseElevator(Elevator elevator);

    /**
     * Method invokes request to call elevator with direction
     *
     * @param elevatorCallRequest elevator call request including direction
     */
    void executeElevatorCallRequestWithDirection(
        ElevatorCallRequestWithDirection elevatorCallRequest);

    /**
     * Method invokes request to call elevator without direction
     *
     * @param elevatorCallRequest elevator call request without direction
     */
    void executeElevatorCallRequestWithNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequest);
}