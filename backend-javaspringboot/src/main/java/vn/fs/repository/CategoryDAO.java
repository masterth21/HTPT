package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entity.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long>{

}