package com.babuley.sombrero.repositories;

import com.babuley.sombrero.domain.Lesson;
import com.babuley.sombrero.domain.rooms.Room;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository  extends PagingAndSortingRepository<Lesson, Long> {

    @Override
    List<Lesson> findAll();
}
