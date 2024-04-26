package de.tom.service;

import de.tom.domain.Article;
import de.tom.domain.ArticleDTO;
import de.tom.repository.DatabaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private DatabaseRepository databaseRepository;

    public List<Article> getAllArticles() {
        return databaseRepository.findAll(Article.class);
    }

    public Optional<Article> getArticleById(int id) {
        return Optional.ofNullable(databaseRepository.findById(Article.class, id));
    }

    public Article createArticle(ArticleDTO articleDTO) {

        Article newArticle = new Article();

        newArticle.setArticleType(articleDTO.getArticleType());
        newArticle.setDealerId(articleDTO.getDealerId());
        newArticle.setName(articleDTO.getName());
        newArticle.setDescription(articleDTO.getDescription());

        return databaseRepository.save(newArticle);

    }

    public void deleteArticle(int id) {
        if(databaseRepository.findById(Article.class, id) == null) {
            throw new EntityNotFoundException("Article not found with ID: " + id);
        }else {
            databaseRepository.delete(Article.class, id);
        }

    }

}
