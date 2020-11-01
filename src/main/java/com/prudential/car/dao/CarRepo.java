package com.prudential.car.dao;

import com.prudential.car.dao.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepo extends JpaRepository<Car,Long> {
    @Query("select c from Car c")
    Page<Car> findPagedCar(Pageable pageable);

    @Modifying
    @Query(value = "update Car c set c.remainCount=:newRemainCount , c.version=:newVersion where c.id=:cId and c.version=:oldVersion")
    public int updateCarRemainCount(@Param("cId")long carId, @Param("oldVersion") int oldVersion,@Param("newRemainCount") int newRemainCount,@Param("newVersion") int newVersion);

}
