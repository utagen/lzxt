<template>
  <section class="container news-list">
    <div class="breadcrumb-wrap">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{breadcrumbName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <empty-list v-if="isEmpty">客官！请稍等，小编正在准备各种有价值的信息和案例</empty-list>
    <template v-else>
      <div class="list-wrap">
        <el-radio-group v-if="showNewsList" v-model="newsType" @change="switchNewsType">
          <el-radio-button :label="1">题库资讯</el-radio-button>
          <el-radio-button :label="2">升学资讯</el-radio-button>
          <el-radio-button :label="3">其他资讯</el-radio-button>
        </el-radio-group>
        <div v-for="item in list" class="list-item" :key="item.id" >
          <el-image :src="item.imageUrl" fit="contain" class="cover">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
          <div class="list-item__content">
            <h4 class="title">
              <router-link v-if="showNewsList" :to="`/news/${item.id}`">{{item.title}}</router-link>
              <router-link v-else :to="`/cases/${item.id}`">{{item.title}}</router-link>
            </h4>
            <p class="summary" v-html="item.summary"></p>
            <div class="footer">{{item.createTime}}</div>
          </div>
        </div>
      </div>
      <el-pagination
        layout="total,prev,pager,next,jumper"
        hide-on-single-page
        :current-page.sync="page"
        :page-size="pageSize"
        :total="total"
        @current-change="switchPagination">
      </el-pagination>
    </template>
  </section>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex'

export default {
  name: 'service-list',
  props: {
    service: {
      type: String,
      required: true
    }
  },
  data () {
    return { newsType: 1, page: 1, pageSize: 10 }
  },
  computed: {
    ...mapGetters('news', {
      newsList: 'newsList',
      newsTotal: 'total'
    }),
    ...mapState('case', {
      caseList: state => state.caseList.list,
      caseTotal: state => state.caseList.total
    }),
    showNewsList () {
      return this.service === 'news'
    },
    list () {
      return this.showNewsList ? this.newsList : this.caseList
    },
    total () {
      return this.showNewsList ? this.newsTotal : this.caseTotal
    },
    isEmpty () {
      return this.total === 0
    },
    breadcrumbName () {
      return this.$route.meta.title
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    ...mapActions('news', ['getNewsList']),
    ...mapActions('case', ['getCaseList']),
    getList () {
      const { newsType, page, pageSize } = this
      this.showNewsList ? this.getNewsList({ newsType, page, pageSize }) : this.getCaseList({ page, pageSize })
    },
    switchNewsType (type) {
      this.page = 1
      this.getList()
    },
    switchPagination (page) {
      this.page = page
      this.getList()
    }
  }
}
</script>
