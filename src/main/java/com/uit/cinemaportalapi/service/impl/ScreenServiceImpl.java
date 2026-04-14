package com.uit.cinemaportalapi.service.impl;



import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.entity.Screen;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.repository.ScreenRepository;
import com.uit.cinemaportalapi.service.CinemaService;
import com.uit.cinemaportalapi.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private CinemaService cinemaService;

    @Override
    public java.util.List<Screen> getScreens() {
        try {
            return screenRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find all Screen: " + e.getMessage());
        }
    }

    @Override
    public java.util.List<Screen> getScreensByCinema(Long cinemaID) {
        try {
            Cinema cinema = cinemaService.getCinemaByID(cinemaID);
            return screenRepository.findAllByCinema(cinema);
        } catch (Exception e) {
            throw new BadRequestException("Can not find Screen by cinema: " + e.getMessage());
        }
    }

    @Override
    public Screen findScreenByID(Long ID) {
        try {
            return screenRepository.findById(ID)
                    .orElseThrow(() -> new BadRequestException("Screen not found with id: " + ID));
        } catch (Exception e) {
            throw new BadRequestException("Can not find Screen by id: " + e.getMessage());
        }
    }

    @Override
    public Screen createScreen(Screen screen) {
        try {
            if (screen.getCinema() == null || screen.getCinema().getId() == null) {
                throw new BadRequestException("cinema.id is required");
            }
            if (screen.getScreenCode() == null || screen.getScreenCode().trim().isEmpty()) {
                throw new BadRequestException("screenCode is required");
            }

            Cinema cinema = cinemaService.getCinemaByID(screen.getCinema().getId());
            String screenCode = screen.getScreenCode().trim().toUpperCase();
            if (screenRepository.existsByCinemaAndScreenCodeIgnoreCase(cinema, screenCode)) {
                throw new BadRequestException("screenCode already exists in this cinema: " + screenCode);
            }

            screen.setId(null);
            screen.setCinema(cinema);
            screen.setScreenCode(screenCode);
            return screenRepository.save(screen);
        } catch (Exception e) {
            throw new BadRequestException("Can not create Screen: " + e.getMessage());
        }
    }

    @Override
    public Screen updateScreen(Long id, Screen screen) {
        try {
            Screen existingScreen = findScreenByID(id);

            if (screen.getCinema() != null && screen.getCinema().getId() != null
                    && !screen.getCinema().getId().equals(existingScreen.getCinema().getId())) {
                Cinema cinema = cinemaService.getCinemaByID(screen.getCinema().getId());
                existingScreen.setCinema(cinema);
            }

            if (screen.getScreenCode() != null && !screen.getScreenCode().trim().isEmpty()) {
                String nextCode = screen.getScreenCode().trim().toUpperCase();
                if (!nextCode.equalsIgnoreCase(existingScreen.getScreenCode())
                        && screenRepository.existsByCinemaAndScreenCodeIgnoreCase(existingScreen.getCinema(), nextCode)) {
                    throw new BadRequestException("screenCode already exists in this cinema: " + nextCode);
                }
                existingScreen.setScreenCode(nextCode);
            }

            existingScreen.setTypeScreen(screen.getTypeScreen());
            return screenRepository.save(existingScreen);
        } catch (Exception e) {
            throw new BadRequestException("Can not update Screen: " + e.getMessage());
        }
    }

    @Override
    public void deleteScreen(Long id) {
        try {
            Screen existingScreen = findScreenByID(id);
            screenRepository.delete(existingScreen);
        } catch (Exception e) {
            throw new BadRequestException("Can not delete Screen: " + e.getMessage());
        }
    }
}
