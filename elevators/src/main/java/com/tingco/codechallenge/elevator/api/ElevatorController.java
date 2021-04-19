package com.tingco.codechallenge.elevator.api;

import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestNoDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorCallRequestWithDirection;
import com.tingco.codechallenge.elevator.impl.request.ElevatorMoveBetweenFloorsRequest;
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
    void releaseElevator(Elevator elevator);

    //================================================================================================

    //Added by Marcin Migdal
    void executeElevatorCallRequestWithDirection(
        ElevatorCallRequestWithDirection elevatorCallRequest);

    void executeElevatorCallRequestWithNoDirection(
        ElevatorCallRequestNoDirection elevatorCallRequest);

    void executeElevatorCallRequestBetweenFloors(
        ElevatorMoveBetweenFloorsRequest elevatorCallRequest);
}