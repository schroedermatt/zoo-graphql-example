package com.opi.zoo.rest.domain

enum AnimalType {
    BEAR,
    BIRD,
    ELEPHANT,
    FISH,
    GIRAFFE,
    INSECT,
    KOALA,
    LION,
    LIZARD,
    OTTER,
    RAT,
    SNAKE,
    TIGER,
    TURTLE,
    ZEBRA

    // random type helper

    private static final List<AnimalType> VALUES = Collections.unmodifiableList(Arrays.asList(values()))
    private static final int SIZE = VALUES.size()
    private static final Random RANDOM = new Random()

    static AnimalType randomType()  {
        return VALUES.get(RANDOM.nextInt(SIZE))
    }
}