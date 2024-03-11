package de.tom.repository;

import de.tom.domain.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Article findById(int id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO article(id, article_type, dealer_id, name, description)" +
            "VALUES (:id, :articleType, :dealerId, :name, :description)",
            nativeQuery = true)
    void saveArticle(@Param("id") int id, @Param("articleType") Article.articleType articleType, @Param("dealerId") int dealerId,
                     @Param("name") String name, @Param("description") String description);

}
