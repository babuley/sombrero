package com.babuley.sombrero.repositories;

import com.babuley.sombrero.domain.StudentGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends PagingAndSortingRepository<StudentGroup, Long> {
}
