package com.compassouol.backendrecruitment.repositories;

import java.util.List;

import com.compassouol.backendrecruitment.models.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT cities.* " + "FROM cities " + "NATURAL JOIN states "
            + "WHERE cities.city_name_normalized ILIKE %:search% OR states.state_name_normalized ILIKE %:search% OR states.state_short_name ILIKE %:search% "
            + "ORDER BY cities.city_name_normalized ASC, states.state_name_normalized ASC", nativeQuery = true)
    List<City> findAllWithSearch(String search);
}
