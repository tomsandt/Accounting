package de.tom.service;

import de.tom.domain.Article;
import de.tom.domain.ArticleDTO;
import de.tom.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(int id) {
        return Optional.ofNullable(articleRepository.findById(id));
    }

    public Article createArticle(ArticleDTO articleDTO) {

        Article newArticle = new Article();

        newArticle.setArticleType(articleDTO.getArticleType());
        newArticle.setDealerId(articleDTO.getDealerId());
        newArticle.setName(articleDTO.getName());
        newArticle.setDescription(articleDTO.getDescription());

        return articleRepository.save(newArticle);

    }

    public void deleteArticle(int id) {
        if(!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article not found with ID: " + id);
        }else {
            articleRepository.deleteById(id);
        }

    }

}
