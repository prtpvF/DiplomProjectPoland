package pl.diplom.common.model.enums;

/**
 * Value of importance start from 1(min) to 5(max)
 * ACCEPTED - order is accepted - 1
 * IN_PROGRESS - start make a pizza - 2
 * READY - pizza is coked - 3
 * IN_DELIVER - deliveryman is going to a client address - 4
 * DELIVERED - order is given to a client - 5
 */
public enum PersonOrderEnum {
        ACCEPTED,
        IN_PROCESS,
        READY,
        IN_DELIVER,
        DELIVERED
}
