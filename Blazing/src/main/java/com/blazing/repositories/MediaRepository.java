package com.blazing.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blazing.objects.Media;
@Repository
public interface MediaRepository extends GeneralMediaRepository<Media>{

}
