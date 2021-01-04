package com.taxi.taxi.service;

import com.taxi.taxi.dto.PriceDto;
import com.taxi.taxi.model.Company;
import com.taxi.taxi.model.Driver;
import com.taxi.taxi.model.Price;
import com.taxi.taxi.model.User;
import com.taxi.taxi.request.PriceRequest;
import com.taxi.taxi.repository.PriceRepository;
import com.taxi.taxi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UserRepository userRepository;

    public PriceDto addPrice(PriceRequest priceRequest, User user) {
        Price price = new Price(priceRequest.getFirstFareDay(), priceRequest.getSecondFareDay(), priceRequest.getFirstFareNight(), priceRequest.getSecondFareNight(),
                priceRequest.getStartPrice(), priceRequest.getSecondFareDistance(), user, priceRequest.getName(), priceRequest.getDayStart(), priceRequest.getNightStart());
        priceRepository.save(price);

        return new PriceDto(price);
    }


    public List<Price> getUserPrices(User user) {
        List<Price> prices = priceRepository.findByUserId(user.getId());

        return prices;
    }
}
