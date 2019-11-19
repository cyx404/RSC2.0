package com.rsc.repository;

import com.rsc.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region,Integer> {

    @Override
    List<Region> findAll();
}
