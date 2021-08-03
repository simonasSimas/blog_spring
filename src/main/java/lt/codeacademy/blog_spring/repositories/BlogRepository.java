package lt.codeacademy.blog_spring.repositories;

import lt.codeacademy.blog_spring.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // select * from car where model = :model
    List<Blog> getPostsByTitle(String title);

    // select * from car where model like :str
    Page<Blog> getPostsByTitleContaining(Pageable pageable, String title);

    Blog getBlogById (Long id);
}

