package com.opoinf.laboratorio_opoinf.service

import com.opoinf.laboratorio_opoinf.model.Article
import com.opoinf.laboratorio_opoinf.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(
  private val articleRepository: ArticleRepository
) {

  fun findAll(): List<Article> =
    articleRepository.findAll()
}
