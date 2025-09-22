package com.ThatoMoet.repository;

import com.ThatoMoet.entities.FinancialRecords;
import com.ThatoMoet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialDataRepository extends JpaRepository<FinancialRecords, Long> {
    List<FinancialRecords> findByUserAndYear(User user, int year);
}
