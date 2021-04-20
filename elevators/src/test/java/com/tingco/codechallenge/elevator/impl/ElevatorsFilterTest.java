package com.tingco.codechallenge.elevator.impl;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.Elevator.Direction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.tingco.codechallenge.elevator.config.TestConfig.*;

public class ElevatorsFilterTest {

    private ElevatorsFilter elevatorsFilter = new ElevatorsFilter();
    private List<Elevator> elevators = new ArrayList<Elevator>();
    
    @BeforeEach
    void initTest(){
        //given
        elevators.clear();
        Elevator elevator1 = getElevatorAtFloor(1,Direction.UP,FLOOR_2);
        Elevator elevator2 = getElevatorAtFloor(2,Direction.UP,FLOOR_7);
        Elevator elevator3 = getElevatorAtFloor(3,Direction.DOWN,FLOOR_5);
        Elevator elevator4 = getElevatorAtFloor(3,Direction.DOWN,FLOOR_10);
        Elevator elevator5 = getElevatorAtFloor(2,Direction.DOWN,FLOOR_8);
        Elevator elevator6 = getElevatorAtFloor(2,Direction.UP,FLOOR_8);
        elevators.addAll(List.of(elevator1,elevator2,elevator3,elevator4,elevator5,elevator6));
    }
    
    @Test
    void countElevatorsGoingUpTowardsFloor2(){
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP,FLOOR_2);
        //then
        assertEquals(0,result.size());
    }

    @Test
    void countElevatorsGoingUpTowardsFloor5(){
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP,FLOOR_5);
        //then
        assertEquals(1,result.size());
    }

    @Test
    void countElevatorsGoingUpTowardsFloor9(){
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.UP,FLOOR_9);
        //then
        assertEquals(3,result.size());
    }

    @Test
    void countElevatorsGoingDownTowardsFloor10(){
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN,FLOOR_10);
        //then
        assertEquals(0,result.size());
    }

    @Test
    void countElevatorsGoingDownTowardsFloor7(){
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN,FLOOR_7);
        //then
        assertEquals(2,result.size());
    }

    @Test
    void countElevatorsGoingDownTowardsFloor3(){
        //when
        List<Elevator> result = elevatorsFilter
            .getElevatorsGoingWithDirectionTowardsFloor(elevators, Direction.DOWN,FLOOR_3);
        //then
        assertEquals(3,result.size());
    }


    private Elevator getElevatorAtFloor(int elevatorId,Direction direction, int elevatorFloor){
        Elevator elevator = ElevatorFactory.getElevator(elevatorId);
        elevator.setCurrentFloor(elevatorFloor);
        elevator.setDirection(direction);
        return elevator;
    }


}
