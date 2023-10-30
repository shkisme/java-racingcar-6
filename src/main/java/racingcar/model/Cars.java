package racingcar.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import racingcar.model.car.Car;
import racingcar.model.car.Name;
import racingcar.model.car.Position;

public class Cars {
    private static final String DUPLICATE_CAR_MESSAGE = "중복된 이름의 자동차가 존재합니다.";
    private static final String EMPTY_CAR_MESSAGE = "자동차 리스트가 비었습니다.";

    private final List<Car> cars;

    private Cars(List<Car> cars) {
        this.cars = cars;
    }

    public static Cars from(String carNames) {
        List<Car> cars = generateCars(carNames);
        validateEmpty(cars);
        validateDuplicate(cars);
        return new Cars(cars);
    }

    private static void validateEmpty(List<Car> cars) {
        if (cars.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_CAR_MESSAGE);
        }
    }

    private static void validateDuplicate(List<Car> cars) {
        List<Name> carNames = cars.stream()
                .map(Car::name)
                .toList();
        Set<Name> nonDuplicateCarNames = new HashSet<>(carNames);
        if (nonDuplicateCarNames.size() != carNames.size()) {
            throw new IllegalArgumentException(DUPLICATE_CAR_MESSAGE + carNames);
        }
    }

    private static List<Car> generateCars(String carNames) {
        return Arrays.stream(carNames.split(","))
                .map(String::trim)
                .map(Name::from)
                .map(name -> new Car(name, new Position(0)))
                .toList();
    }
}
