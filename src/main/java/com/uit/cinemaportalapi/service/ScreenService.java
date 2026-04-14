package com.uit.cinemaportalapi.service;


import com.uit.cinemaportalapi.entity.Screen;

import java.util.List;

public interface ScreenService {
   List<Screen> getScreens();
   List<Screen> getScreensByCinema(Long cinemaID);
   Screen findScreenByID(Long ID);
   Screen createScreen(Screen screen);
   Screen updateScreen(Long id, Screen screen);
   void deleteScreen(Long id);
}
