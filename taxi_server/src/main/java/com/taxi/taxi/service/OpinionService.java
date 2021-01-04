package com.taxi.taxi.service;

import com.taxi.taxi.dto.OpinionDto;
import com.taxi.taxi.model.*;
import com.taxi.taxi.repository.*;
import com.taxi.taxi.request.OpinionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionService {
    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public OpinionDto addOpinion(Customer customer, OpinionRequest opinionRequest) {
        Ride ride = rideRepository.findByCustomerAndIdAndOpinionIsNull(customer, opinionRequest.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("Nie możesz wykonać tej operacji"));

        Opinion opinion = new Opinion(opinionRequest.getRate(), opinionRequest.getComment(), ride);
        opinionRepository.save(opinion);

        Driver driver = ride.getDailyInfo().getDriver();
        Double rate = driver.getRate();
        Double ratesNumber = driver.getRatesNumber().doubleValue();

        if(driver.getRatesNumber() == 0) {
            rate = opinion.getRate().doubleValue();
        } else {
            rate = (ratesNumber * rate + opinionRequest.getRate()) / (ratesNumber + 1);
        }

        driver.setRate(rate);
        driver.setRatesNumber(driver.getRatesNumber() + 1);
        driverRepository.save(driver);

        if(ride.getDailyInfo().getCompany() != null) {
            Company company = ride.getDailyInfo().getCompany();
            rate = company.getRate();
            ratesNumber = company.getRatesNumber().doubleValue();

            if(company.getRatesNumber() == 0) {
                rate = opinion.getRate().doubleValue();
            } else {
                rate = (ratesNumber * rate + opinionRequest.getRate()) / (ratesNumber + 1);
            }

            company.setRate(rate);
            company.setRatesNumber(company.getRatesNumber() + 1);
            companyRepository.save(company);
        }

        return new OpinionDto(opinion);
    }

    public List<OpinionDto> getOpinionsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Użytkownik nie istnieje"));

        List<Ride> rides;

        if(user.getUserType() == "CUSTOMER") {
            rides = rideRepository.findAllByCustomer((Customer) user);
        } else if (user.getUserType() == "DRIVER") {
            rides = rideRepository.findAllByDailyInfoDriver((Driver) user);
        } else {
            rides = rideRepository.findAllByDailyInfoCompany((Company) user);
        }

        List<OpinionDto> opinions = rides
                .stream()
                .filter(ride -> ride.getOpinion() != null)
                .map(ride -> new OpinionDto(ride.getOpinion()))
                .collect(Collectors.toList());

        return opinions;
    }

    public OpinionDto getOpinionsForRide(Long rideId) {
        Ride ride = rideRepository.findByIdAndOpinionIsNotNull(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Przejazd jeszcze nie został oceniony"));

        return new OpinionDto(ride.getOpinion());
    }
}
