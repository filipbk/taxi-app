package com.taxi.taxi.service;

import com.taxi.taxi.dto.CarDto;
import com.taxi.taxi.model.Car;
import com.taxi.taxi.model.Price;
import com.taxi.taxi.model.User;
import com.taxi.taxi.repository.PriceRepository;
import com.taxi.taxi.request.CarRequest;
import com.taxi.taxi.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PriceRepository priceRepository;

    public CarDto createCar(CarRequest carRequest, User user) {
        Price price = priceRepository.findByIdAndUser(carRequest.getPriceId(), user)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Zestaw cen o indeksie {0} nie istnieje", carRequest.getPriceId())));

        Car car = new Car(carRequest.getProducer(), carRequest.getModelName(), carRequest.getProductionYear(), carRequest.getRegistrationNumber(), carRequest.getColor(), user, price);
        carRepository.save(car);

        return new CarDto(car);
    }

    public List<CarDto> getUserCars(User user) {
        List<CarDto> cars = carRepository.findByOwnerId(user.getId())
                .stream()
                .map(car -> new CarDto(car))
                .collect(Collectors.toList());

        return cars;
    }
}
