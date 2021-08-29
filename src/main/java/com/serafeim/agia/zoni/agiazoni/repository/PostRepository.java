package com.serafeim.agia.zoni.agiazoni.repository;

import com.serafeim.agia.zoni.agiazoni.model.WPPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PostRepository extends JpaRepository<WPPost, Long> {
}
