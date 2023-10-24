<template>
  <section class="container article">
    <div class="breadcrumb-wrap">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="parent.route">{{ parent.title}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{breadcrumbName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <common-article :title="article.title" :author="article.author" :image="article.imageUrl" :create-time="article.createTime" :content="article.content"/>
  </section>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'service-article',
  props: {
    articleId: {
      type: [String, Number],
      required: true
    },
    service: {
      type: String,
      required: true
    }
  },
  mounted () {
    this.getArticle(this.articleId)
  },
  computed: {
    ...mapState('news', { newsArticle: 'article' }),
    ...mapState('case', { caseArticle: 'article' }),
    isShowNewArticle () {
      return this.service === 'news'
    },
    article () {
      // return this.newsArticle()
      return this.isShowNewArticle ? this.newsArticle : this.caseArticle
    },
    breadcrumbName () {
      return this.$route.meta.title
    },
    parent () {
      return this.$route.meta.parent
    }
  },
  methods: {
    ...mapActions('news', { getNewsArticle: 'getArticle' }),
    ...mapActions('case', { getCaseArticle: 'getArticle' }),

    getArticle (articleId) {
      // this.getNewsArticle(articleId)
      this.isShowNewArticle ? this.getNewsArticle(articleId) : this.getCaseArticle(articleId)
    }
  }
}
</script>
