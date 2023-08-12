package io.fam.bloggersite.repository;

import org.springframework.stereotype.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.fam.bloggersite.entity.Blog;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long>, PagingAndSortingRepository<Blog, Long> {

}
