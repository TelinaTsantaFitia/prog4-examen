package com.telina.demo.repository;

import com.telina.demo.entity.BookCopy;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {}
