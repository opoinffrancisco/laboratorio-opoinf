package com.opoinf.laboratorio_opoinf.repository

import com.opoinf.laboratorio_opoinf.model.Article
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ArticleRepository {

  private val articles = listOf(
    Article(id = UUID.randomUUID(), title= "Article 1", content= "Content 1"),
    Article(id = UUID.randomUUID(), title= "Article 2", content= "Content 2"),
  )

  fun findAll(): List<Article> =
    articles

}
