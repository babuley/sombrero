package com.babuley.sombrero.repositories;

import com.babuley.sombrero.domain.Subject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
}
